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
    
    public static final SimpleDateFormat weekYearFormat = new SimpleDateFormat("'Week:'w',Year:'Y");
    public static final SimpleDateFormat monthYearFormat = new SimpleDateFormat("'Month:'M',Year:'Y");

    public static Date parseDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        return formatter.parse(date);
    }
}
