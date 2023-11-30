package use_case.chat;

public class ChatOutputData {
    private final String currentMessage;
    private boolean isUserMessage;


    public boolean isUserMessage() {
        return this.isUserMessage;
    }

    public String getMessage() {
        return this.currentMessage;
    }
    public ChatOutputData(String message, boolean isUserMessage) {
        this.currentMessage = message;
        this.isUserMessage = isUserMessage;
    }

}
