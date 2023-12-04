package test.use_case;

import data_access.CSVFileManagementDataAccessInterface;
import data_access.CSVFileManagementDataAccessObject;

public class FakeCSVFileManagementDataAccessObject implements CSVFileManagementDataAccessInterface {

    @Override
    public void writeBasicCSV(String fileName, String csvData) {

    }

    @Override
    public String getOutputDirectory() {
        return null;
    }
}
