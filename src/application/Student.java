package application;

public class Student {

	// Declare attribut
	private String studentID;
	private String name;

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

}
