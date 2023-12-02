package view;

import interface_adapter.logged_in.LoggedInViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SandwichBar extends JPanel {
    private JFrame mainFrame;
    public JPanel sandwich;

    public SandwichBar(JFrame mainFrame) {
        setLayout(new BorderLayout());
        this.sandwich = createNavigationPanel();
        this.mainFrame = mainFrame;
    }

    private void createAndShowMainFrame() {
        mainFrame = new JFrame("Main Frame");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        JPanel navigationPanel = createNavigationPanel();
        mainFrame.getContentPane().add(navigationPanel, BorderLayout.WEST);

        HomeView homeView = new HomeView(new LoggedInViewModel());
        mainFrame.getContentPane().add(homeView, BorderLayout.CENTER);

        mainFrame.setVisible(true);
    }

    private JPanel createNavigationPanel() {
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));

        JButton homeButton = createNavigationButton("Home");
        JButton patientsButton = createNavigationButton("Patients");

        navigationPanel.add(homeButton);
        navigationPanel.add(patientsButton);

        return navigationPanel;
    }

    private JButton createNavigationButton(String text) {
        JButton button = new JButton(text);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchFrame(text);
            }
        });

        return button;
    }

    private void switchFrame(String view) {
        mainFrame.getContentPane().removeAll(); // Remove existing components

        JPanel navigationPanel = createNavigationPanel();
        mainFrame.getContentPane().add(navigationPanel, BorderLayout.WEST);

        if ("Home".equals(view)) {
            HomeView homeView = new HomeView(new LoggedInViewModel());
            mainFrame.getContentPane().add(createPanelForFrame(homeView), BorderLayout.CENTER);
        } else if ("Patients".equals(view)) {
            PatientListView patientsListView = new PatientListView();
            mainFrame.getContentPane().add(createPanelForFrame(patientsListView), BorderLayout.CENTER);
        }

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private JPanel createPanelForFrame(JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(frame.getContentPane(), BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            new SandwichBar();
        });
    }
}
