package connection_tdb;

import github_provider.Repository;
import org.apache.jena.rdf.model.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TDBRepository {
    private TDBConnection conn;

    public TDBRepository() {
        conn = new TDBConnection();
//        conn.loadModel();
        conn.setOntologyPrefix();

    }

    public List<String> getAllClasses() {
        List<Statement> classesTriple = this.conn.getStatements(null, Prefixes.RDF_TYPE, Prefixes.OWL_CLASS);
        List<String> classes = new ArrayList<String>();
        for (Statement triple : classesTriple) {
            if (triple.getSubject().getLocalName() != null) {
                classes.add(Utils.format(triple.getSubject().getLocalName()));
            }
        }
        System.out.print(classes);
        return classes;

    }

    public void insertClassInstance(Map<String, List<Repository>> entity) {

    }
}
