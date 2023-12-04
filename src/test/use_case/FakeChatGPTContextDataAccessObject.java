package test.use_case;

import data_access.ChatGPTContextDataAccessObject;
import entity.Patient;

import java.util.*;

public class FakeChatGPTContextDataAccessObject extends ChatGPTContextDataAccessObject {
    private String[] apiCalls = {"getWarnings", "getPregnancy"};
    private String[] csvCommands = {"writeCSV", "readCSV"};

    public FakeChatGPTContextDataAccessObject(Patient patient) {
        super(patient);
    }

    public HashMap<String, ArrayList<String>> getDrugAPICallsFromResponse(String response) {
        HashMap<String, ArrayList<String>> calls = new HashMap<>();
        calls.put("DrugName", new ArrayList<>(Arrays.asList(apiCalls)));
        return calls;
    }

    public ArrayList<String> getCSVDataFromResponse(String response) {
        return new ArrayList<>(Arrays.asList(csvCommands));
    }
}
