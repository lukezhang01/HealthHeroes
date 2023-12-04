package view;

import interface_adapter.chat.ChatController;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginState;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

public class ChatView extends JDialog implements ActionListener, PropertyChangeListener {


    private boolean canUserChat = true;
    private final ChatViewModel chatViewModel;

    private final Dimension CHAT_SIZE = new Dimension(600, 700);


    private final Dimension MESSAGE_SIZE = new Dimension(CHAT_SIZE.width/2, 60);

    private final JPanel mainPanel, inputPanel;
    private final JScrollPane scrollPane;

    private final JButton sendButton;
    private final JTextArea messageInput;

    private final ChatController chatController;


    public ChatView(ChatController chatController, ChatViewModel chatViewModel) {
        super((Frame) null, chatViewModel.TITLE_LABEL, false);
        this.chatViewModel = chatViewModel;
        // connect listener
        this.chatViewModel.addPropertyChangeListener(this);
        this.chatController = chatController;
        this.setMinimumSize(CHAT_SIZE);
        this.setLocationRelativeTo(null);
        // create UI components
        // create main frame
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        // create scrollPane
        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(chatViewModel.BACKGROUND_COLOR);
        mainPanel.setBackground(chatViewModel.BACKGROUND_COLOR);
        this.add(scrollPane, BorderLayout.CENTER);
        // create bottom panel
        inputPanel = new JPanel();
        inputPanel.setBackground(chatViewModel.MESSAGE_BAR_COLOR);
        messageInput = new JTextArea(3, 30);
        messageInput.setBackground(chatViewModel.INPUT_BAR_COLOR);
        messageInput.setForeground(chatViewModel.MESSAGE_COLOR);
        messageInput.setLineWrap(true);
        sendButton = new JButton(chatViewModel.CHAT_LABEL);
        sendButton.setBackground(chatViewModel.SEND_BUTTON_COLOR);
        sendButton.setForeground(new Color(255, 255, 255));
        sendButton.setOpaque(true);
        sendButton.setContentAreaFilled(true);
        sendButton.setBorderPainted(false);
        sendButton.setFocusPainted(false);
        // add it to the input panel
        inputPanel.add(messageInput);
        inputPanel.add(sendButton);
        inputPanel.setBackground(chatViewModel.MESSAGE_BAR_COLOR);
        inputPanel.setForeground(chatViewModel.MESSAGE_COLOR);
        this.add(inputPanel, BorderLayout.SOUTH);
        // listen to the sendButton
        sendButton.addActionListener(this);
        // enable popup
        this.setBackground(chatViewModel.BACKGROUND_COLOR);
        this.setVisible(true);
    }

    public void addMessage(String message, boolean isUser){
        // create indicator label
        JLabel indicatorLabel = new JLabel(isUser ? "\uD83D\uDC64 User" : "\uD83E\uDD16 Assistant");
        indicatorLabel.setFont(new Font("Monospace", Font.PLAIN, 14));
        indicatorLabel.setBackground(chatViewModel.BACKGROUND_COLOR);
        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setText(message);
        if (isUser) {
            indicatorLabel.setForeground(chatViewModel.USER_HEADER_COLOR);
            messageArea.setForeground(chatViewModel.USER_HEADER_COLOR);
        } else{
            messageArea.setForeground(chatViewModel.AI_HEADER_COLOR);
            indicatorLabel.setForeground(chatViewModel.AI_HEADER_COLOR);
        }
        // set icon
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/chat_icon.png"));
        if (icon.getImage() != null) {
            this.setIconImage(icon.getImage());
        } else {
            System.err.println("Icon could not be loaded.");
        }
        // Set the background of the message area to be transparent
        messageArea.setBackground(chatViewModel.BACKGROUND_COLOR);

        // adjust the messageArea size dynamically based on the content
        messageArea.setSize(MESSAGE_SIZE.width, Short.MAX_VALUE);
        messageArea.setPreferredSize(new Dimension(MESSAGE_SIZE.width, messageArea.getPreferredSize().height));

        // add the text to scroll
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setPreferredSize(messageArea.getPreferredSize());
        scrollPane.setMaximumSize(messageArea.getPreferredSize());
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        // Set the background of the JScrollPane to be transparent
        scrollPane.setBackground(chatViewModel.BACKGROUND_COLOR);
        scrollPane.getViewport().setBackground(chatViewModel.BACKGROUND_COLOR);

        // create a wrap panel for the jscrollpane
        JPanel messageWrap = new JPanel();
        messageWrap.setLayout(new BoxLayout(messageWrap, BoxLayout.X_AXIS));
        messageWrap.add(indicatorLabel);
        messageWrap.add(Box.createHorizontalStrut(8));
        messageWrap.add(scrollPane);
        messageWrap.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageWrap.setBackground(chatViewModel.BACKGROUND_COLOR);
        messageArea.setBackground(chatViewModel.BACKGROUND_COLOR);

        // create the container panel
        JPanel container = new JPanel(new BorderLayout());
        container.add(messageWrap, isUser ? BorderLayout.EAST : BorderLayout.WEST);
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.setBackground(chatViewModel.BACKGROUND_COLOR);
        container.setMaximumSize(new Dimension(CHAT_SIZE.width, container.getPreferredSize().height));

        // add the container to the main panel
        mainPanel.add(container);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
//
//        // set vertical gap to 0
//        mainPanel.add(Box.createRigidArea(new Dimension(0, 0)));

        // refresh ui
        mainPanel.revalidate();
        mainPanel.repaint();
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());


    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        String message = messageInput.getText();
        System.out.println(this.canUserChat);
        if (this.canUserChat && !message.isEmpty()) {
            // check if there's a message
            this.canUserChat = false;
            chatController.execute(messageInput.getText(), true);
            messageInput.setText("");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ChatState state = (ChatState) evt.getNewValue();
        this.canUserChat = state.canUserChat();
        if (!state.getNewMessage().isEmpty()) {
            this.addMessage(state.getNewMessage(), state.isNewMessageFromUser());
        }
    }
}