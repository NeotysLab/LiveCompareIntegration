package com.neotys.tricentis.workloadParser.graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;

import org.jfree.data.time.*;
import org.jfree.ui.RectangleInsets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class GraphUtils {


    public static byte [] getTimeSeriesGraph(Map<String,Long> updateCounts, int frequency) throws Exception {
        if(frequency != Calendar.MINUTE && frequency != Calendar.SECOND) {
            throw new IllegalArgumentException("Unsupported time series frequency specified - must be minutes or seconds");
        }
        // build the dataset
        TimeSeries ts = new TimeSeries("UpdateCount");
        RegularTimePeriod p = null;
        Date d = null;
        Date start = null;
        Date end = null;
        DateFormat f = null;
        if(frequency == Calendar.MINUTE) {
            f = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        }
        if(frequency == Calendar.SECOND) {
            f = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        }
        for(String s : updateCounts.keySet()) {
            d = f.parse(s);
            if(start == null || d.getTime() < start.getTime()) {
                start = d;
            }
            if(end == null || d.getTime() > end.getTime()) {
                end = d;
            }
            if(frequency == Calendar.MINUTE) {
                p = RegularTimePeriod.createInstance(Minute.class, d, TimeZone.getDefault());
                ts.add(p, updateCounts.get(s));
            }
            if(frequency == Calendar.SECOND) {
                p = RegularTimePeriod.createInstance(Second.class, d, TimeZone.getDefault());
                ts.add(p, updateCounts.get(s));
            }
        }
        String chartTitle = null;
        String xTitle = null;
        if(frequency == Calendar.MINUTE) {
            chartTitle = "Updates per Minute";
            if(end != null && start != null) {
                long minuteDelta = (end.getTime() - start.getTime()) / (60 * 1000);
                chartTitle = chartTitle + " (Last " + minuteDelta + " Minutes)";
                xTitle = "minutes";
            }
        }
        if(frequency == Calendar.SECOND) {
            chartTitle = "Updates per Second";
            if(end != null && start != null) {
                long delta = (end.getTime() - start.getTime()) / (1000);
                chartTitle = chartTitle + " (Last " + delta + " Seconds)";
                xTitle = "seconds";
            }
        }
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(ts);
        return getGraphFromDataSet(dataset, chartTitle, xTitle, "updates");
    }

    public static byte [] getGraphFromDataSet(TimeSeriesCollection dataset, String titleText, String xLabel, String yLabel) throws Exception {
        // create the chart
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                titleText,  // title
                xLabel,          // x-axis label
                yLabel,          // y-axis label
                dataset,            // data
                true,               // create legend?
                true,               // generate tooltips?
                false               // generate URLs?
        );

        TextTitle title = chart.getTitle();
        title.setPaint(new Color(0, 84, 148));
        title.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 12));
        XYPlot plot = (XYPlot) chart.getPlot();

        Color fgColor = new Color(46, 110, 158);
        Color bgColor = new Color(228, 232, 234);
        Color gridColor = new Color(128, 128, 128);

        chart.getLegend().setItemPaint(fgColor);

        plot.setBackgroundPaint(bgColor);
        plot.setDomainGridlinePaint(gridColor);
        plot.setRangeGridlinePaint(gridColor);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        plot.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseItemLabelPaint(fgColor);
            renderer.setSeriesItemLabelPaint(0, fgColor);
            renderer.setBaseFillPaint(fgColor);
            renderer.setBaseOutlinePaint(fgColor);
            renderer.setSeriesPaint(0, fgColor);
            renderer.setBaseShapesVisible(false);
            renderer.setBaseShapesFilled(false);
            renderer.setDrawSeriesLineAsPath(true);
        }
        BufferedImage img = chart.createBufferedImage(600, 300);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "PNG", baos);
        return baos.toByteArray();

    }
}

