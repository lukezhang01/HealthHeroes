package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

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

        public LoginView(LoginViewModel viewModel, LoginController controller) {
            this.viewModel = viewModel;
            this.controller = controller;
            viewModel.addPropertyChangeListener(this);

            setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.insets = new Insets(10, 10, 5, 10); // top, left, bottom, right padding

            // Username label and text field
            constraints.gridx = 0; // column 0
            constraints.gridy = 0; // row 0
            add(new JLabel("Username"), constraints);

            constraints.gridx = 1; // column 1
            usernameField = new JTextField(15);
            add(usernameField, constraints);

            // Password label and text field
            constraints.gridx = 0; // reset to column 0
            constraints.gridy = 1; // next row
            add(new JLabel("Password"), constraints);

            constraints.gridx = 1; // column 1
            passwordField = new JPasswordField(15);
            add(passwordField, constraints);

            // Login button centered below the text fields
            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(e -> onLogin());
            constraints.gridx = 0; // spans both columns
            constraints.gridy = 2; // next row
            constraints.gridwidth = 2; // span two columns
            constraints.anchor = GridBagConstraints.CENTER;
            add(loginButton, constraints);

            // Sign Up label and button in a panel for alignment
            JPanel signUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            signUpPanel.add(new JLabel("Are you a new user?"));

            JButton signUpButton = new JButton("Sign Up");
            signUpButton.addActionListener(e -> onSignUp());
            signUpPanel.add(signUpButton);

            // Adding the sign-up panel to the frame
            constraints.gridx = 0; // reset to column 0
            constraints.gridy = 3; // next row
            constraints.gridwidth = 2; // span two columns
            add(signUpPanel, constraints);

            // Set the frame size
            setPreferredSize(new Dimension(350, 200));
            setVisible(true);
        }

        private void onLogin() {
            // Get the user input from the fields
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Print the data
            System.out.println("Login button clicked");
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            controller.handleLogin(username, password);
        }

        private void onSignUp() {
            // Print the message
            System.out.println("Sign Up button clicked");

            controller.handleSignup();
        }
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        // check if there is an error state
        System.out.println(state);
        System.out.println(state.getDialogMessage());
        if (!state.getDialogMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getDialogMessage());
            state.setDialogMessage("");
        }
    }

    private void setFields(LoginState state) {
        usernameField.setText(state.getUsername());
    }


}