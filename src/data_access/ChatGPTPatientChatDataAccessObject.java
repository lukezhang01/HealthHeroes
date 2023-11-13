package data_access;

import entity.Patient;


public class ChatGPTPatientChatDataAccessObject extends ChatGPTPatientDataAccessAbstractClass {
    public ChatGPTPatientChatDataAccessObject(Patient patient) {
        super(patient, "You are a doctor's assistant. You must keep your outputs as concise and accurate as possible. Do not format your output, keep the formatting simple. Remember you are chatting with a licensed doctor.");
    }
}
