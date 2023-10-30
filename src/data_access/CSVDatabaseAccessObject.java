package data_access;
import entity.User;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CSVDatabaseAccessObject {
    private final File csvFile;
    private final String[] patient_headers = {"name", "password", "medication"};
    private Map<String, User> patients = new HashMap<>();

    public CSVDatabaseAccessObject(String file_path) throws IOException{
        csvFile = new File(file_path);
        if (csvFile.length() == 0) {
            return patients;
        }
    }
}
