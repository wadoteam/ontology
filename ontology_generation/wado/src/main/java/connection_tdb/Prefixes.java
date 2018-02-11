package connection_tdb;

public class Prefixes {
    public static final String RDF_TYPE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    public static final String OWL_CLASS = "http://www.w3.org/2002/07/owl#Class";
    public static final String OWL_OBJECT_PROPERTY = "http://www.w3.org/2002/07/owl#ObjectProperty";
    public static final String OWL_DATATYPE_PROPERTY = "http://www.w3.org/2002/07/owl#DatatypeProperty";



    public static final String WADO_PROGRAMING_LANGUAGE = "Language";
    public static final String WADO_ISSUE = "Issue";
    public static final String WADO_LICENSE = "License";
    public static final String WADO_REPOSITORY = "Repository";
    
    public static final String WADO_HAS_REPOSITORY = "hasRepository";
    public static final String WADO_HAS_DESCRIPTION = "hasDescription";
    public static final String WADO_HAS_LINK = "hasLink";
    public static final String WADO_HAS_STARS = "hasStars";
    public static final String WADO_HAS_PRIORITY = "hasPriority";
    public static final String WADO_HAS_ISSUE = "hasIssue";
    public static final String WADO_HAS_CREATED_DATE= "hasCreatedDate";
    public static final String WADO_IS_CLOSED= "isClosed";
    public static final String WADO_HAS_LICENSE= "hasLicense";
    public static final String WADO_HAS_PRIMARY_LANGUAGE= "hasPrimaryLanguage";

    public static String ONTOLOGY_NS;


    public static void createWadoPrefixes(String ns) {
        Prefixes.ONTOLOGY_NS = ns;
    }

}
