package main;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import github_provider.Classifier;
import github_provider.Repository;
=======
import connection_tdb.Prefixes;
import connection_tdb.TDBConnection;
import connection_tdb.TDBRepository;
import github_provider.Repository;
import org.apache.jena.rdf.model.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
>>>>>>> 5d9963ebaeae3f8caeff45f68fc21157608dfced

public class Main {


    public static void main(String... argv) {

//        System.out.print("Size " + result.size());
//        for (Statement s : result) {
//            System.out.print(s.toString());
//        }

//        TDBRepository manager = new TDBRepository();
//        manager.getAllClasses();
<<<<<<< HEAD
//        System.out.print(Prefixes.ONTOLOGY_NS);
    	
    	List<String> classes = new ArrayList<>();
		classes.add("java");

		Map<String, Map<String, List<Repository>>> classification = Classifier.classifyRepositories(classes);
		System.out.println(classification);
=======
        Map<String, Map<String, List<Repository>>> t = new HashMap<String, Map<String, List<Repository>>>();
        Map<String, List<Repository>> testTopic = new HashMap<String, List<Repository>>();
        Repository r = new Repository();
        List<Repository> liR = new ArrayList<Repository>();

        r.setDescription("Descrieretest");
        r.setLanguage("Java");
        r.setName("EntitateTest");
        r.setStars(4);
        r.setUrl("http://url.com");
        liR.add(r);
        testTopic.put("topic", liR);
        t.put("Book", testTopic);

//        manager.insertInstances(t);
        manager.printAll();
>>>>>>> 5d9963ebaeae3f8caeff45f68fc21157608dfced
    }

}
