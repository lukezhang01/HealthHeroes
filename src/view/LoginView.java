package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;



public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "log in";

    private JTextField usernameField;
    private JPasswordField passwordField;
    private final LoginViewModel viewModel;
    private final LoginController controller;

    private void launchErrorMessage(String message){
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/error.png"));
        JOptionPane.showMessageDialog(
                this,
                message,
                "Sign In Error",
                JOptionPane.ERROR_MESSAGE,
                icon
        );
    }

    public LoginView(LoginViewModel viewModel, LoginController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
        viewModel.addPropertyChangeListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 5, 10); // top, left, bottom, right padding

        // Logo setup
        URL logoUrl = getClass().getClassLoader().getResource("icons/HealthHeroesLogo.png");
        ImageIcon originalIcon = new ImageIcon(logoUrl);
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH); // adjust width and height as needed
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledIcon);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(logoLabel, constraints);
        add(Box.createHorizontalStrut(12));

        constraints.gridwidth = 1; // Reset gridwidth

        // Username label and text field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Lato", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(usernameLabel, constraints);

        usernameField = new JTextField(15);
        constraints.gridx = 1;
        add(usernameField, constraints);

        // Password label and text field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Lato", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(passwordLabel, constraints);

        passwordField = new JPasswordField(15);
        constraints.gridx = 1;
        add(passwordField, constraints);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Lato", Font.BOLD, 14));
        loginButton.setBackground(new Color(105, 191, 128));
        loginButton.setOpaque(true);
        loginButton.setContentAreaFilled(true);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> onLogin());
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(loginButton, constraints);

        // Sign Up label and button
        JLabel newUserLabel = new JLabel("Are you a new user?");
        newUserLabel.setFont(newUserLabel.getFont().deriveFont(Font.BOLD));
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(new Color(126, 175, 252));
        signUpButton.setOpaque(true);
        signUpButton.setContentAreaFilled(true);
        signUpButton.setBorderPainted(false);
        signUpButton.setFocusPainted(false);
        signUpButton.setForeground(Color.WHITE);
        signUpButton.addActionListener(e -> onSignUp());
        JPanel signUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signUpPanel.add(newUserLabel);
        signUpPanel.add(Box.createHorizontalStrut(9));
        signUpPanel.add(signUpButton);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        add(signUpPanel, constraints);

        setPreferredSize(new Dimension(350, 200));
        setVisible(true);
    }

    private void onLogin() {
        // Handle login
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        controller.handleLogin(username, password);
        usernameField.setText("");
        passwordField.setText("");
    }

    private void onSignUp() {
        // Handle sign up
        controller.handleSignup();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Handle property changes
        LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        if (state.getDialogMessage() != null && !state.getDialogMessage().isEmpty()) {
            launchErrorMessage(state.getDialogMessage());
            state.setDialogMessage("");
        }
    }

    private void setFields(LoginState state) {
        usernameField.setText(state.getUsername());
    }
}
