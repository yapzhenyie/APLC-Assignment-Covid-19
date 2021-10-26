/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie.utils;

import aplc.yapzhenyie.APLCAssignment;
import aplc.yapzhenyie.data.Country;
import aplc.yapzhenyie.data.DataElement;
import java.text.ParseException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Yap Zhen Yie
 */
public class StatisticReportUtils {

    public static List<Country> getDistinctCountries(List<Country> dataset) {
        return dataset.stream()
                .filter(FunctionUtils.distinctByKey(p -> p.getCountryName()))
                .collect(Collectors.toList());
    }

    public static List<Country> getCountriesContainsName(List<Country> dataset, String country) {
        return dataset.stream()
                .filter(FunctionUtils.distinctByKey(p -> p.getCountryName()))
                .filter(p -> p.getCountryName().toLowerCase().contains(country.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static List<String> getFormattedWeeklyDate(List<Country> dataset) {
        Country result = APLCAssignment.getSelectedConfirmedCasesDataset().stream().findAny().orElse(null);
        if (result == null) {
            return null;
        }
        return result.getDataset().stream()
                .map(p -> DateTimeHelper.weekYearFormat.format(p.getDate()))
                .filter(FunctionUtils.distinctByKey(p -> p))
                .collect(Collectors.toList());
    }

    public static List<String> getFormattedMonthlyDate(List<Country> dataset) {
        Country result = APLCAssignment.getSelectedConfirmedCasesDataset().stream().findAny().orElse(null);
        if (result == null) {
            return null;
        }
        return result.getDataset().stream()
                .map(p -> DateTimeHelper.monthYearFormat.format(p.getDate()))
                .filter(FunctionUtils.distinctByKey(p -> p))
                .collect(Collectors.toList());
    }

    public static String getFormattedWeeklyStartDate(List<Country> dataset, String weekYear) {
        Country result = APLCAssignment.getSelectedConfirmedCasesDataset().stream().findAny().orElse(null);
        if (result == null) {
            return null;
        }
        return result.getDataset().stream()
                .filter(p -> DateTimeHelper.weekYearFormat.format(p.getDate()).equals(weekYear))
                .sorted(Comparator.comparing(p -> p.getDate()))
                .map(p -> DateTimeHelper.convertDateToShortFormat(p.getDate())).findFirst().orElse(null);
    }

    public static String getFormattedWeeklyEndDate(List<Country> dataset, String weekYear) {
        Country result = APLCAssignment.getSelectedConfirmedCasesDataset().stream().findAny().orElse(null);
        if (result == null) {
            return null;
        }
        return result.getDataset().stream()
                .filter(p -> DateTimeHelper.weekYearFormat.format(p.getDate()).equals(weekYear))
                .sorted(Comparator.comparing(p -> p.getDate()))
                .map(p -> DateTimeHelper.convertDateToShortFormat(p.getDate())).reduce((a, b) -> b).orElse(null);
    }

    public static Integer getTotalConfirmedCasesByCountry(List<Country> dataset, String country) {
        List<Country> results = dataset.stream().filter(p -> p.getCountryName().equals(country)).collect(Collectors.toList());
        if (results == null) {
            return null;
        }
        return results.stream().map(p -> p.getDataset())
                .mapToInt(dataElements -> dataElements.stream().mapToInt(p -> p.getData()).sum())
                .sum();
    }

    public static Integer getWeeklyConfirmedCasesByCountry(List<Country> dataset, String country, String weekYear) {
        List<Country> results = dataset.stream().filter(p -> p.getCountryName().equals(country)).collect(Collectors.toList());
        if (results == null) {
            return null;
        }
        return results.stream().map(p -> p.getDataset())
                .mapToInt(dataElements -> dataElements.stream().filter(p -> DateTimeHelper.weekYearFormat.format(p.getDate()).equals(weekYear)).mapToInt(p -> p.getData()).sum())
                .sum();
    }

    public static Integer getMonthlyConfirmedCasesByCountry(List<Country> dataset, String country, String monthYear) {
        List<Country> results = dataset.stream().filter(p -> p.getCountryName().equals(country)).collect(Collectors.toList());
        if (results == null) {
            return null;
        }
        return results.stream().map(p -> p.getDataset())
                .mapToInt(dataElements -> dataElements.stream().filter(p -> DateTimeHelper.monthYearFormat.format(p.getDate()).equals(monthYear)).mapToInt(p -> p.getData()).sum())
                .sum();
    }

    public static Integer getLowestDataByCountry(List<Country> dataset, String country) {
        List<Country> results = dataset.stream().filter(p -> p.getCountryName().equals(country)).collect(Collectors.toList());
        if (results == null) {
            return null;
        }
        return results.stream()
                .map(p -> p.getDataset())
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(DataElement::getDate)).entrySet().stream()
                .mapToInt(map -> map.getValue().stream().mapToInt(ele -> ele.getData()).sum()).min().orElse(0);
    }

    public static List<DataElement> getLowestDataByCountry2(List<Country> dataset, String country) {
        Country result = dataset.stream().filter(p -> p.getCountryName().equals(country)).findAny().orElse(null);
        if (result == null) {
            return null;
        }
        return result.getDataset().stream()
                .collect(Collectors.groupingBy(DataElement::getData)).entrySet().stream()
                .min(Comparator.comparing(Map.Entry::getKey)).get().getValue();
    }

    public static Integer getHighestDataByCountry(List<Country> dataset, String country) {
        List<Country> results = dataset.stream().filter(p -> p.getCountryName().equals(country)).collect(Collectors.toList());
        if (results == null) {
            return null;
        }
        return results.stream()
                .map(p -> p.getDataset())
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(DataElement::getDate)).entrySet().stream()
                .mapToInt(map -> map.getValue().stream().mapToInt(ele -> ele.getData()).sum()).max().orElse(0);
    }

    public static List<DataElement> getHighestDataByCountry2(List<Country> dataset, String country) {
        Country result = dataset.stream().filter(p -> p.getCountryName().equals(country)).findAny().orElse(null);
        if (result == null) {
            return null;
        }
        return result.getDataset().stream()
                .collect(Collectors.groupingBy(DataElement::getData)).entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getKey)).get().getValue();
    }

    /**
     *
     * The result might more than 1 as the data(cases) remained the same.
     *
     * @param dataset
     * @param country
     * @return
     */
//    public static List<DataElement> getTotalConfirmedCasesByCountry(List<Country> dataset, String country) {
//        Country result = dataset.stream().filter(p -> p.getCountryName().equals(country)).findAny().orElse(null);
//        if (result == null) {
//            return null;
//        }
//        return result.getDataset().stream()
//                .collect(Collectors.groupingBy(DataElement::getData)).entrySet().stream()
//                .max(Comparator.comparing(Map.Entry::getKey)).get().getValue();
//    }
}
