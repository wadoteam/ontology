package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntResource;

import connection_tdb.TDBRepository;
import connection_tdb.Utils;
import github_provider.Classifier;
import github_provider.Repository;

public class Main {

    public static void main(String... argv) {

        TDBRepository manager = new TDBRepository();
        /*
         * pentru ca ontologia se face o singura data sunt comentate liniile de
         * mai sus in are se i au repositories si se clasifica
         */

//        Map<String, String> classes = manager.getAllClasses();
//        int nr=0;
//        while (Classifier.hasNext()) {
//            Map<String, Map<String, List<Repository>>> classification = Classifier.classifyRepositories(classes);
//            System.out.println("A");
//            manager.insertInstances(classification);
//            
//            System.out.println("B");
//            manager.syncDataset();
//            
//            System.out.println("C");
//            saveInstances(manager);
//            
//            System.out.println((nr++)+" "+classification);
//        }
        
        manager.addFramework("Spring", "Java");
        manager.addFramework("Angular", "JavaScript");
        manager.addFramework("Meteor", "JavaScript");
        manager.addFramework("Vue", "JavaScript");
        manager.addFramework("Ember", "JavaScript");

    }

    private static void saveInstances(TDBRepository manager) {
        Map<String, List<OntResource>> instances = new HashMap<>();
        List<OntProperty> properties = manager.getAllProperties();
        for (OntProperty property : properties) {
            List<OntClass> domain = manager.getDomainsClassesFor(property);
            List<OntClass> range = manager.getRangeClassesFor(property);

            List<OntResource> domainInstances;
            List<OntResource> rangeInstances;

            domainInstances = getInstancesFor(manager, instances, domain);
            rangeInstances = getInstancesFor(manager, instances, range);
            for (OntResource d : domainInstances) {
                for (OntResource r : rangeInstances) {
                    String description = manager.readDescription(r);
                    if (description.matches("(.*[^a-zA-Z])?" + Utils.format(d.getLocalName()) + "([^a-zA-Z].*)?")) {
                        manager.insertProperty(d, property, r);
                    }
                }
            }
        }
    }

    private static List<OntResource> getInstancesFor(TDBRepository manager, Map<String, List<OntResource>> instances,
                                                     List<OntClass> domain) {
        List<OntResource> domainInstances = new ArrayList<>();
        for (OntClass d : domain) {
            if (!instances.containsKey(d.getLocalName())) {
                List<OntResource> r = manager.readInstanceFor(d);
                domainInstances.addAll(r);
                instances.put(d.getLocalName(), r);
            } else {
                domainInstances.addAll(instances.get(d.getLocalName()));
            }
        }
        return domainInstances;
    }

}
