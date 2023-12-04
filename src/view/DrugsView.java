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
        gbc.anchor = GridBagConstraints.CENTER;


        String[] dropdownValues = {"Warnings", "Description", "Interactions", "Pregnancy", "Nursing(Mothers)", "Usage", "Abuse", "Handling", "Reactions"};
        JLabel recallTitle = new JLabel("Recent Recalls");
        recallTitle.setFont(new Font("Lato", Font.BOLD, 16));
        recallTitle.setForeground(new Color(211, 83, 83));
        right.add(recallTitle, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;


        List<String> recalls = new ArrayList<>();
        List<Map<String, Object>> recallsMap = fdaObject.recentlyRecalled(5);
        for (int i = 0; i < 5; i++) {
            recalls.add((i+1) + "." + recallsMap.get(i).get("product_description").toString());
        }

        // create a panel to hold the recall labels
        JPanel recallsPanel = new JPanel();
        recallsPanel.setLayout(new BoxLayout(recallsPanel, BoxLayout.Y_AXIS));
        recallsPanel.setBackground(new Color(212, 213, 214)); // Set the background color if needed

        // add recalls to the recallsPanel
        for (String recall : recalls) {
            JLabel recallLabel = new JLabel(recall);
            recallsPanel.add(recallLabel);
        }

        // create a scroll pane containing the recallsPanel
        JScrollPane recallsScrollPane = new JScrollPane(recallsPanel);
        recallsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        recallsScrollPane.setMaximumSize(new Dimension(500, 100));
        recallsScrollPane.setMinimumSize(new Dimension(500, 100));
        recallsScrollPane.setPreferredSize(new Dimension(500, 100));

        // add it to scroll pane
        right.add(recallsScrollPane, gbc);
        right.add(Box.createVerticalStrut(30), gbc);



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


