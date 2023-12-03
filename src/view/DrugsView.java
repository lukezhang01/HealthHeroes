package view;

import javax.swing.*;
import data_access.FdaDatabaseAccessObject;
import data_access.FdaDatabaseAccessInterface;
import interface_adapter.ViewModel;

import java.util.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
        this.fdaObject = new FdaDatabaseAccessObject();

        JPanel centerPanel = new JPanel();
        String[] dropdownValues = {"Warnings","Description","Interactions","Pregnancy","Nursing(Mothers)","Usage","Abuse","Handling","Reactions"};
        JComboBox<String> dropdown = new JComboBox<>(dropdownValues);
        JTextField inputField = new JTextField(20);
        JButton submitButton = new JButton("Submit");
        JTextArea outputLabel = new JTextArea("");


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();
                String selectedOption = (String) dropdown.getSelectedItem();
                if(Objects.equals(selectedOption, "Warnings")){
                    outputLabel.setText(fdaObject.getWarnings(inputText));
                }
                else if(Objects.equals(selectedOption, "Description")){
                    outputLabel.setText(fdaObject.getDescription(inputText));
                }
                else if(Objects.equals(selectedOption, "Interactions")){
                    outputLabel.setText(fdaObject.getInteractions(inputText));
                }
                else if(Objects.equals(selectedOption, "Pregnancy")){
                    outputLabel.setText(fdaObject.getPregnancy(inputText));
                }
                else if(Objects.equals(selectedOption, "Nursing(Mothers)")){
                    outputLabel.setText(fdaObject.getNursing(inputText));
                }
                else if(Objects.equals(selectedOption, "Usage")){
                    outputLabel.setText(fdaObject.getUsage(inputText));
                }
                else if(Objects.equals(selectedOption, "Abuse")){
                    outputLabel.setText(fdaObject.getAbuse(inputText));
                }
                else if(Objects.equals(selectedOption, "Handling")){
                    outputLabel.setText(fdaObject.getHandling(inputText));
                }
                else if(Objects.equals(selectedOption, "Reactions")){
                    outputLabel.setText(fdaObject.getReactions(inputText));
                }

            }
        });
        outputLabel.setLineWrap(true);
        outputLabel.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputLabel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(200, 100));

        JLabel recallTitle = new JLabel("Recent Recalls");
        List<String> recalls = new ArrayList<>();
        List<Map<String,Object>> recallsMap = fdaObject.recentlyRecalled(5);
        for(int i=0;i<5;i++){
            recalls.add(recallsMap.get(i).get("product_description").toString());
        }

        JTextArea recallText1 = new JTextArea(recalls.get(0));
        JTextArea recallText2 = new JTextArea(recalls.get(1));
        JTextArea recallText3 = new JTextArea(recalls.get(2));
        JTextArea recallText4 = new JTextArea(recalls.get(3));
        JTextArea recallText5 = new JTextArea(recalls.get(4));

        recallText1.setLineWrap(true);
        recallText2.setLineWrap(true);
        recallText3.setLineWrap(true);
        recallText4.setLineWrap(true);
        recallText5.setLineWrap(true);

        centerPanel.add(inputField);
        centerPanel.add(submitButton);
        centerPanel.add(dropdown);
        centerPanel.add(scrollPane);
        centerPanel.add(recallTitle);
        centerPanel.add(recallText1);
        centerPanel.add(recallText2);
        centerPanel.add(recallText3);
        centerPanel.add(recallText4);
        centerPanel.add(recallText5);
        this.add(centerPanel,BorderLayout.CENTER);


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


