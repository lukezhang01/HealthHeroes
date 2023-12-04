package test.use_case;

import data_access.ChatGPTChatDataAccessObject;
import data_access.ChatGPTHealthDataAccessInterface;
import data_access.ChatGPTPatientDataAwareDataAccessObject;
import entity.Patient;

public class FakeChatGPTChatDataAccessObject extends ChatGPTChatDataAccessObject {


    public FakeChatGPTChatDataAccessObject(Patient patient) {
        super(patient);
    }

    public String messageGPT(String message) {
        return "Mocked AI response for: " + message;
    }

    public String messageGPT(String message, String fdaData, boolean includeFdaData) {
        return "Mocked AI response with FDA data for: " + message;
    }
}