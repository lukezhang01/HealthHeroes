package data_access;

import java.io.IOException;

public interface FdaDatabaseAccessInterface {
    String getWarnings(String drugName) throws IOException;

    String getDescription(String drugName) throws IOException;

    String getInteractions(String drugName) throws IOException;

    String getPregnancy(String drugName) throws IOException;

    String getNursing(String drugName) throws IOException;
//  For nursing mothers
    String getUsage(String drugName) throws IOException;

    String getAbuse(String drugName) throws IOException;

    String getHandling(String drugName) throws IOException;

    String getReactions(String drugName) throws IOException;
}
