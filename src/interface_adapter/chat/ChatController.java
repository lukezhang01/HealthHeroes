package interface_adapter.chat;

import use_case.chat.ChatInputBoundary;
import use_case.chat.ChatInputData;

public class ChatController {

    final ChatInputBoundary chatUseCaseInteractor;


    public ChatController(ChatInputBoundary chatUseCaseInteractor) {
        this.chatUseCaseInteractor = chatUseCaseInteractor;
    }


    public void execute(String message, boolean isUser) {
        ChatInputData chatInputData = new ChatInputData(message, isUser);
        this.chatUseCaseInteractor.execute(chatInputData);
    }
}
