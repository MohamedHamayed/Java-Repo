package Test;

import java.util.List;

public class Row {
	
	private String title;
	private String company;
	private String location;
	private String type;
	private String level;
	private String yearsExp;
	private String country;
	private String skills;
	
	public Row() {
		super();
	}
	
	public Row(List<String> header) {
		super();
		this.title = header.get(0);
		this.company = header.get(1);
		this.location = header.get(2);
		this.type = header.get(3);
		this.level = header.get(4);
		this.yearsExp = header.get(5);
		this.country = header.get(6);
		this.skills = header.get(7);
	}

	@Override
	public String toString() {
		return "Row [title=" + title + ", company=" + company + ", location=" + location + ", type=" + type + ", level="
				+ level + ", yearsExp=" + yearsExp + ", country=" + country + ", skills=" + skills + "]";
	}

	public String getTitle() {
		return title;
	}

	public String getCompany() {
		return company;
	}

	public String getLocation() {
		return location;
	}

	public String getType() {
		return type;
	}

	public String getLevel() {
		return level;
	}

	public String getYearsExp() {
		return yearsExp;
	}

	public String getCountry() {
		return country;
	}

	public String getSkills() {
		return skills;
	}


}
