package Test;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import joinery.DataFrame;


public class Consumer {
	
	@Autowired
	RestTemplate restTemplate = new RestTemplate();

	public List<List<String>> readData() {
		List<List<String>> df;
		df = restTemplate.getForObject("http://localhost:8080/readData", List.class);
		return df;
		}
	public List <String> sumAndStr() {
		List <String> ss;
		ss = restTemplate.getForObject("http://localhost:8080/summaryAndStructure", List.class);
		return ss;
		}
	public List<List<String>> cleanData() {
		List<List<String>> df;
		df = restTemplate.getForObject("http://localhost:8080/cleanData", List.class);
		return df;
		}
	public List<List<String>> countCompanies() {
		List<List<String>> df;
		df = restTemplate.getForObject("http://localhost:8080/countCompanies", List.class);
		return df;
		}
	public List<List<String>> countJobs() {
		List<List<String>> df;
		df = restTemplate.getForObject("http://localhost:8080/countJobs", List.class);
		return df;
		}
	public List<List<String>> countAreas() {
		List<List<String>> df;
		df = restTemplate.getForObject("http://localhost:8080/countAreas", List.class);
		return df;
		}
	public List<List<String>> countSkills() {
		List<List<String>> df;
		df = restTemplate.getForObject("http://localhost:8080/countSkills", List.class);
		return df;
		}
	public List<List<String>> factExp() {
		List<List<String>> df;
		df = restTemplate.getForObject("http://localhost:8080/factExp", List.class);
		return df;
		}
	public double[][] kmeans() {
		double[][] df;
		df = restTemplate.getForObject("http://localhost:8080/kmeans", double[][].class);
		return df;
		}
//	public Row getRow() {
//		Row row;
//		row = restTemplate.getForObject("http://localhost:8080/getRow", Row.class);
//		return row;
//		}
}
