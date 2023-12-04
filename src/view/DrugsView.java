package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.View;


import data_access.FdaDatabaseAccessObject;
import data_access.FdaDatabaseAccessInterface;
import interface_adapter.ViewModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DrugsView extends JPanel {
    public final String viewName = "Drugs";
    private PatientListView patientListView;

    private FdaDatabaseAccessObject fdaObject;

    public DrugsView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setMaximumSize(ViewModel.VIEW_DIMENSION);
        this.setMinimumSize(ViewModel.VIEW_DIMENSION);
        this.fdaObject = new FdaDatabaseAccessObject();

        JPanel title = new JPanel(new GridBagLayout());
        JPanel left = new JPanel(new GridBagLayout());
        JPanel right = new JPanel(new GridBagLayout());
        JPanel centerBox = new JPanel(new GridBagLayout());
        JPanel bottomBox = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;


        String[] dropdownValues = {"Warnings", "Description", "Interactions", "Pregnancy", "Nursing(Mothers)", "Usage", "Abuse", "Handling", "Reactions"};
        JLabel recallTitle = new JLabel("Recent Recalls");
        recallTitle.setFont(new Font("Arial", Font.BOLD, 16));
        right.add(recallTitle, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;


        List<String> recalls = new ArrayList<>();
        List<Map<String, Object>> recallsMap = fdaObject.recentlyRecalled(5);
        for (int i = 0; i < 5; i++) {
            recalls.add((i+1) + "." + recallsMap.get(i).get("product_description").toString());
        }


        addField(bottomBox, gbc, recalls.get(0));
        addField(bottomBox, gbc, recalls.get(1));
        addField(bottomBox, gbc, recalls.get(2));
        addField(bottomBox, gbc, recalls.get(3));
        addField(bottomBox, gbc, recalls.get(4));


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Drug Info");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));


        gbc.gridwidth = 1;
        gbc.gridy++;

        JComboBox<String> dropdown = new JComboBox<>(dropdownValues);
        JTextField inputField = new JTextField("Ibuprofen", 20);
        JButton submitButton = new JButton();
        JTextArea outputLabel = new JTextArea("[Warnings Allergy alert: Ibuprofen may cause a severe allergic reaction, especially in people allergic to aspirin. Symptoms may include: rash facial swelling asthma (wheezing) hives skin reddening shock blisters If an allergic reaction occurs, stop use and seek medical help right away. Stomach bleeding warning: This product contains an NSAID, which may cause severe stomach bleeding. The chance is higher if you: take more or for a longer time than directed take a blood thinning (anticoagulant) or steroid drug have had stomach ulcers or bleeding problems have 3 or more alcoholic drinks every day while using this product are age 60 or older take other drugs containing prescription or nonprescription NSAIDs [aspirin, ibuprofen, naproxen, or others] Heart attack and stroke warning: NSAIDs, except aspirin, increase the risk of heart attack, heart failure, and stroke. These can be fatal. The risk is higher if you use more than directed or for longer than directed. Do not use if you have ever had an allergic reaction to any other pain reliever/fever reducer right before or after heart surgery Ask a doctor before use if stomach bleeding warning applies to you you have a history of stomach problems, such as heartburn you have high blood pressure, heart disease, liver cirrhosis, kidney disease, asthma, or had a stroke you are taking a diuretic you have problems or serious side effects from taking pain relievers or fever reducers Ask a doctor or pharmacist before use if you are under a doctor's care for any serious condition taking aspirin for heart attack or stroke, because ibuprofen may decrease this benefit of aspirin taking any other drug When using this product take with food or milk if stomach upset occurs Stop use and ask a doctor if you experience any of the following signs of stomach bleeding: feel faint have bloody or black stools vomit blood have stomach pain that does not get better you have symptoms of heart problems or stroke chest pain slurred speech leg swelling trouble breathing weakness in one part or side of body pain gets worse or lasts more than 10 days fever gets worse or lasts more than 3 days redness or swelling is present in the painful area any new symptoms appear If pregnant or breast-feeding, ask a health professional before use. It is especially important not to use ibuprofen at 20 weeks or later in pregnancy unless definitely directed to do so by a doctor because it may cause problems in the unborn child or complications during delivery. Keep out of reach of children. In case of overdose, get medical help or contact a Poison Control Center right away.]");
        JScrollPane scrollPane = new JScrollPane(outputLabel);

        JLabel Search = new JLabel("Drug Name");
        Search.setFont(ViewModel.HEADING_FONT_BOLD);
        Search.setForeground(ViewModel.TEXT_HIGHLIGHT_COLOR);
        Search.setBackground(ViewModel.BACKGROUND_COLOR);

        JLabel Info = new JLabel("Drug Info");
        Info.setFont(ViewModel.HEADING_FONT_BOLD);
        Info.setForeground(ViewModel.TEXT_HIGHLIGHT_COLOR);
        Info.setBackground(ViewModel.BACKGROUND_COLOR);


        left.add(Search);
        left.add(Box.createRigidArea(new Dimension(10, 0)));
        left.add(inputField);
        left.add(Box.createRigidArea(new Dimension(10, 0)));
        left.add(dropdown);
        left.add(Box.createRigidArea(new Dimension(10, 0)));
        left.add(submitButton);
        left.add(Box.createRigidArea(new Dimension(10, 0)));
        centerBox.add(Info);
        centerBox.add(Box.createRigidArea(new Dimension(10, 0)));
        centerBox.add(scrollPane);

        submitButton.setOpaque(false);
        submitButton.setBackground(new Color(69, 130, 222));
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setFont(ViewModel.HEADING_FONT_BOLD);
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setMinimumSize(new Dimension(30, 70));
        submitButton.setMaximumSize(new Dimension(30, 70));
        submitButton.setText("🔎 Find Info");
        submitButton.setOpaque(true);
        submitButton.setContentAreaFilled(true);
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();
                String selectedOption = (String) dropdown.getSelectedItem();
                if (Objects.equals(selectedOption, "Warnings")) {
                    outputLabel.setText(fdaObject.getWarnings(inputText));
                } else if (Objects.equals(selectedOption, "Description")) {
                    outputLabel.setText(fdaObject.getDescription(inputText));
                } else if (Objects.equals(selectedOption, "Interactions")) {
                    outputLabel.setText(fdaObject.getInteractions(inputText));
                } else if (Objects.equals(selectedOption, "Pregnancy")) {
                    outputLabel.setText(fdaObject.getPregnancy(inputText));
                } else if (Objects.equals(selectedOption, "Nursing(Mothers)")) {
                    outputLabel.setText(fdaObject.getNursing(inputText));
                } else if (Objects.equals(selectedOption, "Usage")) {
                    outputLabel.setText(fdaObject.getUsage(inputText));
                } else if (Objects.equals(selectedOption, "Abuse")) {
                    outputLabel.setText(fdaObject.getAbuse(inputText));
                } else if (Objects.equals(selectedOption, "Handling")) {
                    outputLabel.setText(fdaObject.getHandling(inputText));
                } else if (Objects.equals(selectedOption, "Reactions")) {
                    outputLabel.setText(fdaObject.getReactions(inputText));
                }

            }
        });
        outputLabel.setLineWrap(true);
        outputLabel.setWrapStyleWord(true);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500,100));

        titleLabel.setFont(ViewModel.TITLE_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        titleLabel.setBackground(ViewModel.HEADER_COLOR);
        titleLabel.setForeground(ViewModel.TEXT_COLOR);

        recallTitle.setFont(ViewModel.TITLE_FONT);
        recallTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        recallTitle.setAlignmentY(Component.TOP_ALIGNMENT);
        recallTitle.setBackground(ViewModel.HEADER_COLOR);
        recallTitle.setForeground(ViewModel.TEXT_COLOR);

        title.add(titleLabel);
        title.setPreferredSize(new Dimension((int) ViewModel.VIEW_DIMENSION.getWidth(),40));
        left.setPreferredSize(new Dimension((int) ViewModel.VIEW_DIMENSION.getWidth(),50));
        centerBox.setPreferredSize(new Dimension((int) ViewModel.VIEW_DIMENSION.getWidth(),400));
        right.setPreferredSize(new Dimension((int) ViewModel.VIEW_DIMENSION.getWidth(),100));
        bottomBox.setPreferredSize(new Dimension((int) ViewModel.VIEW_DIMENSION.getWidth(),300));

        bottomBox.setBackground(new Color(212,213,214));
        left.setBackground(new Color(255, 255, 255));
        title.setBackground(new Color(255,255,255));
        MatteBorder blueLineBorder = new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY);
        MatteBorder grayLineBorder = new MatteBorder(0, 0, 1, 0,  Color.LIGHT_GRAY);
        CompoundBorder compoundBorder = new CompoundBorder(blueLineBorder, new EmptyBorder(10, 10, 10, 10));
        CompoundBorder compoundBorder2 = new CompoundBorder(grayLineBorder, new EmptyBorder(10, 10, 10, 10));
        left.setBorder(compoundBorder2);
        centerBox.setBorder(compoundBorder2);
        this.add(title);
        this.add(left);
        this.add(centerBox);
        this.add(right);
        this.add(bottomBox);

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
}

/*
public class DrugsView extends JPanel {
    public final String viewName = "Drugs";
    private PatientListView patientListView;

    private FdaDatabaseAccessObject fdaObject;

    public DrugsView() {
        this.setLayout(new BorderLayout());
        this.setMaximumSize(ViewModel.VIEW_DIMENSION);
        this.setMinimumSize(ViewModel.VIEW_DIMENSION);
        this.fdaObject = new FdaDatabaseAccessObject();

        JPanel left = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JPanel left2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JPanel right = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;


        String[] dropdownValues = {"Warnings", "Description", "Interactions", "Pregnancy", "Nursing(Mothers)", "Usage", "Abuse", "Handling", "Reactions"};
        JLabel recallTitle = new JLabel("Recent Recalls");
        recallTitle.setFont(new Font("Arial", Font.BOLD, 16));
        right.add(recallTitle, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;


        List<String> recalls = new ArrayList<>();
        List<Map<String, Object>> recallsMap = fdaObject.recentlyRecalled(5);
        for (int i = 0; i < 5; i++) {
            recalls.add(recallsMap.get(i).get("product_description").toString());
        }


        addField(right, gbc, recalls.get(0));
        addField(right, gbc, recalls.get(1));
        addField(right, gbc, recalls.get(2));
        addField(right, gbc, recalls.get(3));
        addField(right, gbc, recalls.get(4));


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Drug Info");


        gbc.gridwidth = 1;
        gbc.gridy++;

        JComboBox<String> dropdown = new JComboBox<>(dropdownValues);
        JTextField inputField = new JTextField();
        JButton submitButton = new JButton();
        JTextArea outputLabel = new JTextArea("Drug Information");
        JScrollPane scrollPane = new JScrollPane(outputLabel);

        titleLabel.setFont(ViewModel.TITLE_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        titleLabel.setBackground(ViewModel.HEADER_COLOR);
        titleLabel.setForeground(ViewModel.TEXT_COLOR);

        inputField.setFont(ViewModel.HEADING_FONT_BOLD);
        inputField.setForeground(ViewModel.TEXT_COLOR);

        submitButton.setOpaque(false);
        submitButton.setBackground(new Color(69, 130, 222));
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setFont(ViewModel.HEADING_FONT_BOLD);
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setMinimumSize(new Dimension(30, 70));
        submitButton.setMaximumSize(new Dimension(30, 70));
        submitButton.setText("🔎 Find Info");
        submitButton.setOpaque(true);
        submitButton.setContentAreaFilled(true);
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);


        left2.add(Box.createHorizontalStrut(8));

        left.add(titleLabel);
        left.add(Box.createRigidArea(new Dimension(10, 0)));
        left2.add(inputField);
        left2.add(Box.createRigidArea(new Dimension(10, 0)));
        left2.add(dropdown);
        left2.add(Box.createRigidArea(new Dimension(10, 0)));
        left2.add(submitButton);
        left2.add(Box.createRigidArea(new Dimension(10, 0)));


        center.add(scrollPane);


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();
                String selectedOption = (String) dropdown.getSelectedItem();
                if (Objects.equals(selectedOption, "Warnings")) {
                    outputLabel.setText(fdaObject.getWarnings(inputText));
                } else if (Objects.equals(selectedOption, "Description")) {
                    outputLabel.setText(fdaObject.getDescription(inputText));
                } else if (Objects.equals(selectedOption, "Interactions")) {
                    outputLabel.setText(fdaObject.getInteractions(inputText));
                } else if (Objects.equals(selectedOption, "Pregnancy")) {
                    outputLabel.setText(fdaObject.getPregnancy(inputText));
                } else if (Objects.equals(selectedOption, "Nursing(Mothers)")) {
                    outputLabel.setText(fdaObject.getNursing(inputText));
                } else if (Objects.equals(selectedOption, "Usage")) {
                    outputLabel.setText(fdaObject.getUsage(inputText));
                } else if (Objects.equals(selectedOption, "Abuse")) {
                    outputLabel.setText(fdaObject.getAbuse(inputText));
                } else if (Objects.equals(selectedOption, "Handling")) {
                    outputLabel.setText(fdaObject.getHandling(inputText));
                } else if (Objects.equals(selectedOption, "Reactions")) {
                    outputLabel.setText(fdaObject.getReactions(inputText));
                }

            }
        });
        outputLabel.setLineWrap(true);
        outputLabel.setWrapStyleWord(true);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        left.setBackground(new Color(255, 255, 255));
        left2.setBackground(new Color(255, 255, 255));
        right.setBackground(new Color(224, 224, 229));


        JPanel mainPanel = new JPanel(new GridLayout(4, 1));

        ((GridLayout) mainPanel.getLayout()).setHgap(1);
        ((GridLayout) mainPanel.getLayout()).setVgap(1);
        mainPanel.setBackground(new Color(212, 213, 214));
        mainPanel.add(left);
        mainPanel.add(left2);
        mainPanel.add(center);
        mainPanel.add(right);
        this.add(mainPanel);
        // this.add(center,BorderLayout.CENTER);
        // this.add(right,BorderLayout.CENTER);

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
}

    public DrugsView() {
        this.setLayout(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.setMaximumSize(ViewModel.VIEW_DIMENSION);
        this.setMinimumSize(ViewModel.VIEW_DIMENSION);
        this.fdaObject = new FdaDatabaseAccessObject();
        this.setBackground(ViewModel.BACKGROUND_COLOR);

        // creating top panel + sorting dropdown menu
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(ViewModel.BACKGROUND_COLOR);
        // title label
        JLabel titleLabel = new JLabel("Drug Info");
        titleLabel.setFont(ViewModel.TITLE_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBackground(ViewModel.HEADER_COLOR);
        titleLabel.setForeground(ViewModel.TEXT_COLOR);
        topPanel.add(Box.createVerticalStrut(8));
        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(15));

        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // Add the "Sort by" label and combo box to the sub-panel
        sortPanel.setBackground(ViewModel.BACKGROUND_COLOR);
        JLabel sortByLabel = new JLabel("Drug Name");
        sortByLabel.setFont(ViewModel.HEADING_FONT_BOLD);
        sortByLabel.setForeground(ViewModel.TEXT_HIGHLIGHT_COLOR);
        sortByLabel.setBackground(ViewModel.BACKGROUND_COLOR);
        sortPanel.add(sortByLabel);

        JTextField inputField = new JTextField();
        inputField.setSize(new Dimension(100,10));

        String[] dropdownValues = {"Warnings","Description","Interactions","Pregnancy","Nursing(Mothers)","Usage","Abuse","Handling","Reactions"};
        JComboBox<String> dropDown = new JComboBox<String>(dropdownValues);
        dropDown.setForeground(ViewModel.TEXT_COLOR);

        // add filters / sorts
        dropDown.setFont(ViewModel.HEADING_FONT_BOLD);
        sortPanel.add(sortByLabel, BorderLayout.CENTER);
        sortPanel.add(inputField, BorderLayout.CENTER);
        sortPanel.add(dropDown, BorderLayout.CENTER);
        // add search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(ViewModel.BACKGROUND_COLOR);
        searchPanel.setForeground(ViewModel.TEXT_COLOR);
        // search button
        JButton submitButton = new JButton();
        submitButton.setOpaque(false);
        submitButton.setBackground(new Color(69, 130, 222));
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setFont(ViewModel.HEADING_FONT_BOLD);
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setMinimumSize(new Dimension(30, 70));
        submitButton.setMaximumSize(new Dimension(30, 70));
        submitButton.setText("🔎 Find Info");
        submitButton.setOpaque(true);
        submitButton.setContentAreaFilled(true);
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);
        searchPanel.add(Box.createHorizontalStrut(8));
        searchPanel.add(submitButton);

        JLabel outputLabel = new JLabel("");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();
                String selectedOption = (String) dropDown.getSelectedItem();
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

        topPanel.add(sortPanel, BorderLayout.CENTER);
        topPanel.add(Box.createVerticalStrut(20));
        // this.container.add(topPanel, BorderLayout.CENTER);
        this.add(topPanel, BorderLayout.NORTH);
        // add header for added patients
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(searchPanel, BorderLayout.CENTER);
        // scrollable view for patients

        JScrollPane scrollPane = new JScrollPane(outputLabel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(ViewModel.BACKGROUND_COLOR);
        // set the scrollbar color
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(72, 108, 150);
                this.trackColor = new Color(212, 213, 214);
                this.scrollBarWidth = 8;
            }
        });
        JPanel outputPanel = new JPanel();
        outputPanel.add(scrollPane);
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        outputPanel.setBackground(new Color(212, 213, 214));
        // Create the bottom panel with add button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(ViewModel.BACKGROUND_COLOR);

        JLabel recallTitle = new JLabel("Recent Recalls");
        recallTitle.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(recallTitle, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;





        List<String> recalls = new ArrayList<>();
        List<Map<String,Object>> recallsMap = fdaObject.recentlyRecalled(5);
        for(int i=0;i<5;i++){
            recalls.add(recallsMap.get(i).get("product_description").toString());
        }


        addField(bottomPanel,gbc,recalls.get(0));
        addField(bottomPanel,gbc,recalls.get(1));
        addField(bottomPanel,gbc,recalls.get(2));
        addField(bottomPanel,gbc,recalls.get(3));
        addField(bottomPanel,gbc,recalls.get(4));


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;

        this.add(scrollPane, BorderLayout.CENTER);
        //this.container.add(bottomPanel);
        this.add(bottomPanel, BorderLayout.SOUTH);

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

 */


