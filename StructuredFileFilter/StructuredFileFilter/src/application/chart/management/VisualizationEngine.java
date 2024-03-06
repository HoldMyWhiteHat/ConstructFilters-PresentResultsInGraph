package application.chart.management;

import java.io.IOException;
import java.util.List;

import javax.swing.WindowConstants;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;

public class VisualizationEngine {

	public void showSingleSeriesBarChart(String pAlias, List<String[]> series, String pXAxisName, String pYAxisName,
			String outputFileName, int pXPosition, int pYPosition) {

		BarChartViewerSingleSeries exampleChart = new BarChartViewerSingleSeries(series, pXAxisName, pYAxisName,
				pXPosition, pYPosition);
		CategoryChart chart = exampleChart.getChart();
		new SwingWrapper<>(chart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// Save it
		if (!outputFileName.isEmpty()) {
			saveChartAsPng(outputFileName, chart);
		}
	}// end method showSingleSeriesBarChart()

	public void showSingleSeriesLineChart(String pAlias, List<String[]> series, String pXAxisName, String pYAxisName,
			String outputFileName, int pXPosition, int pYPosition) {

		LineChartViewerSingleSeries exampleChart = new LineChartViewerSingleSeries(series, pXAxisName, pYAxisName,
				pXPosition, pYPosition);
		XYChart chart = exampleChart.getChart();
		new SwingWrapper<>(chart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// Save it
		if (!outputFileName.isEmpty()) {
			saveChartAsPng(outputFileName, chart);
		}
	}// end method showSingleSeriesBarChart()

	private void saveChartAsPng(String outputFileName, CategoryChart chart) {
		try {
			BitmapEncoder.saveBitmap(chart, outputFileName, BitmapFormat.PNG);
		} catch (IOException e) {
			System.err.println("VizEngine::showSingleSeriesChart: unable to output " + outputFileName + ".png");
			e.printStackTrace();
		}
	}

	private void saveChartAsPng(String outputFileName, XYChart chart) {
		try {
			BitmapEncoder.saveBitmap(chart, outputFileName, BitmapFormat.PNG);
		} catch (IOException e) {
			System.err.println("VizEngine::showSingleSeriesChart: unable to output " + outputFileName + ".png");
			e.printStackTrace();
		}
	}
}
