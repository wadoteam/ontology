package main;

import connection_tdb.TDBRepository;
import connection_tdb.Utils;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String... argv) {

        TDBRepository manager = new TDBRepository();
        /*
         * pentru ca ontologia se face o singura data sunt comentate liniile de mai sus in are se i au repositories si se clasifica
         */


//        Map<String, String> classes = manager.getAllClasses();
//        Map<String, Map<String, List<Repository>>> classification = Classifier.classifyRepositories(classes);
//        manager.insertInstances(classification);

        saveInstances(manager);
        //        for (OntProperty pp : properties) {
//            System.out.println(pp.getURI());
//            System.out.println(manager.getDomainsClassesFor(pp));
//            System.out.println("--------------------");
//        }
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
            System.out.println(domain);
            System.out.println(property);
            rangeInstances = getInstancesFor(manager, instances, range);
//            for (OntResource d : domainInstances) {
//                for (OntResource r : rangeInstances) {
//                    String description = manager.readDescription(r);
//                    if (description.contains(Utils.format(d.getLocalName()))) {
//                        System.out.println(d + " " + property + " " + r);
////                        manager.insertProperty(d,property, r);
//                    }
//                }
//            }
        }
    }

    private static List<OntResource> getInstancesFor(TDBRepository manager, Map<String, List<OntResource>> instances, List<OntClass> domain) {
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
