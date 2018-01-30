package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import connection_tdb.TDBRepository;
import github_provider.Classifier;
import github_provider.Repository;

public class Main {

    public static void main(String... argv) {

//        System.out.print("Size " + result.size());
//        for (Statement s : result) {
//            System.out.print(s.toString());
//        }

        TDBRepository manager = new TDBRepository();
//        manager.getAllClasses();
//
//        Map<String, Map<String, List<Repository>>> t = new HashMap<String, Map<String, List<Repository>>>();
//        Map<String, List<Repository>> testTopic = new HashMap<String, List<Repository>>();
//        Repository r = new Repository();
//        List<Repository> liR = new ArrayList<Repository>();
//
//        r.setDescription("Descrieretest");
//        r.setLanguage("Java");
//        r.setName("EntitateTest");
//        r.setStars(4);
//        r.setUrl("http://url.com");
//        liR.add(r);
//        testTopic.put("topic", liR);
//        t.put("Book", testTopic);
//
//        manager.insertInstances(t);
    	
//    	List<String> classes = new ArrayList<>();
//		classes.add("java");
//		classes.add("javascript");

		Map<String, Map<String, List<Repository>>> classification = Classifier.classifyRepositories(manager.getAllClasses());
		manager.insertInstances(classification);
//		System.out.println(classification);
    }

}
