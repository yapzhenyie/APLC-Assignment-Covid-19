/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Yap Zhen Yie
 */
public class ChartUtils {

    public static JFreeChart generateBarChart(String title, String xAxisTitle, String yAxisTitle, DefaultCategoryDataset dataSet) {
        JFreeChart chart = ChartFactory.createBarChart(title, xAxisTitle, yAxisTitle,
                dataSet, PlotOrientation.VERTICAL, true, true, false);
        return chart;
    }
}
