/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Yap Zhen Yie
 */
public class DateTimeHelper {
    
    public static final SimpleDateFormat weekYearFormat = new SimpleDateFormat("'Week:'ww',Year:'Y");
    public static final SimpleDateFormat monthYearFormat = new SimpleDateFormat("'Month:'MM',Year:'yyyy");

    public static Date parseDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        return formatter.parse(date);
    }
    
    public static String convertDateAsString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy '('EEE')'");
        return formatter.format(date);
    }
    
    public static String convertDateToShortFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        return formatter.format(date);
    }
    
    public static String convertDateToMonthFormat(String monthYear) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM yyyy");
        return formatter.format(monthYearFormat.parse(monthYear));
    }
}
