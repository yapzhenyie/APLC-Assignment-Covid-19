package aplc.yapzhenyie.screens;

import aplc.yapzhenyie.APLCAssignment;
import aplc.yapzhenyie.data.Country;
import aplc.yapzhenyie.utils.ChartUtils;
import aplc.yapzhenyie.utils.DateTimeHelper;
import aplc.yapzhenyie.utils.DialogUtils;
import aplc.yapzhenyie.utils.StatisticReportUtils;
import aplc.yapzhenyie.utils.constants.ConstantMessage;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Yap Zhen Yie
 */
public class PageStatistic extends JFrame implements ActionListener, IPage {

    private final JLabel labelSelectedDatasetText;
    private final JLabel labelReportDateText;
    private final JComboBox<String> comboBoxReportType;

    private final JButton buttonGoBack;
    private final JButton buttonCancel;

    // Search Box
    private final JTextField fieldSearchBox;
    private final JButton buttonSearch;
    private final JList<String> listSearchResult;
    private final JScrollPane scrollPaneSearchResult;

    // Chart
    private final JPanel containerChart;
    private final ChartPanel chartPanel;

    // Table 1
    private final JPanel containerTable1Header;
    private final JLabel labelTable1Header;
    private final JPanel containerTable1;
    private final JScrollPane scrollPaneTable1;
    private final JTable table1;

    // Table 2
    private final JPanel containerTable2Header;
    private final JLabel labelTable2Header;
    private final JPanel containerTable2;
    private final JScrollPane scrollPaneTable2;
    private final JTable table2;

    // Main Content Container
    private final JScrollPane scrollPaneContainer;

    private List<Country> searchResults = new ArrayList<>();

    public PageStatistic() {
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
        labelReportType.setBounds(20, 90, 80, 22);
        labelReportType.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelReportType.setForeground(new java.awt.Color(0, 0, 0));
        labelReportType.setText("Report Type:");
        super.getContentPane().add(labelReportType);

        comboBoxReportType = new JComboBox();
        comboBoxReportType.setBounds(110, 89, 300, 25);
        comboBoxReportType.setFont(new java.awt.Font("Times New Roman", 0, 14));
        comboBoxReportType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Total Confirmed Cases",
            "Weekly and Monthly Confirmed Cases", "Highest/Lowest Death and Recovered Cases", "Highest Daily Covid-19 Confirmed Cases"}));
        comboBoxReportType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxReportTypeItemStateChanged(evt);
            }
        });
        super.getContentPane().add(comboBoxReportType);

        buttonCancel = new JButton();
        buttonCancel.setBounds(470, 86, 90, 30);
        buttonCancel.setText("Cancel");
        buttonCancel.setFont(new Font("Times New Roman", 1, 14));
        buttonCancel.addActionListener(this);
        super.getContentPane().add(buttonCancel);

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
        container.setLayout(new BoxLayout(container, 1));

        containerChart = new JPanel();
        container.add(containerChart);

        chartPanel = new ChartPanel(ChartUtils.generateBarChart("Covid-19 Confirmed Cases by Country", "Country", "Confirmed Cases", null));
        chartPanel.setPreferredSize(new Dimension(960, 290));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chartPanel.setBackground(Color.white);
        chartPanel.setRangeZoomable(true);
        chartPanel.setDomainZoomable(true);
        containerChart.add(chartPanel);
        {
            containerTable1Header = new JPanel();
            containerTable1Header.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
            container.add(containerTable1Header);

            labelTable1Header = new JLabel();
            labelTable1Header.setFont(new java.awt.Font("Times New Roman", 1, 28));
            labelTable1Header.setForeground(new java.awt.Color(0, 0, 0));
            containerTable1Header.add(labelTable1Header);

            containerTable1 = new JPanel();
            containerTable1.setBorder(BorderFactory.createEmptyBorder(3, 0, 20, 0));
            container.add(containerTable1);

            table1 = new JTable();
            table1.setFont(new java.awt.Font("Times New Roman", 0, 14));
            table1.getTableHeader().setFont(new Font("Times New Roman", 1, 15));
            table1.setPreferredScrollableViewportSize(new Dimension(550, 250));
            table1.setFillsViewportHeight(true);
            table1.getTableHeader().setReorderingAllowed(false);
            table1.setRowHeight(25);
            table1.setOpaque(false);
            table1.setDefaultEditor(Object.class, null);
            table1.setModel(new javax.swing.table.DefaultTableModel(null, new String[]{"No."}));

            scrollPaneTable1 = new JScrollPane();
            scrollPaneTable1.setPreferredSize(new Dimension(550, 250));
            scrollPaneTable1.setViewportView(table1);
            containerTable1.add(scrollPaneTable1);
        }

        {
            containerTable2Header = new JPanel();
            containerTable2Header.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
            containerTable2Header.setVisible(false);
            container.add(containerTable2Header);

            labelTable2Header = new JLabel();
            labelTable2Header.setFont(new java.awt.Font("Times New Roman", 1, 28));
            labelTable2Header.setForeground(new java.awt.Color(0, 0, 0));
            containerTable2Header.add(labelTable2Header);

            containerTable2 = new JPanel();
            containerTable2.setBorder(BorderFactory.createEmptyBorder(3, 0, 20, 0));
            containerTable2.setVisible(false);
            container.add(containerTable2);

            table2 = new JTable();
            table2.setFont(new java.awt.Font("Times New Roman", 0, 14));
            table2.getTableHeader().setFont(new Font("Times New Roman", 1, 15));
            table2.setPreferredScrollableViewportSize(new Dimension(550, 250));
            table2.setFillsViewportHeight(true);
            table2.getTableHeader().setReorderingAllowed(false);
            table2.setRowHeight(25);
            table2.setOpaque(false);
            table2.setDefaultEditor(Object.class, null);
            table2.setModel(new javax.swing.table.DefaultTableModel(null, new String[]{"No."}));

            scrollPaneTable2 = new JScrollPane();
            scrollPaneTable2.setPreferredSize(new Dimension(550, 250));
            scrollPaneTable2.setViewportView(table2);
            containerTable2.add(scrollPaneTable2);
        }

        scrollPaneContainer = new JScrollPane();
        scrollPaneContainer.setBounds(10, 130, 999, 412);
        scrollPaneContainer.setViewportView(container);
        scrollPaneContainer.setAutoscrolls(true);
        scrollPaneContainer.getVerticalScrollBar().setUnitIncrement(16);
        scrollPaneContainer.setBorder(BorderFactory.createEmptyBorder());
        super.getContentPane().add(scrollPaneContainer);

        super.setGlassPane(new JComponent() {
            protected void paintComponent(Graphics g) {
                g.setColor(super.getBackground());
                g.fillRect(0, 130, super.getWidth(), 412);
            }
        });
        Container glassPane = (Container) super.getGlassPane();
        glassPane.setLayout(null);
        glassPane.addMouseListener(new MouseAdapter() {
        });

        JLabel labelLoading = new JLabel();
        labelLoading.setFont(new java.awt.Font("Times New Roman", 0, 18));
        labelLoading.setForeground(new java.awt.Color(0, 0, 0));
        labelLoading.setBounds(470, 295, 150, 25);
        labelLoading.setText("Loading...");
        glassPane.add(labelLoading);

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
        } else if (event.getSource() == this.buttonCancel) {
            updateComponents();
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
        comboBoxReportType.setSelectedIndex(0);
        comboBoxReportType.setEnabled(true);
        buttonCancel.setVisible(false);
        fieldSearchBox.setText("Country");
        fieldSearchBox.setForeground(Color.GRAY);
        listSearchResult.setModel(new DefaultListModel<>());
        scrollPaneSearchResult.setVisible(false);
        scrollPaneTable1.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));

        // Default selection
        updateSelectedReportType();
    }

    private void updateSelectedReportType() {
        switch (comboBoxReportType.getSelectedIndex()) {
            default:
                comboBoxReportType.setSelectedIndex(0);
            case 0: {
                List<Country> dataset = APLCAssignment.getSelectedConfirmedCasesDataset();
                List<Country> countryList = StatisticReportUtils.getDistinctCountries(dataset);
                Country cour = countryList.stream().findAny().orElse(null);
                labelReportDateText.setText(DateTimeHelper.convertDateAsString(cour.getDataset().get(cour.getDataset().size() - 1).getDate()));

                DefaultCategoryDataset chartDataSet = new DefaultCategoryDataset();
                String[][] data = new String[countryList.size()][3];

                for (int i = 0; i < countryList.size(); i++) {
                    Country country = countryList.get(i);
                    Integer total = StatisticReportUtils.getTotalConfirmedCasesByCountry(dataset, country.getCountryName());
                    // Table data
                    data[i][0] = String.valueOf(i + 1 + ".");
                    data[i][1] = country.getCountryName();
                    data[i][2] = total.toString();
                    // Chart data
                    chartDataSet.setValue(total, "Confirmed Cases", country.getCountryName());
                }
                JFreeChart chart = ChartUtils.generateBarChart("Covid-19 Total Confirmed Cases by Country", "Country", "Total Confirmed Cases", chartDataSet);
                chartPanel.setChart(chart);

                containerTable1Header.setVisible(true);
                labelTable1Header.setText("Covid-19 Total Confirmed Cases by Country");

                DefaultTableModel model = new DefaultTableModel(data,
                        new String[]{"No.", "Country", "Total Confirmed Cases"}) {
                    @Override
                    public Class getColumnClass(int column) {
                        switch (column) {
                            case 0:
                                return String.class;
                            case 1:
                                return String.class;
                            case 2:
                                return Integer.class;
                            default:
                                return String.class;
                        }
                    }

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                table1.setModel(model);
                TableRowSorter trs = new TableRowSorter(model);

                class IntComparator implements Comparator {

                    public int compare(Object o1, Object o2) {
                        Integer int1 = Integer.parseInt(o1.toString());
                        Integer int2 = Integer.parseInt(o2.toString());
                        return int1.compareTo(int2);
                    }

                    public boolean equals(Object o2) {
                        return this.equals(o2);
                    }
                }

                trs.setComparator(2, new IntComparator());
                table1.setRowSorter(trs);
                table1.getColumnModel().getColumn(0).setPreferredWidth(50);
                table1.getColumnModel().getColumn(1).setPreferredWidth(300);
                table1.getColumnModel().getColumn(2).setPreferredWidth(180);
                DefaultTableCellRenderer centerrenderer = new DefaultTableCellRenderer();
                centerrenderer.setHorizontalAlignment(JLabel.CENTER);
                table1.getColumnModel().getColumn(0).setCellRenderer(centerrenderer);
                table1.getColumnModel().getColumn(2).setCellRenderer(centerrenderer);
                table1.setAutoCreateRowSorter(false);
                table1.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

                scrollPaneTable1.setPreferredSize(new Dimension(550, 250));
                scrollPaneTable1.getVerticalScrollBar().setValue(0);
                scrollPaneTable1.getHorizontalScrollBar().setValue(0);
                containerChart.setVisible(true);
                containerTable2Header.setVisible(false);
                containerTable2.setVisible(false);
                break;
            }
            case 1: {
                containerChart.setVisible(false);

                List<Country> dataset = APLCAssignment.getSelectedConfirmedCasesDataset();
                List<Country> countryList = StatisticReportUtils.getDistinctCountries(dataset);
                Country cour = countryList.stream().findAny().orElse(null);
                labelReportDateText.setText(DateTimeHelper.convertDateAsString(cour.getDataset().get(cour.getDataset().size() - 1).getDate()));

                // Weekly Data table
                List<String> formattedWeeklyDate = StatisticReportUtils.getFormattedWeeklyDate(dataset);

                String[] table1Columns = new String[formattedWeeklyDate.size() + 2];
                table1Columns[0] = "No.";
                table1Columns[1] = "Country";
                for (int j = 0; j < formattedWeeklyDate.size(); j++) {
                    String weeklyDate = formattedWeeklyDate.get(j);
                    table1Columns[j + 2] = StatisticReportUtils.getFormattedWeeklyStartDate(dataset, weeklyDate)
                            + " - " + StatisticReportUtils.getFormattedWeeklyEndDate(dataset, weeklyDate);
                }

                String[][] table1Data = new String[countryList.size()][formattedWeeklyDate.size() + 2];

                for (int i = 0; i < countryList.size(); i++) {
                    Country country = countryList.get(i);
                    // Table data
                    table1Data[i][0] = String.valueOf(i + 1 + ".");
                    table1Data[i][1] = country.getCountryName();
                    for (int j = 0; j < formattedWeeklyDate.size(); j++) {
                        Integer weeklyConfirmedCases = StatisticReportUtils.getWeeklyConfirmedCasesByCountry(dataset,
                                country.getCountryName(), formattedWeeklyDate.get(j));
                        table1Data[i][j + 2] = weeklyConfirmedCases.toString();
                    }
                }

                containerTable1Header.setVisible(true);
                labelTable1Header.setText("Covid-19 Confirmed Cases by Country (Weekly)");

                DefaultTableModel model = new DefaultTableModel(table1Data, table1Columns) {
                    @Override
                    public Class getColumnClass(int column) {
                        switch (column) {
                            case 0:
                                return String.class;
                            case 1:
                                return String.class;
                            default:
                                return Integer.class;
                        }
                    }

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                table1.setModel(model);
                TableRowSorter trs = new TableRowSorter(model);

                class IntComparator implements Comparator {

                    public int compare(Object o1, Object o2) {
                        Integer int1 = Integer.parseInt(o1.toString());
                        Integer int2 = Integer.parseInt(o2.toString());
                        return int1.compareTo(int2);
                    }

                    public boolean equals(Object o2) {
                        return this.equals(o2);
                    }
                }
                table1.setRowSorter(trs);
                table1.getColumnModel().getColumn(0).setPreferredWidth(50);
                table1.getColumnModel().getColumn(1).setPreferredWidth(300);
                DefaultTableCellRenderer centerrenderer = new DefaultTableCellRenderer();
                centerrenderer.setHorizontalAlignment(JLabel.CENTER);
                table1.getColumnModel().getColumn(0).setCellRenderer(centerrenderer);
                for (int j = 0; j < formattedWeeklyDate.size(); j++) {
                    trs.setComparator(j + 2, new IntComparator());
                    table1.getColumnModel().getColumn(j + 2).setPreferredWidth(200);
                    table1.getColumnModel().getColumn(j + 2).setCellRenderer(centerrenderer);
                }
                table1.setAutoCreateRowSorter(false);
                table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                scrollPaneTable1.setPreferredSize(new Dimension(960, 325));
                scrollPaneTable1.getVerticalScrollBar().setValue(0);
                scrollPaneTable1.getHorizontalScrollBar().setValue(0);

                // Monthly Data table
                List<String> formattedMonthlyDate = StatisticReportUtils.getFormattedMonthlyDate(dataset);

                String[] table2Columns = new String[formattedMonthlyDate.size() + 2];
                table2Columns[0] = "No.";
                table2Columns[1] = "Country";
                for (int j = 0; j < formattedMonthlyDate.size(); j++) {
                    String monthlyDate = formattedMonthlyDate.get(j);
                    try {
                        table2Columns[j + 2] = DateTimeHelper.convertDateToMonthFormat(monthlyDate);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }

                String[][] table2Data = new String[countryList.size()][formattedMonthlyDate.size() + 2];

                for (int i = 0; i < countryList.size(); i++) {
                    Country country = countryList.get(i);
                    // Table data
                    table2Data[i][0] = String.valueOf(i + 1 + ".");
                    table2Data[i][1] = country.getCountryName();
                    for (int j = 0; j < formattedMonthlyDate.size(); j++) {
                        Integer monthlyConfirmedCases = StatisticReportUtils.getMonthlyConfirmedCasesByCountry(dataset,
                                country.getCountryName(), formattedMonthlyDate.get(j));
                        table2Data[i][j + 2] = monthlyConfirmedCases.toString();
                    }
                }

                containerTable2Header.setVisible(true);
                labelTable2Header.setText("Covid-19 Confirmed Cases by Country (Monthly)");

                DefaultTableModel model2 = new DefaultTableModel(table2Data, table2Columns) {
                    @Override
                    public Class getColumnClass(int column) {
                        switch (column) {
                            case 0:
                                return String.class;
                            case 1:
                                return String.class;
                            default:
                                return Integer.class;
                        }
                    }

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                table2.setModel(model2);

                TableRowSorter trs2 = new TableRowSorter(model2);
                table2.setRowSorter(trs2);
                table2.getColumnModel().getColumn(0).setPreferredWidth(50);
                table2.getColumnModel().getColumn(1).setPreferredWidth(300);
                DefaultTableCellRenderer centerrenderer2 = new DefaultTableCellRenderer();
                centerrenderer2.setHorizontalAlignment(JLabel.CENTER);
                table2.getColumnModel().getColumn(0).setCellRenderer(centerrenderer2);
                for (int j = 0; j < formattedMonthlyDate.size(); j++) {
                    trs2.setComparator(j + 2, new IntComparator());
                    table2.getColumnModel().getColumn(j + 2).setPreferredWidth(200);
                    table2.getColumnModel().getColumn(j + 2).setCellRenderer(centerrenderer2);
                }
                table2.setAutoCreateRowSorter(false);
                table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                containerTable2.setVisible(true);
                scrollPaneTable2.setPreferredSize(new Dimension(960, 325));
                scrollPaneTable2.getVerticalScrollBar().setValue(0);
                scrollPaneTable2.getHorizontalScrollBar().setValue(0);
                break;
            }
            case 2: {
                containerChart.setVisible(false);
                containerTable2Header.setVisible(false);
                containerTable2.setVisible(false);

                List<Country> datasetDeath = APLCAssignment.getSelectedDeathCasesDataset();
                List<Country> datasetRecovered = APLCAssignment.getSelectedRecoveredCasesDataset();
                List<Country> countryList = StatisticReportUtils.getDistinctCountries(datasetDeath.size() > datasetRecovered.size() ? datasetDeath : datasetRecovered);
                Country cour = countryList.stream().findAny().orElse(null);
                labelReportDateText.setText(DateTimeHelper.convertDateAsString(cour.getDataset().get(cour.getDataset().size() - 1).getDate()));

                String[] table1Columns = new String[6];
                table1Columns[0] = "No.";
                table1Columns[1] = "Country";
                table1Columns[2] = "Lowest Death";
                table1Columns[3] = "Highest Death";
                table1Columns[4] = "Lowest Recovered";
                table1Columns[5] = "Highest Recovered";

                String[][] table1Data = new String[countryList.size()][6];

                for (int i = 0; i < countryList.size(); i++) {
                    Country country = countryList.get(i);
                    table1Data[i][0] = String.valueOf(i + 1 + ".");
                    table1Data[i][1] = country.getCountryName();
                    table1Data[i][2] = String.valueOf(Math.max(StatisticReportUtils.getLowestDataByCountry(datasetDeath, country.getCountryName()), 0));
                    table1Data[i][3] = String.valueOf(Math.max(StatisticReportUtils.getHighestDataByCountry(datasetDeath, country.getCountryName()), 0));
                    table1Data[i][4] = String.valueOf(Math.max(StatisticReportUtils.getLowestDataByCountry(datasetRecovered, country.getCountryName()), 0));
                    table1Data[i][5] = String.valueOf(Math.max(StatisticReportUtils.getHighestDataByCountry(datasetRecovered, country.getCountryName()), 0));
                }

                containerTable1Header.setVisible(true);
                labelTable1Header.setText("Highest/Lowest Daily Death and Recovered Covid-19 Cases by Country");

                DefaultTableModel model = new DefaultTableModel(table1Data, table1Columns) {
                    @Override
                    public Class getColumnClass(int column) {
                        switch (column) {
                            case 0:
                                return String.class;
                            case 1:
                                return String.class;
                            default:
                                return Integer.class;
                        }
                    }

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                table1.setModel(model);
                TableRowSorter trs = new TableRowSorter(model);

                class IntComparator implements Comparator {

                    public int compare(Object o1, Object o2) {
                        Integer int1 = Integer.parseInt(o1.toString());
                        Integer int2 = Integer.parseInt(o2.toString());
                        return int1.compareTo(int2);
                    }

                    public boolean equals(Object o2) {
                        return this.equals(o2);
                    }
                }
                table1.setRowSorter(trs);
                table1.getColumnModel().getColumn(0).setPreferredWidth(50);
                table1.getColumnModel().getColumn(1).setPreferredWidth(300);
                DefaultTableCellRenderer centerrenderer = new DefaultTableCellRenderer();
                centerrenderer.setHorizontalAlignment(JLabel.CENTER);
                table1.getColumnModel().getColumn(0).setCellRenderer(centerrenderer);
                for (int j = 2; j <= 5; j++) {
                    trs.setComparator(j, new IntComparator());
                    table1.getColumnModel().getColumn(j).setPreferredWidth(200);
                    table1.getColumnModel().getColumn(j).setCellRenderer(centerrenderer);
                }
                table1.setAutoCreateRowSorter(false);
                table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                scrollPaneTable1.setPreferredSize(new Dimension(960, 325));
                scrollPaneTable1.getVerticalScrollBar().setValue(0);
                scrollPaneTable1.getHorizontalScrollBar().setValue(0);
                break;
            }
            case 3: {
                containerChart.setVisible(false);
                containerTable2Header.setVisible(false);
                containerTable2.setVisible(false);

                List<Country> datasetConfirmed = APLCAssignment.getSelectedConfirmedCasesDataset();
                List<Country> countryList = StatisticReportUtils.getDistinctCountries(datasetConfirmed);
                Country cour = countryList.stream().findAny().orElse(null);
                labelReportDateText.setText(DateTimeHelper.convertDateAsString(cour.getDataset().get(cour.getDataset().size() - 1).getDate()));

                String[] table1Columns = new String[3];
                table1Columns[0] = "No.";
                table1Columns[1] = "Country";
                table1Columns[2] = "Highest Confirmed Cases";

                String[][] table1Data = new String[countryList.size()][3];

                for (int i = 0; i < countryList.size(); i++) {
                    Country country = countryList.get(i);
                    table1Data[i][0] = String.valueOf(i + 1 + ".");
                    table1Data[i][1] = country.getCountryName();
                    table1Data[i][2] = String.valueOf(Math.max(StatisticReportUtils.getHighestDataByCountry(datasetConfirmed, country.getCountryName()), 0));
                }

                containerTable1Header.setVisible(true);
                labelTable1Header.setText("Highest Daily Covid-19 Confirmed Cases by Country");

                DefaultTableModel model = new DefaultTableModel(table1Data, table1Columns) {
                    @Override
                    public Class getColumnClass(int column) {
                        switch (column) {
                            case 0:
                                return String.class;
                            case 1:
                                return String.class;
                            default:
                                return Integer.class;
                        }
                    }

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                table1.setModel(model);
                TableRowSorter trs = new TableRowSorter(model);

                class IntComparator implements Comparator {

                    public int compare(Object o1, Object o2) {
                        Integer int1 = Integer.parseInt(o1.toString());
                        Integer int2 = Integer.parseInt(o2.toString());
                        return int1.compareTo(int2);
                    }

                    public boolean equals(Object o2) {
                        return this.equals(o2);
                    }
                }
                table1.setRowSorter(trs);
                table1.getColumnModel().getColumn(0).setPreferredWidth(50);
                table1.getColumnModel().getColumn(1).setPreferredWidth(300);
                DefaultTableCellRenderer centerrenderer = new DefaultTableCellRenderer();
                centerrenderer.setHorizontalAlignment(JLabel.CENTER);
                table1.getColumnModel().getColumn(0).setCellRenderer(centerrenderer);

                trs.setComparator(2, new IntComparator());
                table1.getColumnModel().getColumn(2).setPreferredWidth(220);
                table1.getColumnModel().getColumn(2).setCellRenderer(centerrenderer);

                table1.setAutoCreateRowSorter(false);
                table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                scrollPaneTable1.setPreferredSize(new Dimension(592, 325));
                scrollPaneTable1.getVerticalScrollBar().setValue(0);
                scrollPaneTable1.getHorizontalScrollBar().setValue(0);
                break;
            }
        }
        scrollPaneContainer.getVerticalScrollBar().setValue(0);
    }

    private void setPerCountryInformation(int index) {
        Country country = searchResults.get(index);
        if (country == null) {
            DialogUtils.showErrorMessageDialog(null, "An error occurred while doing this process.");
            return;
        }
        fieldSearchBox.setText("Country");
        fieldSearchBox.setForeground(Color.GRAY);
        setSelectedCountryInformation(country);
    }

    private void setSelectedCountryInformation(Country country) {
        if (country == null) {
            return;
        }
        comboBoxReportType.setEnabled(false);
        scrollPaneSearchResult.setVisible(false);
        buttonCancel.setVisible(true);

        containerChart.setVisible(false);
        containerTable1Header.setVisible(true);
        labelTable1Header.setText("Covid-19 Statistics (" + country.getCountryName() + ")");

        List<Country> datasetConfirmed = APLCAssignment.getSelectedConfirmedCasesDataset();
        List<Country> datasetDeath = APLCAssignment.getSelectedDeathCasesDataset();
        List<Country> datasetRecovered = APLCAssignment.getSelectedRecoveredCasesDataset();
        labelReportDateText.setText(DateTimeHelper.convertDateAsString(country.getDataset().get(country.getDataset().size() - 1).getDate()));

        String[] table1Columns = new String[2];
        table1Columns[0] = "Statistic";
        table1Columns[1] = "Cases";

        String[][] table1Data = new String[9][2];

        table1Data[0][0] = "Total Confirmed Cases";
        table1Data[1][0] = "Total Death Cases";
        table1Data[2][0] = "Total Recovered Cases";
        table1Data[3][0] = "Lowest Confirmed Cases (Per day)";
        table1Data[4][0] = "Highest Confirmed Cases (Per day)";
        table1Data[5][0] = "Lowest Death Cases (Per day)";
        table1Data[6][0] = "Highest Death Cases (Per day)";
        table1Data[7][0] = "Lowest Recovered Cases (Per day)";
        table1Data[8][0] = "Highest Recovered Cases (Per day)";

        table1Data[0][1] = String.valueOf(StatisticReportUtils.getTotalConfirmedCasesByCountry(datasetConfirmed, country.getCountryName()));
        table1Data[1][1] = String.valueOf(StatisticReportUtils.getTotalConfirmedCasesByCountry(datasetDeath, country.getCountryName()));
        table1Data[2][1] = String.valueOf(StatisticReportUtils.getTotalConfirmedCasesByCountry(datasetRecovered, country.getCountryName()));
        table1Data[3][1] = String.valueOf(Math.max(StatisticReportUtils.getLowestDataByCountry(datasetConfirmed, country.getCountryName()), 0));
        table1Data[4][1] = String.valueOf(Math.max(StatisticReportUtils.getHighestDataByCountry(datasetConfirmed, country.getCountryName()), 0));
        table1Data[5][1] = String.valueOf(Math.max(StatisticReportUtils.getLowestDataByCountry(datasetDeath, country.getCountryName()), 0));
        table1Data[6][1] = String.valueOf(Math.max(StatisticReportUtils.getHighestDataByCountry(datasetDeath, country.getCountryName()), 0));
        table1Data[7][1] = String.valueOf(Math.max(StatisticReportUtils.getLowestDataByCountry(datasetRecovered, country.getCountryName()), 0));
        table1Data[8][1] = String.valueOf(Math.max(StatisticReportUtils.getHighestDataByCountry(datasetRecovered, country.getCountryName()), 0));

        DefaultTableModel model = new DefaultTableModel(table1Data, table1Columns) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    default:
                        return Integer.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(model);
        TableRowSorter trs = new TableRowSorter(model);

        class IntComparator implements Comparator {

            public int compare(Object o1, Object o2) {
                Integer int1 = Integer.parseInt(o1.toString());
                Integer int2 = Integer.parseInt(o2.toString());
                return int1.compareTo(int2);
            }

            public boolean equals(Object o2) {
                return this.equals(o2);
            }
        }
        table1.setRowSorter(trs);
        table1.getColumnModel().getColumn(0).setPreferredWidth(250);
        table1.getColumnModel().getColumn(1).setPreferredWidth(200);
        DefaultTableCellRenderer centerrenderer = new DefaultTableCellRenderer();
        centerrenderer.setHorizontalAlignment(JLabel.CENTER);
        table1.getColumnModel().getColumn(1).setCellRenderer(centerrenderer);

        trs.setComparator(1, new IntComparator());

        table1.setAutoCreateRowSorter(false);

        scrollPaneTable1.setPreferredSize(new Dimension(460, 300));
        scrollPaneTable1.getVerticalScrollBar().setValue(0);
        scrollPaneTable1.getHorizontalScrollBar().setValue(0);
        scrollPaneTable1.setBorder(BorderFactory.createEmptyBorder());

        // Table 2
        containerTable2Header.setVisible(true);
        labelTable2Header.setText("Covid-19 Weekly and Monthly Confirmed Cases (" + country.getCountryName() + ")");

        // Weekly Data table
        List<String> formattedWeeklyDate = StatisticReportUtils.getFormattedWeeklyDate(datasetConfirmed);
        // Monthly Data table
        List<String> formattedMonthlyDate = StatisticReportUtils.getFormattedMonthlyDate(datasetConfirmed);

        String[] table2Columns = new String[2];
        table2Columns[0] = "Date";
        table2Columns[1] = "Covid-19 Confirmed Cases";

        String[][] table2Data = new String[formattedWeeklyDate.size() + formattedMonthlyDate.size()][2];
        for (int j = 0; j < formattedWeeklyDate.size(); j++) {
            String weeklyDate = formattedWeeklyDate.get(j);
            table2Data[j][0] = StatisticReportUtils.getFormattedWeeklyStartDate(datasetConfirmed, weeklyDate)
                    + " - " + StatisticReportUtils.getFormattedWeeklyEndDate(datasetConfirmed, weeklyDate);
            table2Data[j][1] = StatisticReportUtils.getWeeklyConfirmedCasesByCountry(datasetConfirmed, country.getCountryName(), weeklyDate).toString();
        }

        for (int j = formattedWeeklyDate.size(); j < (formattedWeeklyDate.size() + formattedMonthlyDate.size()); j++) {
            String monthlyDate = formattedMonthlyDate.get(j - formattedWeeklyDate.size());
            try {
                table2Data[j][0] = DateTimeHelper.convertDateToMonthFormat(monthlyDate);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            table2Data[j][1] = StatisticReportUtils.getMonthlyConfirmedCasesByCountry(datasetConfirmed, country.getCountryName(), monthlyDate).toString();
        }

        DefaultTableModel model2 = new DefaultTableModel(table2Data, table2Columns) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    default:
                        return Integer.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table2.setModel(model2);

        TableRowSorter trs2 = new TableRowSorter(model2);
        table2.setRowSorter(trs2);
        table2.getColumnModel().getColumn(0).setPreferredWidth(250);
        table2.getColumnModel().getColumn(1).setPreferredWidth(200);
        DefaultTableCellRenderer centerrenderer2 = new DefaultTableCellRenderer();
        centerrenderer2.setHorizontalAlignment(JLabel.CENTER);
        table2.getColumnModel().getColumn(0).setCellRenderer(centerrenderer2);
        table2.getColumnModel().getColumn(1).setCellRenderer(centerrenderer2);
        trs2.setComparator(1, new IntComparator());

        table2.setAutoCreateRowSorter(false);

        containerTable2.setVisible(true);
        scrollPaneTable2.setPreferredSize(new Dimension(470, 325));
        scrollPaneTable2.getVerticalScrollBar().setValue(0);
        scrollPaneTable2.getHorizontalScrollBar().setValue(0);
    }

    private void comboBoxReportTypeItemStateChanged(java.awt.event.ItemEvent evt) {
        // State == Selected
        if (evt.getStateChange() == 1) {
            getGlassPane().setVisible(true);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    updateSelectedReportType();
                    getGlassPane().setVisible(false);
                }
            });
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
            setPerCountryInformation(listSearchResult.getSelectedIndex());
        }
    }

    private class ButtonSearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == buttonSearch) {
                searchByCountryName(true);
                if (searchResults.size() == 1) {
                    setPerCountryInformation(0);
                }
            }
        }
    }
}
