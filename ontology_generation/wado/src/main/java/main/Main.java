package main;

import connection_tdb.Prefixes;
import connection_tdb.TDBConnection;
import connection_tdb.TDBRepository;
import org.apache.jena.rdf.model.Statement;

import java.util.List;

public class Main {



    public static void main(String... argv) {

//        System.out.print("Size " + result.size());
//        for (Statement s : result) {
//            System.out.print(s.toString());
//        }

        TDBRepository manager = new TDBRepository();
//        manager.getAllClasses();
        System.out.print(Prefixes.ONTOLOGY_NS);
    }

}
