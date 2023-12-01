package use_case.chat;

public interface ChatOutputBoundary {
    void updateChat(String message, boolean isUser);

    void preparePopupView(String message);

    void setUserChatEnabled(boolean enabled);
}
