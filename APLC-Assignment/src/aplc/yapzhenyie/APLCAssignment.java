/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie;

import aplc.yapzhenyie.data.Country;
import aplc.yapzhenyie.data.DataElement;
import aplc.yapzhenyie.screens.PageDashboard;
import aplc.yapzhenyie.screens.PageStatistic;
import aplc.yapzhenyie.utils.DateTimeHelper;
import aplc.yapzhenyie.utils.DialogUtils;
import aplc.yapzhenyie.utils.FileUtils;
import aplc.yapzhenyie.utils.LoggerManager;
import aplc.yapzhenyie.utils.StatisticReportUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Yap Zhen Yie
 */
public class APLCAssignment {

    private static PageDashboard dashboardPage;
    private static PageStatistic statisticPage;

    private static List<Country> confirmedCasesDataset = new ArrayList<>();
    private static List<Country> deathCasesDataset = new ArrayList<>();
    private static List<Country> recoveredCasesDataset = new ArrayList<>();

    private static List<Country> confirmedCasesDatasetOnline = new ArrayList<>();
    private static List<Country> deathCasesDatasetOnline = new ArrayList<>();
    private static List<Country> recoveredCasesDatasetOnline = new ArrayList<>();

    private static boolean usingDefaultDataset = true;
    private static boolean onlineDatasetLoaded = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        initProgram();
        String target = "Malaysia";

//        for(Country res : confirmedCasesDataset.stream()
//                .filter(p -> p.getCountryName().contains("France"))
//                .collect(Collectors.toList())) {
//            int total = res.getDataset().stream().mapToInt(p -> p.getData()).sum();
//            System.out.println(res.getStateName() + " : " + res.getCountryName() + ": " + total);
//        }
        Country result = confirmedCasesDataset.stream().filter(p -> p.getCountryName().equals(target)).findAny().orElse(null);
        if (result != null) {
            List<DataElement> resu = result.getDataset();
            Date firstDate = resu.get(0).getDate();

            List<String> listOfWeeks = new ArrayList<>();
            List<Date> dates = resu.stream().map(map -> map.getDate()).collect(Collectors.toList());
            for (Date date : dates) {
                String formattedDate = DateTimeHelper.monthYearFormat.format(date);
                if (!listOfWeeks.contains(formattedDate)) {
                    listOfWeeks.add(formattedDate);
                    System.out.println(formattedDate);
                }
            }

            List<DataElement> t = resu.stream()
                    .filter(map -> DateTimeHelper.monthYearFormat.format(map.getDate()).equals(listOfWeeks.get(listOfWeeks.size() - 2)))
                    .collect(Collectors.toList());
            System.out.println(t.size());
            System.out.println(StatisticReportUtils.getWeeklyConfirmedCasesByCountry(confirmedCasesDataset, target, listOfWeeks.get(listOfWeeks.size() - 2)));
            System.out.println(StatisticReportUtils.getMonthlyConfirmedCasesByCountry(confirmedCasesDataset, target, listOfWeeks.get(listOfWeeks.size() - 1)));
            List<DataElement> lowestDeath = StatisticReportUtils.getLowestDataByCountry(deathCasesDataset, target);
            List<DataElement> highestDeath = StatisticReportUtils.getHighestDataByCountry(deathCasesDataset, target);
            lowestDeath.forEach(c -> {
                System.out.println("Lowest Death - " + c.getDate() + ": " + c.getData());
            });
            highestDeath.forEach(c -> {
                System.out.println("Highest Death - " + c.getDate() + ": " + c.getData());
            });
            List<DataElement> lowestRecovered = StatisticReportUtils.getLowestDataByCountry(recoveredCasesDataset, target);
            List<DataElement> highestRecovered = StatisticReportUtils.getHighestDataByCountry(recoveredCasesDataset, target);
            lowestRecovered.forEach(c -> {
                System.out.println("Lowest Recovered - " + c.getDate() + ": " + c.getData());
            });
            highestRecovered.forEach(c -> {
                System.out.println("Highest Recovered - " + c.getDate() + ": " + c.getData());
            });

            //int confirmCases = result.getDataset().stream().mapToInt(p -> p.getData()).reduce(0, Integer::sum);
            //Map<Date, Integer> res = confirmedCasesDataset.stream().flatMap(map -> map.getDataset().stream()).collect(Collectors.toMap(e -> e.getDate(),
            //e -> e.getData()));
//            for(Country c : confirmedCasesDataset) {
//            List<DataElement> resu = StatisticReportUtils.getTotalConfirmedCasesByCountry(confirmedCasesDataset, c.getCountryName());
//                System.out.println(resu.size() + ":" + resu.get(0));
//            }
            //int res = result.getDataset().stream().mapToInt(p -> p.getData()).max().getAsInt();
            //List<DataElement> resu = StatisticReportUtils.getTotalConfirmedCasesByCountry(confirmedCasesDataset, target);
            //Integer resu2 = StatisticReportUtils.getTotalConfirmedCasesByCountry(confirmedCasesDataset, target);
            //System.out.println(resu2);
        } else {
            System.out.println("result is null");
        }
//        List<String> country = confirmedCasesDataset.get("Country/Region");
//        for (int i = 0; i < country.size(); i++) {
//            Object obj = country.get(i);
//            // do stuff here
//        }
    }

    private static void initProgram() {
        dashboardPage = new PageDashboard();
        statisticPage = new PageStatistic();
        loadDefaultDatasets();
    }

    public static void stopProgram() {

    }

    private static void loadDefaultDatasets() {
        try {
            getDashboardPage().getDatasetComboBox().setEnabled(false);
            getDashboardPage().getActionButton().setEnabled(false);
            getDashboardPage().getActionButton().setText("Loading Data...");
            String confirmedCasesFile = "datasets\\time_series_covid19_confirmed_global.csv";
            String deathCasesFile = "datasets\\time_series_covid19_deaths_global.csv";
            String recoveredCasesFile = "datasets\\time_series_covid19_recovered_global.csv";

            addLogMessage("Loading default datasets...");
            confirmedCasesDataset = FileUtils.readCSVFile(confirmedCasesFile);
            deathCasesDataset = FileUtils.readCSVFile(deathCasesFile);
            recoveredCasesDataset = FileUtils.readCSVFile(recoveredCasesFile);
            addLogMessage("Default datasets is loaded.");
            getDashboardPage().getDatasetComboBox().setEnabled(true);
            getDashboardPage().getActionButton().setEnabled(true);
            getDashboardPage().getActionButton().setText("Go to Statistic Page");
        } catch (Exception e) {
            addErrorLogMessage("An error is occurred while loading default datasets.");
            getDashboardPage().getDatasetComboBox().setEnabled(true);
            getDashboardPage().getActionButton().setEnabled(true);
            getDashboardPage().getActionButton().setText("Error");
            DialogUtils.showErrorMessageDialog(null, "An error is occurred while\nloading the default datasets!");
            e.printStackTrace();
        }
    }

    public static void loadDatasetFromOnline() {
        try {
            getDashboardPage().getDatasetComboBox().setEnabled(false);
            getDashboardPage().getActionButton().setEnabled(false);
            getDashboardPage().getActionButton().setText("Loading Data...");
            addLogMessage("Loading datasets from online source...");
            addLogMessage("Establishing internet connection...");
            String confirmedCasesFileUrl = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_covid19_confirmed_global.csv&filename=time_series_covid19_confirmed_global.csv";
            String deathCasesFileUrl = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_covid19_deaths_global.csv&filename=time_series_covid19_deaths_global.csv";
            String recoveredCasesFileUrl = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_covid19_recovered_global.csv&filename=time_series_covid19_recovered_global.csv";

            Thread t = new Thread(new Runnable() {
                public void run() {
                    confirmedCasesDatasetOnline = FileUtils.readCSVFileFromUrl(confirmedCasesFileUrl);
                    deathCasesDatasetOnline = FileUtils.readCSVFileFromUrl(deathCasesFileUrl);
                    recoveredCasesDatasetOnline = FileUtils.readCSVFileFromUrl(recoveredCasesFileUrl);
                }
            });
            t.start();
            
            t.join();
            
            addLogMessage("Datasets from online source is loaded.");
            onlineDatasetLoaded = true;
            getDashboardPage().getDatasetComboBox().setEnabled(true);
            getDashboardPage().getActionButton().setEnabled(true);
            getDashboardPage().getActionButton().setText("Go to Statistic Page");
        } catch (Exception e) {
            addErrorLogMessage("An error is occurred while loading the datasets from online source.");
            getDashboardPage().getDatasetComboBox().setEnabled(true);
            getDashboardPage().getActionButton().setEnabled(true);
            getDashboardPage().getActionButton().setText("Error");
            DialogUtils.showErrorMessageDialog(null, "An error is occurred while\nloading the datasets from online source!");
            e.printStackTrace();
        }

    }

    public static PageDashboard getDashboardPage() {
        return dashboardPage;
    }

    public static PageStatistic getStatisticPage() {
        return statisticPage;
    }

    public static List<Country> getSelectedConfirmedCasesDataset() {
        if (!usingDefaultDataset && onlineDatasetLoaded) {
            return confirmedCasesDatasetOnline;
        }
        return confirmedCasesDataset;
    }

    public static List<Country> getSelectedDeathCasesDataset() {
        if (!usingDefaultDataset && onlineDatasetLoaded) {
            return deathCasesDatasetOnline;
        }
        return deathCasesDataset;
    }

    public static List<Country> getSelectedRecoveredCasesDataset() {
        if (!usingDefaultDataset && onlineDatasetLoaded) {
            return recoveredCasesDatasetOnline;
        }
        return recoveredCasesDataset;
    }

    public static boolean isUsingDefaultDataset() {
        return usingDefaultDataset;
    }

    public static void setUsingDefaultDataset(boolean flag) {
        usingDefaultDataset = flag;
    }

    public static boolean isOnlineDatasetLoaded() {
        return onlineDatasetLoaded;
    }

    public static void setIsOnlineDatasetLoaded(boolean flag) {
        onlineDatasetLoaded = flag;
    }

    public static void addLogMessage(String message) {
        if (dashboardPage == null || dashboardPage.getLoggingArea() == null) {
            return;
        }
        LoggerManager.addLogMessage(dashboardPage.getLoggingArea(), message);
    }

    public static void addErrorLogMessage(String message) {
        if (dashboardPage == null || dashboardPage.getLoggingArea() == null) {
            return;
        }
        LoggerManager.addErrorMessage(dashboardPage.getLoggingArea(), message);
    }
}
