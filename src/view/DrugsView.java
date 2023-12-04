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
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setMaximumSize(ViewModel.VIEW_DIMENSION);
        this.setMinimumSize(ViewModel.VIEW_DIMENSION);
        this.fdaObject = new FdaDatabaseAccessObject();

        JPanel left = new JPanel(new GridBagLayout());
        JPanel right = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;



        String[] dropdownValues = {"Warnings","Description","Interactions","Pregnancy","Nursing(Mothers)","Usage","Abuse","Handling","Reactions"};
        JLabel recallTitle = new JLabel("Recent Recalls");
        recallTitle.setFont(new Font("Arial", Font.BOLD, 16));
        right.add(recallTitle, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;





        List<String> recalls = new ArrayList<>();
        List<Map<String,Object>> recallsMap = fdaObject.recentlyRecalled(5);
        for(int i=0;i<5;i++){
            recalls.add(recallsMap.get(i).get("product_description").toString());
        }


        addField(right,gbc,recalls.get(0));
        addField(right,gbc,recalls.get(1));
        addField(right,gbc,recalls.get(2));
        addField(right,gbc,recalls.get(3));
        addField(right,gbc,recalls.get(4));


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Drug Info");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));


        gbc.gridwidth = 1;
        gbc.gridy++;

        JComboBox<String> dropdown = new JComboBox<>(dropdownValues);
        JTextField inputField = new JTextField("Search",20);
        JButton submitButton = new JButton("Submit");
        JTextArea outputLabel = new JTextArea("");
        JScrollPane scrollPane = new JScrollPane(outputLabel);

        left.add(titleLabel);
        left.add(Box.createRigidArea(new Dimension(10, 0)));
        left.add(inputField);
        left.add(Box.createRigidArea(new Dimension(10, 0)));
        left.add(dropdown);
        left.add(Box.createRigidArea(new Dimension(10, 0)));
        left.add(submitButton);
        left.add(Box.createRigidArea(new Dimension(10, 0)));
        left.add(scrollPane);
        left.add(Box.createRigidArea(new Dimension(50, 0)));


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

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        right.setBackground(new Color(224, 224, 229));

        this.add(left,BorderLayout.WEST);
        this.add(right,BorderLayout.EAST);

        this.revalidate();
        this.repaint();
        this.setSize(600, 400);
        this.setVisible(true);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String labelText) {
        gbc.gridx = 0;
        JLabel label = new JLabel(labelText);
        panel.add(label, gbc);

        gbc.gridy++;
    }




    public void setPatientListView(PatientListView patientListView) {
        this.patientListView = patientListView;
    }


}


