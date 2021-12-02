/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie;

import aplc.yapzhenyie.data.Country;
import aplc.yapzhenyie.prolog.PrologKnowledgebaseGenerator;
import aplc.yapzhenyie.screens.PageDashboard;
import aplc.yapzhenyie.screens.PageProlog;
import aplc.yapzhenyie.screens.PageStatistic;
import aplc.yapzhenyie.utils.DialogUtils;
import aplc.yapzhenyie.utils.FileUtils;
import aplc.yapzhenyie.utils.LoggerManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yap Zhen Yie
 */
public class APLCAssignment {

    private static PageDashboard dashboardPage;
    private static PageStatistic statisticPage;
    private static PageProlog prologPage;

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
    }

    private static void initProgram() {
        dashboardPage = new PageDashboard();
        statisticPage = new PageStatistic();
        prologPage = new PageProlog();
        loadDefaultDatasets();
    }

    private static void loadDefaultDatasets() {
        try {
            getDashboardPage().getDatasetComboBox().setEnabled(false);
            getDashboardPage().getPrologActionButton().setEnabled(false);
            getDashboardPage().getActionButton().setEnabled(false);
            getDashboardPage().getActionButton().setText("Loading Data...");
            String confirmedCasesFile = "datasets/time_series_covid19_confirmed_global.csv";
            String deathCasesFile = "datasets/time_series_covid19_deaths_global.csv";
            String recoveredCasesFile = "datasets/time_series_covid19_recovered_global.csv";

            addLogMessage("Loading default datasets...");
            confirmedCasesDataset = FileUtils.readCSVFile(confirmedCasesFile);
            deathCasesDataset = FileUtils.readCSVFile(deathCasesFile);
            recoveredCasesDataset = FileUtils.readCSVFile(recoveredCasesFile);
            addLogMessage("Default datasets is loaded.");
            getDashboardPage().getDatasetComboBox().setEnabled(true);
            getDashboardPage().getPrologActionButton().setEnabled(true);
            getDashboardPage().getActionButton().setEnabled(true);
            getDashboardPage().getActionButton().setText("Go to Statistic Page");
        } catch (Exception e) {
            addErrorLogMessage("An error is occurred while loading default datasets.");
            getDashboardPage().getDatasetComboBox().setEnabled(true);
            getDashboardPage().getPrologActionButton().setEnabled(true);
            getDashboardPage().getActionButton().setEnabled(true);
            getDashboardPage().getActionButton().setText("Error");
            DialogUtils.showErrorMessageDialog(null, "An error is occurred while\nloading the default datasets!");
            e.printStackTrace();
        }
    }

    public static void loadDatasetFromOnline() {
        getDashboardPage().getDatasetComboBox().setEnabled(false);
        getDashboardPage().getPrologActionButton().setEnabled(false);
        getDashboardPage().getActionButton().setEnabled(false);
        getDashboardPage().getActionButton().setText("Loading Data...");
        APLCAssignment.addLogMessage("Loading datasets from online source...");

        String confirmedCasesFileUrl = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_covid19_confirmed_global.csv&filename=time_series_covid19_confirmed_global.csv";
        String deathCasesFileUrl = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_covid19_deaths_global.csv&filename=time_series_covid19_deaths_global.csv";
        String recoveredCasesFileUrl = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_covid19_recovered_global.csv&filename=time_series_covid19_recovered_global.csv";

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    APLCAssignment.addLogMessage("Establishing network connection...");
                    confirmedCasesDatasetOnline = FileUtils.readCSVFileFromUrl(confirmedCasesFileUrl);
                    deathCasesDatasetOnline = FileUtils.readCSVFileFromUrl(deathCasesFileUrl);
                    recoveredCasesDatasetOnline = FileUtils.readCSVFileFromUrl(recoveredCasesFileUrl);

                    addLogMessage("Datasets from online source is loaded.");
                    onlineDatasetLoaded = true;
                    getDashboardPage().getDatasetComboBox().setEnabled(true);
                    getDashboardPage().getPrologActionButton().setEnabled(true);
                    getDashboardPage().getActionButton().setEnabled(true);
                    getDashboardPage().getActionButton().setText("Go to Statistic Page");

                } catch (Exception e) {
                    addErrorLogMessage("An error is occurred while loading the datasets from online source.");
                    getDashboardPage().getDatasetComboBox().setEnabled(true);
                    getDashboardPage().getPrologActionButton().setEnabled(true);
                    getDashboardPage().getActionButton().setEnabled(true);
                    getDashboardPage().getActionButton().setText("Error");
                    DialogUtils.showErrorMessageDialog(null, "An error is occurred while\nloading the datasets from online source!");
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public static PageDashboard getDashboardPage() {
        return dashboardPage;
    }

    public static PageStatistic getStatisticPage() {
        return statisticPage;
    }

    public static PageProlog getPrologPage() {
        return prologPage;
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
