package ITI.Project;

import java.util.ArrayList;	
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import joinery.DataFrame;
import tech.tablesaw.api.Table;

@RestController
public class Rest {
	
	Functions func = new Functions();
	
	@RequestMapping("/readData")
	public List<List<String>> readData() {
		
		List<List<String>> ls = new ArrayList<List<String>>();
		DataFrame data_j = func.readDataJoinery(func.pt);
		for(int i = 0 ; i<data_j.length() ; i++) {
			List<String> l = data_j.row(i);
			ls.add(l);
		}
		return ls;
	}
	
	@RequestMapping("/summaryAndStructure")
	public List <String> sumAndStr() {
		
		smile.data.DataFrame data_s = func.readDataSmile(func.pt);
		List <String> ss = new ArrayList<String>();
		ss = func.structureAndSummary(data_s);
		
		return ss;
	}
	
	@RequestMapping("/cleanData")
	public List<List<String>> cleanData() {
		
		List<List<String>> ls = new ArrayList<List<String>>();
		Table table = func.readDataTable(func.pt);
		DataFrame data_j = func.cleanData(table);
		for(int i = 0 ; i<data_j.length() ; i++) {
			List<String> l = data_j.row(i);
			ls.add(l);
		}
		return ls;
	}
	
	@RequestMapping("/countCompanies")
	public List<List<String>> countCompanies() {
		
		DataFrame data_j = func.readDataJoinery(func.pt);
		List<List<String>> ls = func.countCompanies(data_j);
		
		return ls;
	}
	
	@RequestMapping("/countJobs")
	public List<List<String>> countJobs() {
		
		DataFrame data_j = func.readDataJoinery(func.pt);
		List<List<String>> ls = func.countJobs(data_j);
		
		return ls;
	}
	
	@RequestMapping("/countAreas")
	public List<List<String>> countAreas() {
		
		DataFrame data_j = func.readDataJoinery(func.pt);
		List<List<String>> ls = func.countAreas(data_j);
		
		return ls;
	}
	
	@RequestMapping("/countSkills")
	public List<List<String>> countSkills() {
		
		DataFrame data_j = func.readDataJoinery(func.pt);
		List<List<String>> ls = func.countSkills(data_j);
		
		return ls;
	}
	
	@RequestMapping("/factExp")
	public List<List<String>> factExp() {
		
		List<List<String>> ls = new ArrayList<List<String>>();
		DataFrame data_j = func.readDataJoinery(func.pt);
		DataFrame data_f = func.factorizeExp(data_j);
		for(int i = 0 ; i<data_j.length() ; i++) {
			List<String> l = data_j.row(i);
			ls.add(l);
		}
		return ls;
	}
	
	@RequestMapping("/kmeans")
	public double[][] kmeans() {
		
		smile.data.DataFrame data_s = func.readDataSmile(func.pt);
		double[][] k = func.kmeans(data_s);
		
		return k;
	}
	
	@RequestMapping("/getRow")
	public Row row() {
		
		DataFrame data_j = func.readDataJoinery(func.pt);
		List<String> l = data_j.row(0);
		Row row = new Row(l);
		return row;
	}

}
