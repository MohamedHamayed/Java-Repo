package ITI.Project;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.springframework.stereotype.Component;

import joinery.DataFrame;
import smile.io.Read;
import smile.io.Write;
import tech.tablesaw.api.Table;
import tech.tablesaw.conversion.smile.SmileConverter;

public class Functions {     // Here we Identifying Functions only
	
	public String pt = "Wuzzuf_Jobs.csv";               // My Csv File
 	
	
	public Table readDataTable(String path) {     // A Function called ReadDataTable which returns a Table ( Using TableSaw ) and takes a string
		
		try {
			
			Table data = Table.read().csv(path);         // Object from the Table saw
			return data;                                 // ReadDataTable Function takes a String (path), Returns Table  
			
		} 
		
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public DataFrame readDataJoinery(String path) {	   // A Function called readDataJoinery which returns a  Joinery Dataframe ( Using DataFrame ) and takes a string
		
		try {
			
			DataFrame data = DataFrame.readCsv(path);  // An Object from the DataFrame
			return data;                               // readDataJoinery Function takes a String(path) , Returns Joinary DataFrame
			 
		} 
		
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public smile.data.DataFrame readDataSmile(String path) {    // A Function called readDataSmile which returns a Smile Dataframe ( Using DataFrame ) and takes a string
		
		CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(',');
		try {
			
			smile.data.DataFrame data = Read.csv(path,format);        // An Object from the Smile DataFrame
			return data;                                              // readDataSmile Function Takes a String(path) , returns  smile.data.DataFrame
			
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
	
	public List<String> structureAndSummary(smile.data.DataFrame data) { // A Function called structureAndSummary which returns a List of <String> and takes smile dataframe
		
		List<String> ss = new ArrayList<String>();            // An Object from el List<String> 
		ss.add(data.summary().toString());                    // Summary() is a built-in Function in Smile DataFrame to make a Summary of the data
		ss.add(data.structure().toString());                  // Structure() is a built-in Function in Smile DataFrame to make a Structure of the Data.
		
		return ss;                                            // structureAndSummary Function Takes a Dataframe , Returns a list of ( Summary and Structure ) 
		
	}
	
	// Data Cleaning : 
	
	public DataFrame cleanData(Table data) { // A Function called cleanData which returns a DataFrame and takes a Tablesaw
		
		this.pt = "Wuzzuf_Jobs_Filtered.csv";
		CSVFormat format = CSVFormat.DEFAULT.withDelimiter(',');  // ta3deeel for Osamaaa
		
		Table clean_data = data.dropRowsWithMissingValues().dropDuplicateRows();
//		SmileConverter cd = clean_data.smile();
//		smile.data.DataFrame df = cd.toDataFrame();
//		Path path = Paths.get("Wuzzuf_Jobs_Filtered.csv");
		try {
			clean_data.write().csv(this.pt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return readDataJoinery(this.pt).dropna();
		
	}
	
	public List<List<String>> countJobs(DataFrame df) {
		
		df = df.retain("Title","Company").groupBy("Title").count().sortBy(-1).slice(0, 6);
		List<List<String>> ls = new ArrayList<List<String>>();
		List<String> titleList= df.col("Title");
		List<Integer> countList=df.col("Company");
		List<String> sCountList = countList.stream().map(String::valueOf).collect(Collectors.toList()); 
		ls.add(titleList);
		ls.add(sCountList);
		return ls;
	}
	
	public List<List<String>> countCompanies(DataFrame df) {
		
		df = df.retain("Title","Company").groupBy("Company").count().sortBy(-1).slice(1, 6);
		List<List<String>> ls = new ArrayList<List<String>>();
		List<String> titleList= df.col("Company");
		List<Integer> countList=df.col("Title");
		List<String> sCountList = countList.stream().map(String::valueOf).collect(Collectors.toList()); 
		ls.add(titleList);
		ls.add(sCountList);
		return ls;
	}
	
	public List<List<String>> countAreas(DataFrame df) {
		
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
		df = df.retain("Area","Company").groupBy("Area").count().sortBy(-1).slice(0, 6);
		List<List<String>> ls = new ArrayList<List<String>>();
		List<String> titleList= df.col("Area");
		List<Integer> countList=df.col("Company");
		List<String> sCountList = countList.stream().map(String::valueOf).collect(Collectors.toList()); 
		ls.add(titleList);
		ls.add(sCountList);
		return ls;
	}
	
	public List<List<String>> countSkills(DataFrame df) {
		
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
        
        Map<String, Integer> countSorted = new HashMap<String, Integer>();
        countSorted = count.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(5).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        
        List<List<String>> ls = new ArrayList<List<String>>();
        List<String> titleList = countSorted.keySet().stream().toList();
        List<Integer> countList = countSorted.values().stream().toList();
        List<String> sCountList = countList.stream().map(String::valueOf).collect(Collectors.toList()); 
		ls.add(titleList);
		ls.add(sCountList);
		return ls;
	}
	
	public DataFrame factorizeExp(DataFrame df) {
		
		List<String> years = df.col("YearsExp");
		List<String> fd = new ArrayList<String>();
		for (int i = 0;i<years.size();i++) {
			
			Pattern p = Pattern.compile("\\d+");
	        Matcher m = p.matcher((CharSequence) years.get(i));
	        boolean flag = false;
	        while(m.find()) {
	        	flag = true;
	        	if (Integer.parseInt(m.group()) < 5) {
	            	fd.add("0");
	            	break;
	            }
	            else if (Integer.parseInt(m.group()) <= 5 || Integer.parseInt(m.group()) < 10) {
	            	fd.add("1");
	            	break;
	            }
	            else if (Integer.parseInt(m.group()) <= 10 || Integer.parseInt(m.group()) < 15) {
	            	fd.add("2");
	            	break;
	            }
	            else {
	            	fd.add("3");
	            	break;
	            }
	        }
	        if (flag == false) {
	        	fd.add("0");
	        }
	        	
		}
		
		df.add("FactorizedExpYears", fd);
		return df;
		
	}

}
