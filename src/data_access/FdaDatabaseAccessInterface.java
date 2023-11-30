package data_access;


public interface FdaDatabaseAccessInterface {
    String getWarnings(String drugName) ;

    String getDescription(String drugName) ;

    String getInteractions(String drugName) ;

    String getPregnancy(String drugName) ;

    String getNursing(String drugName) ;
//  For nursing mothers
    String getUsage(String drugName) ;

    String getAbuse(String drugName) ;

    String getHandling(String drugName) ;

    String getReactions(String drugName) ;
}
