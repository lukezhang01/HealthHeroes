package interface_adapter.chat;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ChatState {


    private boolean popUpEnabled = false;

    private String popUpMessage = "";


    private String newMessage = "";

    private boolean isNewMessageFromUser = true;
    private boolean canUserChat = true;



    public ChatState(ChatState copy) {
        this.popUpEnabled = copy.popUpEnabled;
        this.popUpMessage = copy.popUpMessage;
        this.canUserChat = copy.canUserChat;
    }
    public ChatState(){}


    public void setIsNewMessageFromUser(boolean isFromUser){
        this.isNewMessageFromUser = isFromUser;
    }

    public boolean isNewMessageFromUser(){
        return this.isNewMessageFromUser;
    }

    public String getNewMessage() {
        return this.newMessage;
    }

    public void setNewMessage(String newMessage){
        this.newMessage = newMessage;
    }
    public void setCanUserChat(boolean canUserChat) {
        this.canUserChat = canUserChat;
    }

    public void setPopUpMessage(String message){
        this.popUpMessage = message;
    }

    public void setPopupEnabled(boolean state){
        this.popUpEnabled = state;
    }


    public boolean canUserChat() {
        return this.canUserChat;
    }
}
