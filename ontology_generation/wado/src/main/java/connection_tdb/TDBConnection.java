package connection_tdb;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;

import java.util.ArrayList;
import java.util.List;

public class TDBConnection {
    private static final String ONTOLOGY_PATH_TDB = "/home/cosntantin/Downloads/wado/ontology";
    private static final String ONTOLOGY_MODEL_NAME = "wado";
    private static final String ONTOLOGY_FILES = "/home/cosntantin/Downloads/wado/src/main/resources/wado-final.owl";
    private Dataset ds;

    public TDBConnection() {
        ds = TDBFactory.createDataset(ONTOLOGY_PATH_TDB);
    }

    public void loadModel() {
        Model model = null;

        ds.begin(ReadWrite.WRITE);
        model = ds.getNamedModel(ONTOLOGY_MODEL_NAME);
        FileManager.get().readModel(model, ONTOLOGY_FILES);
        ds.commit();
        ds.end();
    }

    public List<Statement> getStatements(String subject, String property, String object) {
        List<Statement> results = new ArrayList<Statement>();

        Model model = null;

        ds.begin(ReadWrite.READ);
        try {
            model = ds.getNamedModel(ONTOLOGY_MODEL_NAME);

            Selector selector = new SimpleSelector(
                    (subject != null) ? model.createResource(subject) : (Resource) null,
                    (property != null) ? model.createProperty(property) : (Property) null,
                    (object != null) ? model.createResource(object) : (RDFNode) null
            );

            StmtIterator it = model.listStatements(selector);
            {
                while (it.hasNext()) {
                    Statement stmt = it.next();
                    results.add(stmt);
                }
            }

            ds.commit();
        } finally {
            ds.end();
        }

        return results;
    }

    public void addStatement(String subject, String property, String object) {
        Model model = null;

        ds.begin(ReadWrite.WRITE);
        try {
            model = ds.getNamedModel(ONTOLOGY_MODEL_NAME);

            Statement stmt = model.createStatement
                    (
                            model.createResource(subject),
                            model.createProperty(property),
                            model.createResource(object)
                    );

            model.add(stmt);
            ds.commit();
        } finally {
            ds.end();
        }
    }
    public void setOntologyPrefix() {
        Prefixes.ONTOLOGY_NS = ds.getNamedModel(ONTOLOGY_MODEL_NAME).getNsPrefixURI("wado");
    }
}
