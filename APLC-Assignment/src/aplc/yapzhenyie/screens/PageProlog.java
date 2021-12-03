/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie.screens;

import aplc.yapzhenyie.APLCAssignment;
import aplc.yapzhenyie.data.Country;
import aplc.yapzhenyie.prolog.PrologStatement;
import aplc.yapzhenyie.prolog.PrologKnowledgebaseGenerator;
import aplc.yapzhenyie.utils.DateTimeHelper;
import aplc.yapzhenyie.utils.StatisticReportUtils;
import aplc.yapzhenyie.utils.constants.ConstantMessage;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;
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
public class PageProlog extends JFrame implements ActionListener, IPage {

    private final JLabel labelSelectedDatasetText;
    private final JLabel labelReportDateText;
    private final JComboBox<String> comboBoxPrologStatement;

    private final JButton buttonGoBack;
    private final JButton buttonFind;
    private final JButton buttonClearOutput;

    private final JTextArea prologOutputArea;

    public PageProlog() {
        super.setSize(1024, 580);
        super.setMinimumSize(new Dimension(1024, 580));
        super.setResizable(false);

        /**
         * Following source code reference from (Jack, 2010) Source:
         * https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
         */
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        super.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        super.setTitle(ConstantMessage.ApplicationName);
        super.setIconImage(new ImageIcon(getClass().getResource("/resources/APU-Logo.png")).getImage());
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel header = new JLabel();
        header.setBounds(20, 0, 420, 40);
        header.setFont(new java.awt.Font("Times New Roman", 1, 21));
        header.setForeground(new java.awt.Color(0, 0, 0));
        header.setText(ConstantMessage.ApplicationHeader);
        super.getContentPane().add(header);

        JLabel subheader = new JLabel();
        subheader.setBounds(20, 22, 300, 40);
        subheader.setFont(new java.awt.Font("Times New Roman", 0, 14));
        subheader.setForeground(new java.awt.Color(0, 0, 0));
        subheader.setText(ConstantMessage.ApplicationSubHeader);
        super.getContentPane().add(subheader);

        JLabel labelSelectedDataset = new JLabel();
        labelSelectedDataset.setBounds(20, 60, 80, 22);
        labelSelectedDataset.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelSelectedDataset.setForeground(new java.awt.Color(0, 0, 0));
        labelSelectedDataset.setText("Dataset:");
        super.getContentPane().add(labelSelectedDataset);

        labelSelectedDatasetText = new JLabel();
        labelSelectedDatasetText.setBounds(80, 61, 250, 22);
        labelSelectedDatasetText.setFont(new java.awt.Font("Times New Roman", 0, 14));
        labelSelectedDatasetText.setForeground(new java.awt.Color(0, 0, 0));
        super.getContentPane().add(labelSelectedDatasetText);

        JLabel labelReportType = new JLabel();
        labelReportType.setBounds(20, 90, 120, 22);
        labelReportType.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelReportType.setForeground(new java.awt.Color(0, 0, 0));
        labelReportType.setText("Prolog Statement:");
        super.getContentPane().add(labelReportType);

        comboBoxPrologStatement = new JComboBox();
        comboBoxPrologStatement.setBounds(150, 89, 320, 25);
        comboBoxPrologStatement.setFont(new java.awt.Font("Times New Roman", 0, 14));
        comboBoxPrologStatement.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Covid-19 Confirmed Cases in Ascending Order",
            "Covid-19 Confirmed Cases in Descending Order"}));
        super.getContentPane().add(comboBoxPrologStatement);

        JLabel labelReportDate = new JLabel();
        labelReportDate.setBounds(400, 60, 80, 22);
        labelReportDate.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelReportDate.setForeground(new java.awt.Color(0, 0, 0));
        labelReportDate.setText("Data as of: ");
        super.getContentPane().add(labelReportDate);

        labelReportDateText = new JLabel();
        labelReportDateText.setBounds(475, 61, 250, 22);
        labelReportDateText.setFont(new java.awt.Font("Times New Roman", 0, 14));
        labelReportDateText.setForeground(new java.awt.Color(0, 0, 0));
        super.getContentPane().add(labelReportDateText);

        buttonGoBack = new JButton();
        buttonGoBack.setBounds(860, 15, 140, 32);
        buttonGoBack.setText("Back to Main");
        buttonGoBack.setFont(new Font("Times New Roman", 1, 16));
        buttonGoBack.addActionListener(this);
        super.getContentPane().add(buttonGoBack);

        buttonFind = new JButton();
        buttonFind.setBounds(490, 89, 100, 25);
        buttonFind.setText("Find");
        buttonFind.setFont(new Font("Times New Roman", 1, 14));
        buttonFind.addActionListener(this);
        buttonFind.requestFocus();
        super.getContentPane().add(buttonFind);

        JLabel labellogs = new JLabel();
        labellogs.setBounds(100, 145, 100, 30);
        labellogs.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labellogs.setForeground(new java.awt.Color(0, 0, 0));
        labellogs.setText("Prolog Output");
        super.getContentPane().add(labellogs);

        buttonClearOutput = new JButton();
        buttonClearOutput.setBounds(790, 140, 120, 30);
        buttonClearOutput.setText("Clear Output");
        buttonClearOutput.setFont(new Font("Times New Roman", 1, 14));
        buttonClearOutput.addActionListener(this);
        super.getContentPane().add(buttonClearOutput);

        prologOutputArea = new JTextArea();
        prologOutputArea.setEditable(false);
        prologOutputArea.setBorder(BorderFactory.createCompoundBorder(prologOutputArea.getBorder(), BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        prologOutputArea.setBounds(100, 180, 810, 345);
        prologOutputArea.setLineWrap(true);
        prologOutputArea.setWrapStyleWord(true);
        DefaultCaret caret = (DefaultCaret) prologOutputArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane loggingAreaScrollPane = new JScrollPane(prologOutputArea);
        loggingAreaScrollPane.setBounds(100, 180, 810, 345);
        super.getContentPane().add(loggingAreaScrollPane);

        super.getContentPane().setLayout(null);
        super.setVisible(false);
    }

    @Override
    public void setPageVisible(boolean flag) {
        super.setVisible(flag);
    }

    public JTextArea getPrologOutputArea() {
        return prologOutputArea;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == this.buttonGoBack) {
            setPageVisible(false);
            APLCAssignment.getDashboardPage().setPageVisible(true);
        } else if (event.getSource() == this.buttonFind) {
            switch (comboBoxPrologStatement.getSelectedIndex()) {
                case 0:
                    PrologStatement.ConfirmedCasesInAscendingOrder();
                    break;
                case 1:
                    PrologStatement.ConfirmedCasesInDescendingOrder();
                    break;
            }
        } else if (event.getSource() == this.buttonClearOutput) {
            getPrologOutputArea().setText("");
        }
    }

    public void updateComponents() {
        if (APLCAssignment.isUsingDefaultDataset()) {
            labelSelectedDatasetText.setText("Default datasets");
        } else {
            if (APLCAssignment.isOnlineDatasetLoaded()) {
                labelSelectedDatasetText.setText("Datasets from Online Source");
            } else {
                labelSelectedDatasetText.setText("Datasets not loaded (Error)");
            }
        }
        comboBoxPrologStatement.setSelectedIndex(0);
        List<Country> dataset = APLCAssignment.getSelectedConfirmedCasesDataset();
        List<Country> countryList = StatisticReportUtils.getDistinctCountries(dataset);
        Country cour = countryList.stream().findAny().orElse(null);
        labelReportDateText.setText(DateTimeHelper.convertDateAsString(cour.getDataset().get(cour.getDataset().size() - 1).getDate()));
        try {
            PrologKnowledgebaseGenerator.createKnowledgebaseFile(dataset);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        getPrologOutputArea().setText("");
    }
}
