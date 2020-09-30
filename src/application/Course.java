package application;

public class Course {

	// Declare attribut
	private String courseID;
	private String name;
	private String credits;

	//Getter and Setters for the attribute 
	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
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
		this.courseID = courseCode;
		this.name = name;
		this.credits = credits;
	}
	
}
