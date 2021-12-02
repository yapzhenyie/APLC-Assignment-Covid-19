/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie.utils;

import aplc.yapzhenyie.APLCAssignment;
import aplc.yapzhenyie.data.Country;
import aplc.yapzhenyie.data.DataElement;
import aplc.yapzhenyie.utils.function.Computable;
import aplc.yapzhenyie.utils.function.ComputableImpl;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Yap Zhen Yie
 */
public class StatisticReportUtils {

    private static BiPredicate<String, String> containsCountryName = (inputParam, reference) -> inputParam.toLowerCase().contains(reference.toLowerCase());
    private static BiPredicate<String, String> equalsCountryName = (inputParam, reference) -> inputParam.toLowerCase().equals(reference.toLowerCase());

    public static List<Country> getDistinctCountries(List<Country> dataset) {
        return dataset.stream()
                .filter(FunctionUtils.distinctByKey(p -> p.getCountryName()))
                .collect(Collectors.toList());
    }

    public static List<Country> getCountriesContainsName(List<Country> dataset, String country) {
        return dataset.stream()
                .filter(FunctionUtils.distinctByKey(p -> p.getCountryName()))
                .filter(p -> containsCountryName.test(p.getCountryName(), country))
                .collect(Collectors.toList());
    }

    public static List<Country> getCountriesEqualsName(List<Country> dataset, String country) {
        return dataset.stream()
                .filter(p -> equalsCountryName.test(p.getCountryName(), country))
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
        List<Country> results = getCountriesEqualsName(dataset, country);
        if (results == null) {
            return null;
        }
        return results.stream().map(p -> p.getDataset())
                .mapToInt(dataElements -> dataElements.stream().mapToInt(p -> p.getData()).reduce(0, (x, y) -> x + y))
                .sum();
    }

    public static Integer getWeeklyConfirmedCasesByCountry(List<Country> dataset, String country, String weekYear) {
        List<Country> results = getCountriesEqualsName(dataset, country);
        if (results == null) {
            return null;
        }
        return results.stream().map(p -> p.getDataset())
                .mapToInt(dataElements -> dataElements.stream().filter(p -> 
                        DateTimeHelper.weekYearFormat.format(p.getDate()).equals(weekYear)).mapToInt(p -> p.getData()).sum())
                .sum();
    }

    public static Integer getMonthlyConfirmedCasesByCountry(List<Country> dataset, String country, String monthYear) {
        List<Country> results = getCountriesEqualsName(dataset, country);
        if (results == null) {
            return null;
        }
        int[] resArray = results.stream().map(p -> p.getDataset())
                .mapToInt(dataElements -> dataElements.stream().filter(p -> 
                        DateTimeHelper.monthYearFormat.format(p.getDate()).equals(monthYear)).mapToInt(p -> p.getData()).sum()).toArray();
        return findSum(resArray, resArray.length);
    }

    public static Integer getLowestDataByCountry(List<Country> dataset, String country) {
        List<Country> results = getCountriesEqualsName(dataset, country);
        if (results == null) {
            return null;
        }
        int[] resArray = results.stream()
                .map(p -> p.getDataset())
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(DataElement::getDate)).entrySet().stream()
                .mapToInt(map -> map.getValue().stream().mapToInt(ele -> ele.getData()).sum()).toArray();
        return findLowestValue(resArray, resArray.length);
    }

    public static Integer getHighestDataByCountry(List<Country> dataset, String country) {
        List<Country> results = getCountriesEqualsName(dataset, country);
        if (results == null) {
            return null;
        }
        int[] resArray = results.stream()
                .map(p -> p.getDataset())
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(DataElement::getDate)).entrySet().stream()
                .mapToInt(map -> map.getValue().stream().mapToInt(ele -> ele.getData()).sum()).toArray();
        return findHighestValue(resArray, resArray.length);
    }

    // Currying
    private static final Function<Integer, Function<Integer, Integer>> findMin = a -> (b -> ((a > b) ? b : a));
    private static final Function<Integer, Function<Integer, Integer>> findMax = a -> (b -> ((a > b) ? a : b));

    private static final Computable<Integer, Integer> computable = new ComputableImpl();

    private static Integer findSum(int[] data, int count) {
        if (count <= 0) {
            return 0;
        }
        return computable.add(findSum(data, count - 1), data[count - 1]);
    }

    // Recursion (Tail)
    private static Integer findHighestValue(int[] data, int count) {
        if (count == 1) {
            return data[0];
        }

        return findMax.apply(data[count - 1]).apply(findHighestValue(data, count - 1));
    }

    private static Integer findLowestValue(int[] data, int count) {
        if (count == 1) {
            return data[0];
        }

        return findMin.apply(data[count - 1]).apply(findLowestValue(data, count - 1));
    }
}
