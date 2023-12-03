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

public class DrugsView extends JPanel {
    public final String viewName = "Drugs";
    private PatientListView patientListView;

    private FdaDatabaseAccessObject fdaObject;

    public DrugsView(){
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





        this.revalidate();
        this.repaint();
        this.setSize(600, 400);
        this.setVisible(true);
    }


    public void setPatientListView(PatientListView patientListView) {
        this.patientListView = patientListView;
    }


}


