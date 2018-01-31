package connection_tdb;

import github_provider.Repository;
import org.apache.jena.base.Sys;
import org.apache.jena.datatypes.xsd.impl.XSDBaseNumericType;
import org.apache.jena.datatypes.xsd.impl.XSDBaseStringType;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TDBRepository {
    private TDBConnection conn;

    public TDBRepository() {
        conn = new TDBConnection();
//        conn.loadModel();
        conn.setOntologyPrefixes();

    }

    public List<OntResource> readInstanceFor(OntClass c) {
        return extractInstances(conn.getStatements(null, Prefixes.RDF_TYPE, c.getURI()));
    }

    public String readDescription(OntResource obj) {
        List<Statement> s = conn.getStatements(obj.getURI(), Prefixes.WADO_HAS_DESCRIPTION, null );
        if(s.size() >= 1) {
            return s.get(0).getObject().asLiteral().getString();
        }
        return "";
    }

    public void insertProperty(OntResource s, OntProperty p, OntResource o) {
        conn.addStatement(s.getURI(),p.getURI(),o.getURI());
    }
    private List<OntResource> extractInstances(List<Statement> stmts) {
        List<OntResource> instances = new ArrayList<>();
        for(Statement s: stmts) {
            if(s.getSubject().toString().startsWith("http")) {
                instances.add(s.getSubject().as(OntResource.class));
            }
        }
        return instances;
    }

    public Map<String, String> getAllClasses() {
        List<Statement> classesTriple = this.conn.getStatements(null, Prefixes.RDF_TYPE, Prefixes.OWL_CLASS);
        Map<String, String> classes = new HashMap<>();
        for (Statement triple : classesTriple) {
            if (triple.getSubject().getLocalName() != null) {
                classes.put(Utils.format(triple.getSubject().getLocalName()), triple.getSubject().getLocalName());
            }
        }
        return classes;

    }

    public void insertInstances(Map<String, Map<String, List<Repository>>> entities) {
        for (Map.Entry<String, Map<String, List<Repository>>> entry : entities.entrySet()) {
            String classToInsert = entry.getKey();
            for (Map.Entry<String, List<Repository>> instances : entry.getValue().entrySet()) {
                this.insertReposBelongSingleClass(classToInsert, instances.getKey(), instances.getValue());
            }
        }
    }

    private void insertReposBelongSingleClass(String className, String priority, List<Repository> repos) {
        for (Repository repo : repos) {
            this.insertOneRepoFor(className, priority, repo);
        }

    }

    private void insertOneRepoFor(String className, String priority, Repository repo) {
        conn.addStatement(Prefixes.ONTOLOGY_NS + repo.getName(), Prefixes.RDF_TYPE, Prefixes.ONTOLOGY_NS + className);
        conn.addStatement(Prefixes.ONTOLOGY_NS + repo.getLanguage(), Prefixes.RDF_TYPE, Prefixes.WADO_PROGRAMING_LANGUAGE);
        conn.addStatement(Prefixes.ONTOLOGY_NS + repo.getUrl(), Prefixes.RDF_TYPE, Prefixes.WADO_PROGRAMING_LANGUAGE);
        conn.addStatement(Prefixes.ONTOLOGY_NS + repo.getName(), Prefixes.WADO_HAS_LINK, repo.getUrl());
        conn.addLiteral(
                Prefixes.ONTOLOGY_NS + repo.getName(),
                Prefixes.WADO_HAS_DESCRIPTION,
                ResourceFactory.createTypedLiteral(repo.getDescription(), XSDBaseStringType.XSDstring)
        );
        conn.addLiteral(
                Prefixes.ONTOLOGY_NS + repo.getName(),
                Prefixes.WADO_HAS_STARS,
                ResourceFactory.createTypedLiteral(String.valueOf(repo.getStars()), XSDBaseNumericType.XSDint)
        );
        conn.addLiteral(
                Prefixes.ONTOLOGY_NS + repo.getName(),
                Prefixes.WADO_HAS_PRIORITY,
                ResourceFactory.createTypedLiteral(priority, XSDBaseStringType.XSDstring)
        );

    }

    public void printAll() {
        List<Statement> listStmt = conn.getStatements(null, Prefixes.WADO_HAS_LINK, null);
        System.out.println(listStmt.size());
        for (Statement s : listStmt) {
            System.out.println(s);
        }
    }

    public void insertProperties() {

    }

    public List<OntProperty> getAllProperties() {
//        List<Statement> properties = this.conn.getStatements(null, Prefixes.RDF_TYPE, Prefixes.OWL_OBJECT_PROPERTY);
//        properties.addAll(this.conn.getStatements(null, Prefixes.RDF_TYPE, Prefixes.OWL_DATATYPE_PROPERTY));
//        System.out.println(properties.size());
//        List<OntProperty> formatedProperties = new ArrayList<>();
//        for (Statement triple : properties) {
//            if (triple.getSubject().getLocalName() != null) {
//                formatedProperties.add(triple.getSubject().as(OntProperty.class));
//            }
//        }
//        return formatedProperties;

        OntModel model = conn.getModel();
        ExtendedIterator<OntProperty> propertiesIterator = model.listAllOntProperties();
        List<OntProperty> properties = new ArrayList<>();
        while (propertiesIterator.hasNext()) {
            properties.add(propertiesIterator.next());
        }
        return properties;
    }

    public List<OntClass> getDomainsClassesFor(OntProperty property) {
        ExtendedIterator<OntClass> opDomains = (ExtendedIterator<OntClass>) property.listDomain();
        return convertToClassesList(opDomains);
    }

    public List<OntClass> getRangeClassesFor(OntProperty property) {
        ExtendedIterator<OntClass> opRange = (ExtendedIterator<OntClass>) property.listRange();
        return convertToClassesList(opRange);
    }

    private List<OntClass> convertToClassesList(ExtendedIterator<OntClass> opDomains) {
        List<OntClass> classes = new ArrayList<>();
        while (opDomains.hasNext()) {
            OntClass c = opDomains.next();
            if(c != null && c.getURI() != null) {
                classes.add(c);
            }
        }
        return classes;
    }
}
