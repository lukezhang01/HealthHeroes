package interface_adapter.chat;

import interface_adapter.ViewModel;
import interface_adapter.chat.ChatState;
import interface_adapter.login.LoginState;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChatViewModel extends ViewModel {
    public String TITLE_LABEL = "Patient Chat Assistant";

    public final Color BACKGROUND_COLOR = new Color(30, 33, 36);
    public final Color MESSAGE_COLOR = new Color(230, 230, 230);

    public final Color MESSAGE_BAR_COLOR = new Color(48, 50, 64);

    public final Color SEND_BUTTON_COLOR = new Color(82, 90, 110);
    public final Color INPUT_BAR_COLOR = new Color(33, 32, 33);

    public final Color USER_HEADER_COLOR = new Color(194, 224, 240);
    public final Color AI_HEADER_COLOR = new Color(215, 245, 196);

    public final String CHAT_LABEL = "SEND CHAT ➡";

    private ChatState state = new ChatState();


    public ChatViewModel() {
        super("chat view");
    }

    public void setState(ChatState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Login Presenter will call to let the ViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public ChatState getState() {
        return state;
    }

}
