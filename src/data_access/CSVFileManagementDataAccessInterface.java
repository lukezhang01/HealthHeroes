package data_access;

public interface CSVFileManagementDataAccessInterface {

    public void writeBasicCSV(String fileName, String csvData);

    public String getOutputDirectory();
}
