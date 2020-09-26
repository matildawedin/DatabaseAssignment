package application;


import java.util.ArrayList;
import java.util.HashMap;

public class Course {

	// Declare attribut
	private String courseCode;
	private String name;
	private String credits;
	
	private HashMap<String, Result> resultList = new HashMap<String, Result>();
	private HashMap<String, Student> studentList = new HashMap<String, Student>();
	
	
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

	public HashMap<String, Result> getResultList() {
		return resultList;
	}

	public void setResultList(HashMap<String, Result> resultList) {
		this.resultList = resultList;
	}
	
	public HashMap<String, Student> getStudentList() {
		return studentList;
	}
	public void setStudentList(HashMap<String, Student> studentList) {
		this.studentList = studentList;
	}
	
	// Methods
	
	public void addResult(String studentID, Result result) {
		resultList.put(studentID, result);
	}
	
	public Result findResult(String studentID) {
		return resultList.get(studentID);
	}
	
	//Constructor
	public Course(String courseCode, String name, String credits) {
		super();//vad glör denna? den genererades automatisk 
		this.courseCode = courseCode;
		this.name = name;
		this.credits = credits;
	}
	
}
