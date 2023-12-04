package view;

import interface_adapter.ViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "sign up";
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField repeatPasswordField;
    private String[] countries = {"Canada", "USA", "..."};
    private JComboBox<String> countryComboBox;
    private final SignupViewModel viewModel;
    private final SignupController controller;

    private Font commonFont = new Font("Lato", Font.PLAIN, 15);
    private Color textColor = Color.BLACK;
    private Dimension commonTextFieldSize = new Dimension(150, 20);
    private Dimension commonButtonSize = new Dimension(100, 25);

    public SignupView(SignupViewModel viewModel, SignupController controller) {
        this.controller = controller;
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 0, 10, 0); // Added padding for y-axis

        URL logoUrl = getClass().getClassLoader().getResource("icons/SignUp.png");
        ImageIcon originalIcon = new ImageIcon(logoUrl);
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledIcon);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(logoLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(commonFont);
        usernameLabel.setForeground(textColor);
        add(usernameLabel, constraints);

        usernameField = new JTextField(15);
        usernameField.setFont(commonFont);
        usernameField.setPreferredSize(commonTextFieldSize);
        constraints.gridx = 1;
        add(usernameField, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(commonFont);
        passwordLabel.setForeground(textColor);
        add(passwordLabel, constraints);

        passwordField = new JPasswordField(15);
        passwordField.setFont(commonFont);
        passwordField.setPreferredSize(commonTextFieldSize);
        constraints.gridx = 1;
        add(passwordField, constraints);

        constraints.gridy = 3;
        constraints.gridx = 0;
        JLabel repeatPasswordLabel = new JLabel("Repeat Password");
        repeatPasswordLabel.setFont(commonFont);
        repeatPasswordLabel.setForeground(textColor);
        add(repeatPasswordLabel, constraints);

        repeatPasswordField = new JPasswordField(15);
        repeatPasswordField.setFont(commonFont);
        repeatPasswordField.setPreferredSize(commonTextFieldSize);
        constraints.gridx = 1;
        add(repeatPasswordField, constraints);

        constraints.gridy = 4;
        constraints.gridx = 0;
        JLabel countryLabel = new JLabel("Country");
        countryLabel.setFont(commonFont);
        countryLabel.setForeground(textColor);
        add(countryLabel, constraints);

        countryComboBox = new JComboBox<>(countries);
        countryComboBox.setFont(commonFont);
        constraints.gridx = 1;
        add(countryComboBox, constraints);

        constraints.gridy = 5;
        constraints.gridx = 0;
        JPanel buttonsPanel = getButtonsPanel();
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(buttonsPanel, constraints);

        setPreferredSize(new Dimension(300, 200));
        setVisible(true);
    }

    private void launchErrorMessage(String message) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/error.png"));
        JOptionPane.showMessageDialog(
                this,
                message,
                "Sign Up Error",
                JOptionPane.ERROR_MESSAGE,
                icon
        );
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        JButton signUpButton = new JButton("Sign up");
        signUpButton.setFont(commonFont);
        signUpButton.setPreferredSize(commonButtonSize);
        signUpButton.setBackground(new Color(126, 175, 252));
        signUpButton.setForeground(new Color(255, 255, 255));
        signUpButton.setOpaque(true);
        signUpButton.setContentAreaFilled(true);
        signUpButton.setBorderPainted(false);
        signUpButton.setFocusPainted(false);
        signUpButton.addActionListener(e -> onSubmit());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(commonFont);
        cancelButton.setPreferredSize(commonButtonSize);
        cancelButton.addActionListener(e -> onCancel());
        cancelButton.setBackground(new Color(219, 77, 96));
        cancelButton.setForeground(new Color(255, 255, 255));
        cancelButton.setOpaque(true);
        cancelButton.setContentAreaFilled(true);
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);

        buttonsPanel.add(signUpButton);
        buttonsPanel.add(cancelButton);
        return buttonsPanel;
    }

    private void onSubmit() {
        controller.handleSubmit(usernameField.getText(), new String(passwordField.getPassword()),
                new String(repeatPasswordField.getPassword()), (String) countryComboBox.getSelectedItem());
    }

    private void onCancel() {
        controller.handleCancel();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        // Action event handling
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();
        if (state.getDialogMessage() != null) {
            launchErrorMessage(state.getDialogMessage());
        }
    }
}
