package Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;	
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import joinery.DataFrame;
import smile.clustering.KMeans;
import smile.clustering.PartitionClustering;
import smile.plot.swing.ScatterPlot;

public class ConsumerTest {

	public static void main(String[] args) {
		
		Consumer consumer = new Consumer();
		Scanner in = new Scanner(System.in);
		
		boolean flag = true;
		
		while (flag) {
			
			System.out.println("1 - Display data\n2 - Display structure and summary\n3 - Clean Data"
					+ "\n4 - Display most demanding companies\n5 - Pie chart for most demanding companies"
					+ "\n6 - Display most popular job titles\n7 - Bar chart for most popular job titles"
					+ "\n8 - Display most popular areas\n9 - Bar chart for most popular areas"
					+ "\n10 - Display most important skills\n11 - Bar chart for most important skills"
					+ "\n12 - Factorize years of experience\n13 - K-means scatter plot for job titles and companies\n14 - Quit");
			int x = in.nextInt();
			
			if (x==1) {
				
				List<List<String>> ls = consumer.readData();
				List <String> names = Arrays.asList("Title","Company","Location","Type","Level","YearsExp","Country","Skills");
				
				DataFrame<String> df = new DataFrame<>(names);
				for (int i = 0;i<ls.size();i++) {
					df.append(ls.get(i));
				}
				
				System.out.print(df);
			}
			
			else if (x==2) {
				
				List <String> ls = consumer.sumAndStr();
				System.out.println("Summary");
				System.out.println(ls.get(0));
				System.out.println("Structure");
				System.out.println(ls.get(1));
			}
			
			else if (x==3) {
				
				List<List<String>> ls = consumer.cleanData();
				List <String> names = Arrays.asList("Title","Company","Location","Type","Level","YearsExp","Country","Skills");
				
				DataFrame<String> df = new DataFrame<>(names);
				for (int i = 0;i<ls.size();i++) {
					df.append(ls.get(i));
				}
				
				System.out.print(df.head());
			}
			
			else if (x==4) {
				
				List<List<String>> companies = consumer.countCompanies();
				List <String> names = Arrays.asList("Company","Count");
				
				DataFrame<String> df = new DataFrame<>();
				for (int i = 0;i<names.size();i++) {
					df.add(names.get(i),companies.get(i));
				}
				
				System.out.print(df.head());
			}
			
			else if (x==5) {
				
				List<List<String>> companies = consumer.countCompanies();
				Render.displayPie(companies);
			}
			
			else if (x==6) {
				
				List<List<String>> jobs = consumer.countJobs();
				List <String> names = Arrays.asList("Title","Count");
				
				DataFrame<String> df = new DataFrame<>();
				for (int i = 0;i<jobs.size();i++) {
					df.add(names.get(i),jobs.get(i));
				}
				
				System.out.print(df.head());
			}
			
			else if (x==7) {
				
				List<List<String>> jobs = consumer.countJobs();
				Render.displayBar(jobs);
			}
			
			else if (x==8) {
				
				List<List<String>> areas = consumer.countAreas();
				List <String> names = Arrays.asList("Area","Count");
				
				DataFrame<String> df = new DataFrame<>();
				for (int i = 0;i<areas.size();i++) {
					df.add(names.get(i),areas.get(i));
				}
				
				System.out.print(df.head());
			}
			
			else if (x==9) {
				
				List<List<String>> areas = consumer.countAreas();
				Render.displayBar(areas);
			}
			
			else if (x==10) {
				
				List<List<String>> skills = consumer.countSkills();
				List <String> names = Arrays.asList("Skill","Count");
				
				DataFrame<String> df = new DataFrame<>();
				for (int i = 0;i<skills.size();i++) {
					df.add(names.get(i),skills.get(i));
				}
				
				System.out.print(df.head());
			}
			
			else if (x==11) {
				
				List<List<String>> skills = consumer.countSkills();
				Render.displayBar(skills);
			}
			
			else if (x==12) {
				
				List<List<String>> ls = consumer.factExp();
				List <String> names = Arrays.asList("Title","Company","Location","Type","Level","YearsExp","Country","Skills","FactYearsExp");
				
				DataFrame<String> df = new DataFrame<>(names);
				for (int i = 0;i<ls.size();i++) {
					df.append(ls.get(i));
				}
				
				System.out.print(df.head());
			}
			
			else if (x==13) {
				
				double [][] kmeans = consumer.kmeans();
				KMeans clusters = PartitionClustering.run(100, () -> KMeans.fit(kmeans,3));
				try {
					ScatterPlot.of(kmeans, clusters.y, '.').canvas().setAxisLabels("Companies", "Jobs").window();
				} catch (InvocationTargetException | InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			else if (x==14) {
				flag = false;
			}
			
			else {
				System.out.print("Wrong input\n");
			}
		}
		in.close();
	}

}
