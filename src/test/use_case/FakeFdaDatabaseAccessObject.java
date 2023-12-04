package test.use_case;

import data_access.FdaDatabaseAccessInterface;
import data_access.FdaDatabaseAccessObject;

public class FakeFdaDatabaseAccessObject implements FdaDatabaseAccessInterface {
    private String[] warnings = {"warning 1", "warning 2"};

    public String getWarnings(String drugName) {
        // only for simplicity
        return warnings[0];
    }

    @Override
    public String getDescription(String drugName) {
        return null;
    }

    @Override
    public String getInteractions(String drugName) {
        return null;
    }

    @Override
    public String getPregnancy(String drugName) {
        return null;
    }

    @Override
    public String getNursing(String drugName) {
        return null;
    }

    @Override
    public String getUsage(String drugName) {
        return null;
    }

    @Override
    public String getAbuse(String drugName) {
        return null;
    }

    @Override
    public String getHandling(String drugName) {
        return null;
    }

    @Override
    public String getReactions(String drugName) {
        return null;
    }
}
