package view;
import interface_adapter.login.LoginController;
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
import javax.swing.*;
import java.awt.*;

public class SignupView extends JFrame implements ActionListener, PropertyChangeListener{
    public final String viewName = "sign up";
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField repeatPasswordField;
    private String[] countries = {"Canada", "USA", "..."};
    private JComboBox<String> countryComboBox;
    private final SignupViewModel viewModel;
    private final SignupController controller;

    public SignupView(SignupViewModel viewModel, SignupController controller) {
        super("Signup Screen");
        this.controller = controller;
        this.viewModel = viewModel;

        // Set the default close operation and layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Username field
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new JLabel("User name"), constraints);

        usernameField = new JTextField(15);
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(usernameField, constraints);

        // Password field
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(new JLabel("Password"), constraints);

        passwordField = new JPasswordField(15);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(passwordField, constraints);

        // Repeat Password field
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(new JLabel("Repeat password"), constraints);

        repeatPasswordField = new JPasswordField(15);
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(repeatPasswordField, constraints);

        // Country combo box
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(new JLabel("Country"), constraints);

        countryComboBox = new JComboBox<>(countries);
        constraints.gridx = 1;
        constraints.gridy = 3;
        add(countryComboBox, constraints);

        JPanel buttonsPanel = getButtonsPanel();

        // Centering the buttons panel in the frame
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2; // Span across two columns
        constraints.fill = GridBagConstraints.NONE;  // Override any previous fill settings
        constraints.anchor = GridBagConstraints.CENTER; // Center the panel
        add(buttonsPanel, constraints);

        // Set the frame size
        setPreferredSize(new Dimension(300, 200));

        // Pack and display the window
        pack();
        setVisible(true);
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 row, 2 cols, 10px horizontal gap

        JButton signUpButton = new JButton("Sign up");
        signUpButton.addActionListener(e -> onSubmit());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> onCancel());

        // Set preferred size for the buttons to be the same
        Dimension buttonSize = new Dimension(100, 25); // Set the desired size for both buttons
        signUpButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);

        // Add buttons to the panel
        buttonsPanel.add(signUpButton);
        buttonsPanel.add(cancelButton);
        return buttonsPanel;
    }

    private void onSubmit() {
        // Get the user input from the fields
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String repeatPassword = new String(repeatPasswordField.getPassword());
        String country = (String) countryComboBox.getSelectedItem();

        // Print the data
        System.out.println("Signup button clicked");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Repeat Password: " + repeatPassword);
        System.out.println("Country: " + country);

        // Submit
        controller.handleSubmit(username, password, repeatPassword, country);
    }

    private void onCancel() {
        // Print the message
        System.out.println("Cancel button clicked");

        controller.handleCancel();
    }
    public void actionPerformed(ActionEvent evt) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();
        if (state.getDialogMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getDialogMessage());
        }
    }
}

