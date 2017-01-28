package midFidelty;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ResultView {
	private double teachTime;
	private double researchTime;
	private double otherTime;
	public ResultView(){
		teachTime = 6;
		researchTime = 2.5;
		otherTime = 3.3;
	}
	public JFreeChart generatePieChart() {
		DefaultPieDataset dataSet = new DefaultPieDataset();
		dataSet.setValue("Teaching", teachTime);
		dataSet.setValue("Research", researchTime);
		dataSet.setValue("Other", otherTime);
		

		JFreeChart chart = ChartFactory.createPieChart(
				"Activity Time", dataSet, true, true, false);

		return chart;
	}

	
	public JFreeChart generateBarChart() {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		dataSet.setValue(teachTime, "", "Teaching");
		dataSet.setValue(researchTime, "", "Reseach");
		dataSet.setValue(otherTime, "", "Other");
		

		JFreeChart chart = ChartFactory.createBarChart(
				"Activity Time", "Activity Nature", "Time in hours",
				dataSet, PlotOrientation.VERTICAL, false, true, true);

		return chart;
	}
}
