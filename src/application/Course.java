package application;

public class Course {

	// Declare attribut
	private String courseCode;
	private String name;
	private String credits;

	//Getter and Setters for the attribute 
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCredits() {
		return credits;
	}
	public void setCredits(String credits) {
		this.credits = credits;
	}
	
	//Constructor
	public Course(String courseCode, String name, String credits) {
		super();//vad glör denna? den genererades automatisk 
		this.courseCode = courseCode;
		this.name = name;
		this.credits = credits;
	}
	
}
