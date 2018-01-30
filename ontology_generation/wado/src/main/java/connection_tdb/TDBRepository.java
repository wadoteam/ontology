package connection_tdb;

import github_provider.Repository;
import org.apache.jena.datatypes.xsd.impl.XSDBaseNumericType;
import org.apache.jena.datatypes.xsd.impl.XSDBaseStringType;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.XSD;

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
        for(Statement s:listStmt){
            System.out.println(s);
        }

    }
}
