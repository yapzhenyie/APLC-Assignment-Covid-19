/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie.screens;

import aplc.yapzhenyie.APLCAssignment;
import aplc.yapzhenyie.data.Country;
import aplc.yapzhenyie.utils.DialogUtils;
import aplc.yapzhenyie.utils.StatisticReportUtils;
import aplc.yapzhenyie.utils.constants.ConstantMessage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Yap Zhen Yie
 */
public class PageStatistic extends JFrame implements ActionListener, IPage {

    private JLabel labelSelectedDatasetText;
    private JComboBox<String> comboBoxReportType;

    private JButton buttonGoBack;

    private JTextField fieldSearchBox;
    private JButton buttonSearch;
    private JList<String> listSearchResult;
    private JScrollPane scrollPaneSearchResult;

    private JTable tableUserList;
    
    private List<Country> searchResults = new ArrayList<>();
    private Country selectedCountry = null;

    public PageStatistic() {
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
        labelReportType.setBounds(20, 90, 80, 22);
        labelReportType.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelReportType.setForeground(new java.awt.Color(0, 0, 0));
        labelReportType.setText("Report Type:");
        super.getContentPane().add(labelReportType);

        comboBoxReportType = new JComboBox();
        comboBoxReportType.setBounds(110, 89, 300, 25);
        comboBoxReportType.setFont(new java.awt.Font("Times New Roman", 0, 14));
        comboBoxReportType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Total Confirmed Cases",
            "Weekly and Monthly Confirmed Cases", "Highest/Lowest Death and Recovered Cases"}));
        comboBoxReportType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxReportTypeItemStateChanged(evt);
            }
        });
        super.getContentPane().add(comboBoxReportType);

        buttonGoBack = new JButton();
        buttonGoBack.setBounds(860, 15, 140, 32);
        buttonGoBack.setText("Back to Main");
        buttonGoBack.setFont(new Font("Times New Roman", 1, 16));
        buttonGoBack.addActionListener(this);
        super.getContentPane().add(buttonGoBack);

        fieldSearchBox = new JTextField();
        fieldSearchBox.setBounds(750, 60, 130, 31);
        fieldSearchBox.setText("Country");
        fieldSearchBox.setForeground(Color.GRAY);
        fieldSearchBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fieldSearchBoxFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldSearchBoxFocusLost(evt);
            }
        });
        fieldSearchBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                searchByCountryName(false);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchByCountryName(false);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                searchByCountryName(false);
            }
        });
        fieldSearchBox.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchByCountryName(true);
                }
            }

            @Override
            public void keyTyped(KeyEvent paramKeyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent paramKeyEvent) {
            }
        });
        super.getContentPane().add(fieldSearchBox);

        buttonSearch = new JButton();
        buttonSearch.setBounds(880, 60, 90, 30);
        buttonSearch.setFont(new java.awt.Font("Times New Roman", 0, 14));
        buttonSearch.setText("Search");
        buttonSearch.addActionListener(new ButtonSearchListener());
        super.getContentPane().add(buttonSearch);

        listSearchResult = new JList<>();
        listSearchResult.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listSearchResultValueChanged(evt);
            }
        });

        scrollPaneSearchResult = new JScrollPane();
        scrollPaneSearchResult.setBounds(750, 90, 130, 110);
        scrollPaneSearchResult.setViewportView(listSearchResult);
        scrollPaneSearchResult.setVisible(false);
        super.getContentPane().add(scrollPaneSearchResult);

        JPanel container = new JPanel();
        container.setLayout(null);

        tableUserList = new JTable();
        tableUserList.setFont(new java.awt.Font("Times New Roman", 0, 14));
        tableUserList.setPreferredScrollableViewportSize(new Dimension(530, 250));
        tableUserList.setFillsViewportHeight(true);
        tableUserList.setRowHeight(25);
        tableUserList.setOpaque(false);
        tableUserList.setDefaultEditor(Object.class, null);
        tableUserList.setModel(new javax.swing.table.DefaultTableModel(
                null,
                new String[]{"No", "Country", " Total Confirmed Cases"}
        ));
        tableUserList.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableUserList.getColumnModel().getColumn(1).setPreferredWidth(300);
        tableUserList.getColumnModel().getColumn(2).setPreferredWidth(180);

        JScrollPane scrollPaneUserList = new JScrollPane();
        scrollPaneUserList.setBounds(180, 80, 530, 250);
        scrollPaneUserList.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        scrollPaneUserList.setViewportView(tableUserList);
        container.add(scrollPaneUserList);

        JScrollPane scrollPaneContainer = new JScrollPane();
        scrollPaneContainer.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneContainer.setBounds(0, 130, 1024, 470);
        scrollPaneContainer.setViewportView(container);
        super.getContentPane().add(scrollPaneContainer);

        super.getContentPane().setLayout(null);
        super.setVisible(false);
    }

    @Override
    public void setPageVisible(boolean flag) {
        super.setVisible(flag);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == this.buttonGoBack) {
            setPageVisible(false);
            APLCAssignment.getDashboardPage().setPageVisible(true);
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
        fieldSearchBox.setText("Country");
        fieldSearchBox.setForeground(Color.GRAY);
        listSearchResult.setModel(new DefaultListModel<>());
        scrollPaneSearchResult.setVisible(false);
        
        
        List<Country> countries = StatisticReportUtils.getDistinctCountries(APLCAssignment.getSelectedConfirmedCasesDataset());
        String[][] data = new String[countries.size()][3];
        for (int i = 0; i < countries.size(); i++) {
            Country country = countries.get(i);
            Integer total = StatisticReportUtils.getTotalConfirmedCasesByCountry(APLCAssignment.getSelectedConfirmedCasesDataset(), country.getCountryName());
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = country.getCountryName();
            data[i][2] = total.toString();
        }
        tableUserList.setModel(new javax.swing.table.DefaultTableModel(
                data,
                new String[]{"No", "Country", " Total Confirmed Cases"}
        ));
        tableUserList.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableUserList.getColumnModel().getColumn(1).setPreferredWidth(300);
        tableUserList.getColumnModel().getColumn(2).setPreferredWidth(180);
    }

    private void comboBoxReportTypeItemStateChanged(java.awt.event.ItemEvent evt) {
        // State == Selected
        if (evt.getStateChange() == 1) {
            if (evt.getItem().equals("Total Confirmed Cases")) {

            } else if (evt.getItem().equals("Weekly and Monthly Confirmed Cases")) {

            } else if (evt.getItem().equals("Highest/Lowest Death and Recovered Cases")) {

            }
        }
    }

    private void searchByCountryName(boolean showErrorMsg) {
        String countryName = fieldSearchBox.getText();
        if (countryName.equals("") || countryName.replace(" ", "").equals("")
                || countryName.equals("Country")) {
            if (showErrorMsg) {
                DialogUtils.showErrorMessageDialog(null, "You must enter a country name\nbefore doing the searching!");
            }
            scrollPaneSearchResult.setVisible(false);
            return;
        }

        List<Country> countryList = StatisticReportUtils.getCountriesContainsName(APLCAssignment.getSelectedConfirmedCasesDataset(), countryName);
        if (countryList == null || countryList.isEmpty()) {
            if (showErrorMsg) {
                DialogUtils.showErrorMessageDialog(null, "Country not found!");
            } else {
                scrollPaneSearchResult.setVisible(true);
                scrollPaneSearchResult.setSize(130, 110);
                listSearchResult.setModel(new javax.swing.AbstractListModel<String>() {
                    String[] strings = {"No Result Found!"};

                    public int getSize() {
                        return strings.length;
                    }

                    public String getElementAt(int i) {
                        return strings[i];
                    }
                });
            }
            return;
        }

        searchResults = countryList;
        scrollPaneSearchResult.setVisible(true);
        listSearchResult.setModel(new javax.swing.AbstractListModel<String>() {
            public int getSize() {
                return countryList.size();
            }

            public String getElementAt(int i) {
                return countryList.get(i).getCountryName();
            }
        });
        if (countryList.size() < 6) {
            scrollPaneSearchResult.setSize(130, countryList.size() * (countryList.size() <= 2 ? (countryList.size() == 2 ? 20 : 22) : 19));
        } else {
            scrollPaneSearchResult.setSize(130, 110);
        }
    }

    private void fieldSearchBoxFocusGained(java.awt.event.FocusEvent evt) {
        if (fieldSearchBox.getText().equals("Country")) {
            fieldSearchBox.setText("");
            fieldSearchBox.setForeground(Color.BLACK);
        }
    }

    private void fieldSearchBoxFocusLost(java.awt.event.FocusEvent evt) {
        if (fieldSearchBox.getText().equals("")) {
            fieldSearchBox.setText("Country");
            fieldSearchBox.setForeground(Color.GRAY);
        }
    }

    private void listSearchResultValueChanged(javax.swing.event.ListSelectionEvent evt) {
        if (evt.getValueIsAdjusting()) {
            if (listSearchResult.getSelectedValue().equals("No Result Found!")) {
                return;
            }
            if (searchResults.size() == 0) {
                DialogUtils.showErrorMessageDialog(null, "An error occurred while doing this process.");
            }
            //updateUserInformation(listSearchResult.getSelectedIndex());
        }
    }

    private class ButtonSearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == buttonSearch) {
                searchByCountryName(true);
                if (searchResults.size() == 1) {
//                    updateUserInformation(0);
                }
            }
        }
    }
}
