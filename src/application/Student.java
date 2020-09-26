package application;


import java.util.HashMap;

public class Student {
	
	// Declare attribut
	private String studentID;
	private String name;

	
	private HashMap<String, Course> courseList = new HashMap<String, Course>();
	private HashMap<String, Result> resultList = new HashMap<String, Result>();
	
	//Getter and Setters for the attribute 
	
	public Student(String studentID, String studentName) {
		super();
		this.studentID = studentID;
		this.name = studentName;
	}

	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, Course> getCourseList() {
		return courseList;
	}
	public void setCourseList(HashMap<String, Course> courseList) {
		this.courseList = courseList;
	}
	public HashMap<String, Result> getResultList() {
		return resultList;
	}
	public void setResultList(HashMap<String, Result> resultList) {
		this.resultList = resultList;
	}
	
	// Methods
	
		public Result findResult(String courseCode) {
			return resultList.get(courseCode);
		}

		// Adds a new result for a given exam.
		public void addResult(String courseCode, Result result) {
			resultList.put(courseCode, result);
		}
	//Constructor
}
