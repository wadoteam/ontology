package connection_tdb;

public class Prefixes {
    public static final String RDF_TYPE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    public static final String OWL_CLASS = "http://www.w3.org/2002/07/owl#Class";


    private static final String WADO_PROGRAMING_LANGUAGE_SUFFIX = "ProgrammingLanguage";
    private static final String WADO_HAS_DESCRIPTION_SUFFIX = "hasDescription";


    public static  String WADO_PROGRAMING_LANGUAGE = "http://www.semanticweb.org/constantin/ontologies/2018/wado-awsome#ProgrammingLanguage";
    public static String ONTOLOGY_NS;



    public static final String WADO_LINK = "http://www.co-ode.org/ontologies/ont.owl#Link";
    public static final String WADO_HAS_LINK = "http://www.co-ode.org/ontologies/ont.owl#hasLink";
    public static final String WADO_HAS_STARS = "http://www.co-ode.org/ontologies/ont.owl#hasStars";
    public static String WADO_HAS_DESCRIPTION = "http://www.co-ode.org/ontologies/ont.owl#hasStars";
    public static String WADO_HAS_PRIORITY = "http://www.co-ode.org/ontologies/ont.owl#hasPriority";


    public static void createWadoPrefixes(String ns) {
        Prefixes.ONTOLOGY_NS = ns;
        Prefixes.WADO_PROGRAMING_LANGUAGE = ns + Prefixes.WADO_PROGRAMING_LANGUAGE_SUFFIX;
        Prefixes.WADO_HAS_DESCRIPTION = ns + Prefixes.WADO_HAS_DESCRIPTION_SUFFIX;
    }

}
