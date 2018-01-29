package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import github_provider.Classifier;
import github_provider.Repository;

public class Main {



    public static void main(String... argv) {

//        System.out.print("Size " + result.size());
//        for (Statement s : result) {
//            System.out.print(s.toString());
//        }

//        TDBRepository manager = new TDBRepository();
//        manager.getAllClasses();
//        System.out.print(Prefixes.ONTOLOGY_NS);
    	
    	List<String> classes = new ArrayList<>();
		classes.add("java");

		Map<String, Map<String, List<Repository>>> classification = Classifier.classifyRepositories(classes);
		System.out.println(classification);
    }

}
