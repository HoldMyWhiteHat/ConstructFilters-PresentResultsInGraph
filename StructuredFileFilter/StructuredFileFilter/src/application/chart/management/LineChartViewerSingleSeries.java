package application.chart.management;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;

public class LineChartViewerSingleSeries {

	private List<String[]> series;
	private String xAxisName;
	private String yAxisName;
	private int xPosition;
	private int yPosition;

	public LineChartViewerSingleSeries(List<String[]> series, String pXAxisName, String pYAxisName, int pXPosition,
			int pYPosition) {
		this.xAxisName = pXAxisName;
		this.yAxisName = pYAxisName;
		this.series = series;
		this.xPosition = pXPosition;
		this.yPosition = pYPosition;
	}

	public XYChart getChart() {

		// Create Chart
		XYChart chart = new XYChartBuilder().width(800).height(600)
				// .title(getClass().getSimpleName())
				.title(yAxisName + " over " + xAxisName).xAxisTitle(xAxisName).yAxisTitle(yAxisName).build();

		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setPlotGridLinesVisible(false);
		chart.getStyler().setXAxisDecimalPattern("#0");// no decimals, if e.g., 4 decimals, the pattern is #0.0000

		// KILLED BY DEATH
		// Series
		// chart.addSeries("test 1", Arrays.asList(0, 1, 2, 3, 4), Arrays.asList(4, 5,
		// 9, 6, 5));

		// Series
		List<Number> xAxisValues = new ArrayList<Number>();
		List<Double> yAxisValues = new ArrayList<Double>();
		for (String[] row : series) {
			Integer xValueInt = Integer.parseInt(row[xPosition].trim());
			xAxisValues.add(xValueInt);

			Double yValue = Double.valueOf(row[yPosition]);
			yAxisValues.add(yValue);
		}

		chart.addSeries("series", xAxisValues, yAxisValues);

		return chart;
	}

	public String getExampleChartName() {
		return this.yAxisName + " over " + this.xAxisName;
		// return getClass().getSimpleName() + " - Basic Bar Chart";
	}
}
