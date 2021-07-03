package Demo;

public class Row {
	
	private String title;
	private String company;
	private String location;
	private String type;
	private String level;
	private String yearsExp;
	private String country;
	private String skills;
	
	public Row(String [] header) {
		super();
		this.title = header[0];
		this.company = header[1];
		this.location = header[2];
		this.type = header[3];
		this.level = header[4];
		this.yearsExp = header[5];
		this.country = header[6];
		this.skills = header[7];
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
