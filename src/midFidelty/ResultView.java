package midFidelty;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.SimpleTimePeriod;

/*
 * @author: Mujtaba Ali
 */
public class ResultView {
	private double teachTime;
	private double researchTime;
	private double otherTime;
	
	
	private ResultSet rs;
	
	public ResultView(){
		teachTime = 6;
		researchTime = 2.5;
		otherTime = 3.3;
	}
	
	 private JFreeChart createChart(final IntervalCategoryDataset dataset) {
	        final JFreeChart chart = ChartFactory.createGanttChart(
	            "",  // chart title
	            "Activity",          // domain axis label
	            "Time",              // range axis label
	            dataset,             // data
	            false,                // include legend
	            true,                // tooltips
	            false                // urls
	        );    
//	        chart.getCategoryPlot().getDomainAxis().setMaxCategoryLabelWidthRatio(10.0f);
	        return chart;    
	    }
	 
	 
	 public static IntervalCategoryDataset createDataset() {
		 
		         final TaskSeries s1 = new TaskSeries("Scheduled");
		         s1.add(new Task("Research",
		                new SimpleTimePeriod(date(1, Calendar.HOUR),
		                		date(2, Calendar.HOUR))));
		         s1.add(new Task("Teaching",
		                new SimpleTimePeriod(date(2, Calendar.HOUR),
		                		date(3, Calendar.HOUR))));
		         s1.add(new Task("Other",
		                new SimpleTimePeriod(date(3, Calendar.HOUR),
		                		date(4, Calendar.HOUR))));
		         
		     	
		         final TaskSeries s2 = new TaskSeries("Actual");
		         
		         
		         s2.add(new Task("Research",
		                new SimpleTimePeriod(date(4, Calendar.HOUR),
		             		   				date(5, Calendar.HOUR))));
		         
		         
		         s2.add(new Task("Teaching",
		                new SimpleTimePeriod(date(5, Calendar.HOUR),
		                 					date(6, Calendar.HOUR))));
		         
		         
		         s2.add(new Task("Other",
		                new SimpleTimePeriod(date(6, Calendar.HOUR),
		                                     date(10, Calendar.HOUR))));
		        
		         
		         
		         
		         final TaskSeriesCollection collection = new TaskSeriesCollection();
		         collection.add(s1);
		         collection.add(s2);

		        
		         
		         return collection;
		     }
	 
	 private static Date date(final int hour, final int hourType) {

	        final Calendar calendar = Calendar.getInstance();
	        calendar.set(hourType, hour);
	        final Date result = calendar.getTime();
	        return result;

	    }
	
	
	
	
	
	
	public JFreeChart generateGanttChart(){
		JFreeChart chart=null;
		IntervalCategoryDataset dataset = createDataset();
        chart = createChart(dataset);
		
		return chart;
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
