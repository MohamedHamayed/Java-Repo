package Test;

import java.util.List;	
import java.util.stream.Collectors;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler.ChartTheme;

public class Render {
	
	public static void displayBar(List<List<String>> df) {
		List<String> title = df.get(0);
		List<Integer> count = df.get(1).stream().map(Integer::parseInt).collect(Collectors.toList());
		 // Create Chart
	    CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Title vs. Count").xAxisTitle("Title").yAxisTitle("Count").theme(ChartTheme.GGPlot2).build();
	 
	    chart.addSeries(".",title,count);
	    new SwingWrapper<CategoryChart>(chart).displayChart();

	}
	
	public static void displayPie(List<List<String>> df) {
		List<String> title = df.get(0);
		List<Integer> count = df.get(1).stream().map(Integer::parseInt).collect(Collectors.toList());
		 // Create Chart
	    PieChart chart = new PieChartBuilder().width(800).height(600).title("Title vs. Count").theme(ChartTheme.GGPlot2).build();
	    for (int i=0;i<title.size();i++) {
	    	chart.addSeries(title.get(i),count.get(i));
	    	}
	    new SwingWrapper<PieChart>(chart).displayChart();

	}


}
