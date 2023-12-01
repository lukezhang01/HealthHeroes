package use_case.chat;

import entity.Doctor;
import entity.Patient;

public class ChatInputData {

    final private boolean isUser;
    final private String message;


    public boolean isUser() {
        return this.isUser;
    }

    public String getMessage() {
        return this.message;
    }

    public ChatInputData(String message, boolean isUser) {
        this.message = message;
        this.isUser = isUser;
    }
}
