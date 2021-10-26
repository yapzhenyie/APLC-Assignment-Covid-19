/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie.utils;

import java.text.DecimalFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author Yap Zhen Yie
 */
public class ChartUtils {

    public static JFreeChart generatePieChart(String title, DefaultPieDataset dataSet) {
        JFreeChart chart = ChartFactory.createPieChart(title, dataSet, true, true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        plot.setLabelGenerator(gen);

        return chart;
    }

    public static JFreeChart generateBarChart(String title, String xAxisTitle, String yAxisTitle, DefaultCategoryDataset dataSet) {
        JFreeChart chart = ChartFactory.createBarChart(title, xAxisTitle, yAxisTitle,
                dataSet, PlotOrientation.VERTICAL, true, true, false);
        return chart;
    }
}
