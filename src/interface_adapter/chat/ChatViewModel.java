package interface_adapter.chat;

import interface_adapter.ViewModel;
import interface_adapter.chat.ChatState;
import interface_adapter.login.LoginState;

import javax.swing.text.View;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChatViewModel extends ViewModel {
    public String TITLE_LABEL = "Patient Chat Assistant";

    public final Color BACKGROUND_COLOR = ViewModel.BACKGROUND_COLOR;
    public final Color MESSAGE_COLOR = ViewModel.TEXT_COLOR;

    public final Color MESSAGE_BAR_COLOR = ViewModel.HEADER_COLOR;

    public final Color SEND_BUTTON_COLOR = ViewModel.TEXT_HIGHLIGHT_COLOR;
    public final Color INPUT_BAR_COLOR = ViewModel.TEXT_SECONDARY_COLOR;

    public final Color USER_HEADER_COLOR = new Color(13, 131, 197);
    public final Color AI_HEADER_COLOR = new Color(93, 126, 69);

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
