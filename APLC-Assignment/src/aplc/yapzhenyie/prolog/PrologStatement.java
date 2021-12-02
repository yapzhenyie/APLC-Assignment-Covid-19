package aplc.yapzhenyie.prolog;

import aplc.yapzhenyie.APLCAssignment;
import java.util.Arrays;
import java.util.Map;
import org.jpl7.Query;
import org.jpl7.Term;

/**
 *
 * @author Yap Zhen Yie
 */
public class PrologStatement {

    private static final String PL_FILE = "APLC-Assignment-Knowledgebase-File.pl";

    public static void ConfirmedCasesInAscendingOrder() {
        connectPLFile(PL_FILE);
        
        printOutput("");
        printOutput("--------------- [ Covid-19 Confirmed Cases in Ascending Order ] ---------------");
        String queryStatement = "ascending_order(Result).";
        Query query = new Query(queryStatement);
        Map<String, Term> solution = query.oneSolution();
        String[] arr = solution.get("Result").toString().replace("','", "").replace("[(", "").replace(")]", "").split("\\), \\(");
        Arrays.asList(arr).forEach(c -> {
            printOutput(c);
        });
        printOutput("--------------- [ End ] ---------------");
    }

    public static void ConfirmedCasesInDescendingOrder() {
        connectPLFile(PL_FILE);
        
        printOutput("");
        printOutput("--------------- [ Covid-19 Confirmed Cases in Descending Order ] ---------------");
        String queryStatement = "descending_order(Result).";
        Query query = new Query(queryStatement);
        Map<String, Term> solution = query.oneSolution();
        String[] arr = solution.get("Result").toString().replace("','", "").replace("[(", "").replace(")]", "").split("\\), \\(");
        Arrays.asList(arr).forEach(c -> {
            printOutput(c);
        });
        printOutput("--------------- [ End ] ---------------");
    }

    private static void connectPLFile(String plFile) {
        String s = "consult('" + plFile + "')";
        Query q1 = new Query(s);
        printOutput((q1.hasSolution() ? "Success" : "Fail") + " to connect to " + plFile + " file.");
    }

    private static void printOutput(String message) {
        String currentMessage = APLCAssignment.getPrologPage().getPrologOutputArea().getText();
        if (currentMessage.isEmpty()) {
            currentMessage += message;
        } else {
            currentMessage += "\n" + message;
        }
        APLCAssignment.getPrologPage().getPrologOutputArea().setText(currentMessage);
    }
}
