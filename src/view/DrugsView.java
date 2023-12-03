package view;

import javax.swing.*;
import data_access.FdaDatabaseAccessObject;
import interface_adapter.ViewModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrugsView extends JFrame {
    public final String viewName = "Drugs";
    private PatientListView patientListView;

    private HomeView homeView;

    private FdaDatabaseAccessObject fdaObject;

    public DrugsView(){

        super("Drugs");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setMaximumSize(ViewModel.VIEW_DIMENSION);
        this.setMinimumSize(ViewModel.VIEW_DIMENSION);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(73, 93, 135));
        JButton homeButton = new JButton("🏠Home");
        JButton patientButton = new JButton("☺Patients");
        JButton drugsButton = new JButton("\uD83D\uDC8A Drugs");
        homeButton.setBackground(new Color(99, 255, 147));
        homeButton.setForeground(new Color(45, 46, 45));
        homeButton.setFont(ViewModel.HEADING_FONT_BOLD);
        homeButton.setOpaque(true);
        homeButton.setContentAreaFilled(true);
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);
        patientButton.setBackground(new Color(99, 255, 147));
        patientButton.setForeground(new Color(45, 46, 45));
        patientButton.setFont(ViewModel.HEADING_FONT_BOLD);
        patientButton.setOpaque(true);
        patientButton.setContentAreaFilled(true);
        patientButton.setBorderPainted(false);
        patientButton.setFocusPainted(false);
        leftPanel.add(homeButton);
        leftPanel.add(patientButton);
        leftPanel.add(drugsButton);
        this.add(leftPanel, BorderLayout.WEST);

        patientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patientListView.setHomeView(homeView);
                patientListView.setDrugsView(DrugsView.this);
                patientListView.setVisible(true);
                DrugsView.this.setVisible(false);
                patientListView.revalidate();
                patientListView.repaint();
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeView.setPatientListView(patientListView);
                homeView.setDrugsView(DrugsView.this);
                homeView.setVisible(true);
                DrugsView.this.setVisible(false);
                homeView.revalidate();
                homeView.repaint();
            }
        });

        this.revalidate();
        this.repaint();
        this.setSize(600, 400);
        this.setVisible(true);
    }


    public void setPatientListView(PatientListView patientListView) {
        this.patientListView = patientListView;
    }



    public void setHomeView(HomeView homeView){
        this.homeView = homeView;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DrugsView();

        });
    }
}


