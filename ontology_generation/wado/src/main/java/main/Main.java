package main;

import java.util.List;
import java.util.Map;

import connection_tdb.TDBRepository;
import github_provider.Classifier;
import github_provider.Repository;

public class Main {

    public static void main(String... argv) {

        TDBRepository manager = new TDBRepository();

//        Map<String, String> classes = manager.getAllClasses();
//		Map<String, Map<String, List<Repository>>> classification = Classifier.classifyRepositories(classes);
//		manager.insertInstances(classification);
        manager.printAll();
        /*
         * pentru ca ontologia se face o singura data sunt comentate liniile de mai sus in are se i au repositories si se clasifica
         */
    }

}
