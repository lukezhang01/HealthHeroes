package interface_adapter.chat;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatViewModel;
import use_case.chat.ChatOutputBoundary;
import view.ChatView;

public class ChatPresenter implements ChatOutputBoundary {
    private final ChatViewModel chatViewModel;



    public ChatPresenter(ChatViewModel chatViewModel) {
        this.chatViewModel = chatViewModel;
    }


    public void preparePopupView(String message){
        ChatState chatState = this.chatViewModel.getState();
        chatState.setPopUpMessage(message);
        chatState.setPopupEnabled(true);
        this.chatViewModel.setState(chatState);
        this.chatViewModel.firePropertyChanged();
    }

    public void setUserChatEnabled(boolean enabled) {
        ChatState chatState = this.chatViewModel.getState();
        chatState.setCanUserChat(enabled);
        chatState.setNewMessage("");
        this.chatViewModel.setState(chatState);
        this.chatViewModel.firePropertyChanged();
    }

    @Override
    public void updateChat(String message, boolean isUser) {
        ChatState chatState = this.chatViewModel.getState();
        chatState.setNewMessage(message);
        chatState.setIsNewMessageFromUser(isUser);
        // update message
        this.chatViewModel.setState(chatState);
        this.chatViewModel.firePropertyChanged();
        // flip message turn
        chatState.setCanUserChat(!isUser);
        chatState.setIsNewMessageFromUser(!isUser);
        this.chatViewModel.setState(chatState);
    }


}
