package test.use_case;

import use_case.chat.ChatOutputBoundary;

import java.util.ArrayList;

public class FakeChatOutputBoundary implements ChatOutputBoundary {
    ArrayList<String> chatUpdates = new ArrayList<>();
    boolean isUserChatEnabled;

    public void updateChat(String message, boolean isUser) {
        chatUpdates.add((isUser ? "User: " : "AI: ") + message);
    }

    public void setUserChatEnabled(boolean isEnabled) {
        isUserChatEnabled = isEnabled;
    }

    public void preparePopupView(String message) {
        chatUpdates.add("Popup: " + message);
    }
}
