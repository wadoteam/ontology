package connection_tdb;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;

import java.util.ArrayList;
import java.util.List;

public class TDBConnection {
	private static String ONTOLOGY_PATH_TDB = "../../../../ontology";
	private static final String ONTOLOGY_MODEL_NAME = "wado";
	private static String ONTOLOGY_FILES;
	private Dataset ds;

	public TDBConnection() {
		ClassLoader classLoader = getClass().getClassLoader();
		ONTOLOGY_FILES = classLoader.getResource("wado-ontology-rdf.owl").toString();
		ONTOLOGY_PATH_TDB = System.getProperty("user.dir") + "/ontology";
		ds = TDBFactory.createDataset(ONTOLOGY_PATH_TDB);
	}

	public void syncDataset() {
		TDB.sync(ds);
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
		model = ds.getNamedModel(ONTOLOGY_MODEL_NAME);
		Selector selector = new SimpleSelector((subject != null) ? model.createResource(subject) : (Resource) null,
				(property != null) ? model.createProperty(property) : (Property) null,
				(object != null) ? model.createResource(object) : (RDFNode) null);
		StmtIterator it = model.listStatements(selector);

		while (it.hasNext()) {
			try { 
				TDB.sync(ds);
				Statement stmt = it.next();
				results.add(stmt);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		ds.end();

		return results;
	}

	public void addStatement(String subject, String property, String object) {
		Model model = ds.getNamedModel(ONTOLOGY_MODEL_NAME);

		Statement stmt = model.createStatement(model.createResource(subject), model.createProperty(property),
				model.createResource(object));

		this.add(stmt);
	}

	public void addLiteral(String subject, String property, Literal object) {
		Model model = ds.getNamedModel(ONTOLOGY_MODEL_NAME);

		Statement stmt = model.createStatement(model.createResource(subject), model.createProperty(property), object);
		this.add(stmt);

	}

	private void add(Statement stmt) {
		Model model = null;
		ds.begin(ReadWrite.WRITE);
		model = ds.getNamedModel(ONTOLOGY_MODEL_NAME);
		model.add(stmt);
		ds.commit();
		ds.end();

	}

	public void setOntologyPrefixes() {
		Prefixes.createWadoPrefixes("http://webprotege.stanford.edu/project/BNheXqpQbFxGhfZ0oWHJp1#");
		// Prefixes.createWadoPrefixes(ds.getNamedModel(ONTOLOGY_MODEL_NAME).getNsPrefixURI("wado"));
	}

	public OntModel getModel() {
		ds.begin(ReadWrite.READ);
		OntModel a = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, ds.getNamedModel(ONTOLOGY_MODEL_NAME));
		ds.end();
		return a;
	}
}
