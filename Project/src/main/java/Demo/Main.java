package Demo;

import java.io.IOException;		
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.poi.hwpf.model.types.LSTFAbstractType;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler.ChartTheme;

import joinery.DataFrame;

import smile.data.*;
import smile.data.measure.NominalScale;
import smile.data.vector.IntVector;
import smile.io.*;
import tech.tablesaw.api.Table;
import tech.tablesaw.conversion.smile.SmileConverter;
import tech.tablesaw.joining.DataFrameJoiner;


public class Main {

	public static Table readDataTable(String path) {
		
		try {
			
			Table data = Table.read().csv(path);
			return data;
			
		} 
		
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static DataFrame readDataJoinery(String path) {	
		
		try {
			
			DataFrame data = DataFrame.readCsv(path);
			return data;
			
		} 
		
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static smile.data.DataFrame readDataSmile(String path) {
		
		CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(',');
		try {
			
			smile.data.DataFrame data = Read.csv(path,format);
			return data;
			
		} 
		
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<smile.data.DataFrame> structureAndSummary(smile.data.DataFrame data) {
		
		List <smile.data.DataFrame> ss = new ArrayList<smile.data.DataFrame>();
		ss.add(data.summary());
		ss.add(data.structure());
		
		return ss;
		
	}
	
	public static DataFrame cleanData(Table data) {
		
		Table clean_data = data.dropRowsWithMissingValues().dropDuplicateRows();
		List <Row> rows = new ArrayList<Row>();
		
		for (int i = 0;i<clean_data.rowCount();i++) {
			String[] header = new String[8];
			for (int j = 0;j<clean_data.columnCount();j++) {
				header[j] = clean_data.getString(i, j);
			}
			Row row = new Row(header);
			rows.add(row);
		}
		
		List <String> names = new ArrayList<String>();
		names.add("Title");
		names.add("Company");
		names.add("Location");
		names.add("Type");
		names.add("Level");
		names.add("YearsExp");
		names.add("Country");
		names.add("Skills");
		
		DataFrame<String> df = new DataFrame<>(names);
		for (int i = 0;i<rows.size();i++) {
			df.append(Arrays.asList(rows.get(i).getTitle()
					,rows.get(i).getCompany()
					,rows.get(i).getLocation()
					,rows.get(i).getType()
					,rows.get(i).getLevel()
					,rows.get(i).getYearsExp()
					,rows.get(i).getCountry()
					,rows.get(i).getSkills()));
		}
		
		return df;
	
	}
	
	public static DataFrame cleanDataTwo(Table data,String pa) {
		
		CSVFormat format = CSVFormat.DEFAULT.withDelimiter(',');
		
		Table clean_data = data.dropRowsWithMissingValues().dropDuplicateRows();
		SmileConverter cd = clean_data.smile();
		smile.data.DataFrame df = cd.toDataFrame();
		Path path = Paths.get(pa);
		
		try {
			Write.csv(df, path, format);
		} 
		
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return readDataJoinery(pa).dropna();
		
	}
	
	public static List<List<String>> countJobs(DataFrame df) {
		
		df = df.retain("Title","Company").groupBy("Title").count().sortBy(-1).slice(0, 6);
		List<List<String>> ls = new ArrayList<List<String>>();
		List<String> titleList= df.col("Title");
		List<Integer> countList=df.col("Company");
		List<String> sCountList = countList.stream().map(String::valueOf).collect(Collectors.toList()); 
		ls.add(titleList);
		ls.add(sCountList);
		return ls;
	}
	
	public static List<List<String>> countCompanies(DataFrame df) {
		
		df = df.retain("Title","Company").groupBy("Company").count().sortBy(-1).slice(1, 6);
		List<List<String>> ls = new ArrayList<List<String>>();
		List<String> titleList= df.col("Company");
		List<Integer> countList=df.col("Title");
		List<String> sCountList = countList.stream().map(String::valueOf).collect(Collectors.toList()); 
		ls.add(titleList);
		ls.add(sCountList);
		return ls;
	}
	
	public static List<List<String>> countAreas(DataFrame df) {
		
		List<String> cities = Arrays.asList("Alexandria","Assiut","Aswan","Beheira","Beni Suef","Cairo","Dakahlia","Damietta","Fayoum","Gharbia","Giza","Ismailia","Matruh","Minya","Monufya","New Valley","Qalubia","Qena","Red Sea","Sharqia","South Sinai","Suez");
		List<String> countries = df.col("Country");
		List<String> areas = new ArrayList<String>();
		
		for(int i = 0; i < countries.size(); i++) {
			boolean flag = true;
			for(int j = 0; j < cities.size(); j++) {
				if (countries.get(i).equals(cities.get(j))) {
					areas.add("Egypt");
					flag = false;
					break;
				}
				
			}
			if (flag) {
				areas.add(countries.get(i));
			}
		}
		
		df.add("Area", areas);
		System.out.print(df.unique("Area").retain("Area"));
		df = df.retain("Area","Company").groupBy("Area").count().sortBy(-1).slice(1, 6);
		List<List<String>> ls = new ArrayList<List<String>>();
		List<String> titleList= df.col("Area");
		List<Integer> countList=df.col("Company");
		List<String> sCountList = countList.stream().map(String::valueOf).collect(Collectors.toList()); 
		ls.add(titleList);
		ls.add(sCountList);
		return ls;
	}
	
	public static List<List<String>> countAreasTwo(DataFrame df) {
		
		List<String> location= df.col("Location");
		List<String> country= df.col("Country");
		List<String> Area = new ArrayList<String>();
		
		for(int i = 0; i < location.size(); i++) {
			Area.add(location.get(i)+","+country.get(i)); 
		}
		
		df.add("Area", Area);
		
		df = df.retain("Area","Company").groupBy("Area").count().sortBy(-1).slice(0, 6);
		List<List<String>> ls = new ArrayList<List<String>>();
		List<String> titleList= df.col("Area");
		List<Integer> countList=df.col("Company");
		List<String> sCountList = countList.stream().map(String::valueOf).collect(Collectors.toList()); 
		ls.add(titleList);
		ls.add(sCountList);
		return ls;
	}
	
	public static Map<String,Integer> countSkills(DataFrame df) {
		
		List<String> skills = df.col("Skills");
		List<String> sk = new ArrayList<String>();
		for(int i = 0; i < skills.size(); i++) {
			String[] skill = skills.get(i).split(",");
			for(int j = 0; j < skill.length; j++) {
				sk.add(skill[j].trim());
			}
		}
		
		Map<String, Integer> count = new HashMap<String, Integer>();
		  
        for (String i : sk) {
            Integer j = count.get(i);
            count.put(i, (j == null) ? 1 : j + 1);
        }
        
		return count;
	}
	
	public static DataFrame factorizeExp(DataFrame df) {
		
		List<String> years = df.col("YearsExp");
		List<Integer> fd = new ArrayList<Integer>();
		for (int i = 0;i<years.size();i++) {
			
			Pattern p = Pattern.compile("\\d+");
	        Matcher m = p.matcher((CharSequence) years.get(i));
	        boolean flag = false;
	        while(m.find()) {
	        	flag = true;
	        	if (Integer.parseInt(m.group()) < 5) {
	            	fd.add(0);
	            	break;
	            }
	            else if (Integer.parseInt(m.group()) <= 5 || Integer.parseInt(m.group()) < 10) {
	            	fd.add(1);
	            	break;
	            }
	            else if (Integer.parseInt(m.group()) <= 10 || Integer.parseInt(m.group()) < 15) {
	            	fd.add(2);
	            	break;
	            }
	            else {
	            	fd.add(3);
	            	break;
	            }
	        }
	        if (flag == false) {
	        	fd.add(0);
	        }
	        	
		}
		
		df.add("FactorizedExpYears", fd);
		return df;
		
	}
	
	public static void main(String[] args) {
		
		smile.data.DataFrame data_s = readDataSmile("Wuzzuf_Jobs.csv");
		DataFrame data_j = readDataJoinery("Wuzzuf_Jobs.csv");
		Table data_t = readDataTable("Wuzzuf_Jobs.csv");
		
		List <smile.data.DataFrame> ss = new ArrayList<smile.data.DataFrame>();
		ss = structureAndSummary(data_s);
		
		System.out.print(ss);
		System.out.print(data_j);
		
		data_j = cleanData(data_t);
		System.out.print("\n");
		System.out.print(data_j);
		
		data_j = cleanDataTwo(data_t,"Wuzzuf_Jobs_two.csv");
		System.out.print("\n");
		System.out.print(data_j);
		
		List<List<String>> lsJobs = countJobs(data_j);
		List<List<String>> lsCompanies = countCompanies(data_j);
		List<List<String>> lsAreas = countAreas(data_j);
		Map<String, Integer> mapSkills = countSkills(data_j);
		
		mapSkills.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(5).forEach(System.out::println);
		
		DataFrame data_f = factorizeExp(data_j);
		System.out.print("\n");
		System.out.print(data_j);
		
		
		
		List<String> title = lsAreas.get(0);
		List<Integer> count = lsAreas.get(1).stream().map(Integer::parseInt).collect(Collectors.toList());
		 // Create Chart
	    CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Title vs. Count").xAxisTitle("Title").yAxisTitle("Count").theme(ChartTheme.GGPlot2).build();
	 
	    chart.addSeries(".",title,count);
	    new SwingWrapper<CategoryChart>(chart).displayChart();
		
	}

}
