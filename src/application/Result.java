package application;
public class Result {
	
	// Attributes
	private String grade;
	private char letterGrade;
	private String studentID;
	private String courseCode;
	private Student student;
	private Course course;
	
	
	// Getters and setters
	public String getResult() {
		return grade;
	}
	public void setResult(String result) {
		this.grade = result;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public char getLetterGrade() {
		return letterGrade;
	}
	public void setLetterGrade(char letterGrade) {
		this.letterGrade = letterGrade;
	}
	
	// Generates a character based on the result in points
		/*public void createLetterGrade() {
			if (result == 404) {
				setLetterGrade('I');
			} else {
				int maxPoints = exam.getMaxPoints();
				if (result >= (0.85 * maxPoints)) {
					setLetterGrade('A');
				} else if (result >= (0.75 * maxPoints)) {
					setLetterGrade('B');
				} else if (result >= (0.65 * maxPoints)) {
					setLetterGrade('C');
				} else if (result >= (0.55 * maxPoints)) {
					setLetterGrade('D');
				} else if (result >= (0.5 * maxPoints)) {
					setLetterGrade('E');
				} else {
					setLetterGrade('F');
				}

			}
		}*/
	
}
