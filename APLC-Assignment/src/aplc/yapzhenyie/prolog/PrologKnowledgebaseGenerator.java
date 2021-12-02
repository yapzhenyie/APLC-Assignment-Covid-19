/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie.prolog;

import aplc.yapzhenyie.data.Country;
import aplc.yapzhenyie.utils.StatisticReportUtils;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Yap Zhen Yie
 */
public class PrologKnowledgebaseGenerator {

    public static void createKnowledgebaseFile(List<Country> dataset) throws FileNotFoundException {
        PrintWriter plFile = new PrintWriter("APLC-Assignment-Knowledgebase-File.pl");
        // Generate Facts
        plFile.println("% Facts");
        getCountriesConfirmedCases(dataset, new LinkedHashMap<String, Integer>(), 0).forEach((countryName, confirmedCases) -> {
            String newCountryName = countryName.toLowerCase().replace(" ", "_").replaceAll("[^A-Za-z0-9_]", "");
            plFile.println("confirmed_cases(" + newCountryName + ", " + confirmedCases + ").");
        });

        // Generate Rules
        plFile.println("");
        plFile.println("% Rules");
        plFile.println("merge_cases_to_list(List) :- findall((CountryName, Cases), confirmed_cases(CountryName, Cases), List).");
        plFile.println("");
        plFile.println("ascending_pivot(_, [], [], []).");
        plFile.println("ascending_pivot((Pivot1, Pivot2), [(Head1, Head2)|Tail], [(Head1, Head2)|List1], List2) :- Pivot2 > Head2, ascending_pivot((Pivot1, Pivot2), Tail, List1, List2).");
        plFile.println("ascending_pivot((Pivot1, Pivot2), [(Head1, Head2)|Tail], List1, [(Head1, Head2)|List2]) :- ascending_pivot((Pivot1, Pivot2), Tail, List1, List2).");
        plFile.println("");
        plFile.println("ascending_quicksort([], []).");
        plFile.println("ascending_quicksort([(Head1, Head2)|Tail], Result) :- ascending_pivot((Head1, Head2), Tail, List1, List2), "
                + "ascending_quicksort(List1, SortedList1), ascending_quicksort(List2, SortedList2), "
                + "append(SortedList1, [(Head1, Head2)|SortedList2], Result).");
        plFile.println("");
        plFile.println("descending_pivot(_, [], [], []).");
        plFile.println("descending_pivot((Pivot1, Pivot2), [(Head1, Head2)|Tail], [(Head1, Head2)|List1], List2) :- Pivot2 < Head2, descending_pivot((Pivot1, Pivot2), Tail, List1, List2).");
        plFile.println("descending_pivot((Pivot1, Pivot2), [(Head1, Head2)|Tail], List1, [(Head1, Head2)|List2]) :- descending_pivot((Pivot1, Pivot2), Tail, List1, List2).");
        plFile.println("");
        plFile.println("descending_quicksort([], []).");
        plFile.println("descending_quicksort([(Head1, Head2)|Tail], Result) :- descending_pivot((Head1, Head2), Tail, List1, List2), "
                + "descending_quicksort(List1, SortedList1), descending_quicksort(List2, SortedList2), "
                + "append(SortedList1, [(Head1, Head2)|SortedList2], Result).");
        plFile.println("");
        plFile.println("printlist([]).");
        plFile.println("printlist([Head|Tail]) :- write(Head), nl, printlist(Tail).");
        plFile.println("");
        plFile.println("ascending_order(Result):- merge_cases_to_list(List), ascending_quicksort(List, Result), printlist(Result).");
        plFile.println("descending_order(Result):- merge_cases_to_list(List), descending_quicksort(List, Result), printlist(Result).");

        plFile.close();
    }

    private static LinkedHashMap<String, Integer> getCountriesConfirmedCases(List<Country> dataset, LinkedHashMap<String, Integer> result, int count) {
        if (count == dataset.size()) {
            return result;
        }

        String countryName = dataset.get(count).getCountryName();

        LinkedHashMap<String, Integer> newResult = (LinkedHashMap<String, Integer>) result.entrySet()
                .stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
        newResult.put(countryName, StatisticReportUtils.getTotalConfirmedCasesByCountry(dataset, countryName));

        return getCountriesConfirmedCases(dataset, newResult, count + 1);
    }
}
