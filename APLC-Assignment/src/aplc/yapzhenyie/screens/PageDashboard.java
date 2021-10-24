/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie.screens;

import aplc.yapzhenyie.APLCAssignment;
import aplc.yapzhenyie.utils.constants.ConstantMessage;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Yap Zhen Yie
 */
public class PageDashboard extends JFrame implements ActionListener, IPage {

    private JTextArea loggingArea;
    private JComboBox<String> comboBoxDataset;
    private JButton buttonAction;

    public PageDashboard() {
        super.setSize(1024, 580);
        super.setMinimumSize(new Dimension(1024, 580));

        /**
         * Following source code reference from (Jack, 2010) Source:
         * https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
         */
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        super.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        super.setTitle(ConstantMessage.ApplicationName);
        super.setIconImage(new ImageIcon("resources/APU-Logo.png").getImage());
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel header = new JLabel();
        header.setBounds(110, 30, 800, 60);
        header.setFont(new java.awt.Font("Times New Roman", 1, 42));
        header.setForeground(new java.awt.Color(0, 0, 0));
        header.setText(ConstantMessage.ApplicationHeader);
        super.getContentPane().add(header);

        JLabel subheader = new JLabel();
        subheader.setBounds(400, 70, 300, 60);
        subheader.setFont(new java.awt.Font("Times New Roman", 0, 14));
        subheader.setForeground(new java.awt.Color(0, 0, 0));
        subheader.setText(ConstantMessage.ApplicationSubHeader);
        super.getContentPane().add(subheader);

        JLabel labelDataset = new JLabel();
        labelDataset.setBounds(330, 152, 150, 25);
        labelDataset.setFont(new java.awt.Font("Times New Roman", 0, 18));
        labelDataset.setForeground(new java.awt.Color(0, 0, 0));
        labelDataset.setText("Dataset:");
        super.getContentPane().add(labelDataset);

        comboBoxDataset = new JComboBox();
        comboBoxDataset.setBounds(420, 150, 250, 30);
        comboBoxDataset.setFont(new java.awt.Font("Times New Roman", 0, 18));
        comboBoxDataset.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Default Datasets", "Datasets from Online"}));
        comboBoxDataset.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxDatasetItemStateChanged(evt);
            }
        });
        super.getContentPane().add(comboBoxDataset);

        JLabel labelDatasetNotes = new JLabel();
        labelDatasetNotes.setBounds(200, 165, 550, 100);
        labelDatasetNotes.setFont(new java.awt.Font("Times New Roman", 0, 14));
        labelDatasetNotes.setForeground(new java.awt.Color(0, 0, 0));
        labelDatasetNotes.setText("<html>1. Default Datasets: The assignment preset datasets."
                + "<br>2. Datasets from Online: The data set is retrieved from online resources and has the latest data."
                + "<br>Note: Datasets from Online might not work properly due to API endpoint changed.");
        super.getContentPane().add(labelDatasetNotes);

        buttonAction = new JButton();
        buttonAction.setBounds(430, 266, 190, 40);
        buttonAction.setText("Go to Statistic Page");
        buttonAction.setFont(new Font("Times New Roman", 1, 18));
        buttonAction.addActionListener(this);
        super.getContentPane().add(buttonAction);

        JLabel labellogs = new JLabel();
        labellogs.setBounds(100, 305, 100, 30);
        labellogs.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labellogs.setForeground(new java.awt.Color(0, 0, 0));
        labellogs.setText("Logs");
        super.getContentPane().add(labellogs);

        loggingArea = new JTextArea();
        loggingArea.setEditable(false);
        loggingArea.setBorder(BorderFactory.createCompoundBorder(loggingArea.getBorder(), BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        loggingArea.setBounds(100, 330, 810, 195);
        loggingArea.setLineWrap(true);
        loggingArea.setWrapStyleWord(true);
        DefaultCaret caret = (DefaultCaret) loggingArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane loggingAreaScrollPane = new JScrollPane(loggingArea);
        loggingAreaScrollPane.setBounds(100, 330, 810, 195);
        super.getContentPane().add(loggingAreaScrollPane);

        super.getContentPane().setLayout(null);
        super.setVisible(true);
    }

    @Override
    public void setPageVisible(boolean flag) {
        super.setVisible(flag);
    }

    public JTextArea getLoggingArea() {
        return loggingArea;
    }

    public JComboBox<String> getDatasetComboBox() {
        return comboBoxDataset;
    }

    public JButton getActionButton() {
        return buttonAction;
    }

    private void comboBoxDatasetItemStateChanged(java.awt.event.ItemEvent evt) {
        // State == Selected
        if (evt.getStateChange() == 1) {
            if (evt.getItem().equals("Default Datasets")) {
                APLCAssignment.setUsingDefaultDataset(true);
                this.buttonAction.setText("Go to Statistic Page");
            } else if (evt.getItem().equals("Datasets from Online")) {
                APLCAssignment.setUsingDefaultDataset(false);
                if (!APLCAssignment.isOnlineDatasetLoaded()) {
                    this.buttonAction.setText("Load Datasets");
                } else {
                    this.buttonAction.setText("Go to Statistic Page");
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == this.buttonAction) {
            JButton btn = (JButton) event.getSource();
            if (APLCAssignment.isUsingDefaultDataset()) {
                setPageVisible(false);
                APLCAssignment.getStatisticPage().setPageVisible(true);
                APLCAssignment.getStatisticPage().updateComponents();
            } else {
                if (!APLCAssignment.isOnlineDatasetLoaded()) {
                    APLCAssignment.loadDatasetFromOnline();
                } else {
                    setPageVisible(false);
                    APLCAssignment.getStatisticPage().setPageVisible(true);
                    APLCAssignment.getStatisticPage().updateComponents();
                }
            }
        }
    }
}
