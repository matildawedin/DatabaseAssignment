package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAL {

	private DbConnection dbc = new DbConnection();
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;


	public ObservableList<String> selectAllStudentID() throws SQLException{
		con = dbc.getConnection();
		ObservableList<String> oblistString = FXCollections.observableArrayList();
		try {
			String selectAll = "SELECT studentID FROM Student";
			rs = con.createStatement().executeQuery(selectAll);

			while(rs.next()) {
				oblistString.add(new String(rs.getString(1)));
			}
			return oblistString;
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	public ObservableList<String> selectAllCourseCode() throws SQLException{
		con = dbc.getConnection();
		ObservableList<String> oblistString = FXCollections.observableArrayList();
		
		try {
			String selectAll = "SELECT courseID FROM Course";
			rs = con.createStatement().executeQuery(selectAll);

			while(rs.next()) {
				oblistString.add(new String(rs.getString(1)));
			}
			return oblistString;
		}
		catch(SQLException e) {
			throw e;
		}
	}

	public ObservableList<Course> selectCourseByCode(String courseCode) throws SQLException{
		con = dbc.getConnection();
		ObservableList<Course> oblistC = FXCollections.observableArrayList();
		
		try{
			String findC = "SELECT * FROM Course WHERE courseID = '" + courseCode + "'";
			rs = con.createStatement().executeQuery(findC);

			while(rs.next()) {
				oblistC.add(new Course(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			return oblistC;
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	public ObservableList<Course> selectCoursebyName(String name) throws SQLException{
		con = dbc.getConnection();
		ObservableList<Course> oblistC = FXCollections.observableArrayList();

		try {
			String selectC = "SELECT * FROM Course WHERE courseName LIKE '%" + name + "%'";

			rs = con.createStatement().executeQuery(selectC);

			while(rs.next()) {
				oblistC.add(new Course(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			return oblistC;
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	public ObservableList<Student> selectAllFromHasStudied(String courseID) throws SQLException{
		con = dbc.getConnection();
		ObservableList<Student> oblistStudent = FXCollections.observableArrayList();
		
		try {
			String findHasStudied = "SELECT DISTINCT s1.studentID, s2.studentName FROM HasStudied s1 JOIN Student s2 ON s1.studentID = s2.studentID WHERE s1.courseID='" + courseID+ "'";
			rs = con.createStatement().executeQuery(findHasStudied); 

			while(rs.next()) {
				oblistStudent.add(new Student(rs.getString(1), rs.getString(2)));
			}
			return oblistStudent;
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	public ObservableList<Student> selectAllFromStudies(String courseID) throws SQLException{
		con = dbc.getConnection();
		ObservableList<Student> oblistStudent = FXCollections.observableArrayList();
		
		try {
			String findStudies = "SELECT DISTINCT s1.studentID, s2.studentName FROM Studies s1 JOIN Student s2 ON s1.studentID = s2.studentID WHERE s1.courseID ='" + courseID +"'"; 
			rs = con.createStatement().executeQuery(findStudies); 
			while(rs.next()) {
				oblistStudent.add(new Student(rs.getString(1), rs.getString(2)));
			}
			return oblistStudent;
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	public ObservableList<Course> selectAllActiveCourses() throws SQLException{
		con = dbc.getConnection();
		ObservableList<Course> oblistCourse = FXCollections.observableArrayList();
		
		try {
			String query1 = "SELECT DISTINCT courseID, courseName, credits FROM Course WHERE courseID NOT IN(SELECT hs.courseID FROM HasStudied hs)";
			rs = con.createStatement().executeQuery(query1);

			while(rs.next()) {
				oblistCourse.add(new Course(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			return oblistCourse;
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	public ObservableList<Course> selectAllFinishedCourses() throws SQLException{
		con = dbc.getConnection();
		ObservableList<Course> oblistCourse = FXCollections.observableArrayList();
		
		try {
			String queryFinishedCourse = "SELECT DISTINCT hs.courseID, c.courseName, c.credits FROM HasStudied hs JOIN Course c ON hs.courseID = c.courseID";

			rs = con.createStatement().executeQuery(queryFinishedCourse); 

			while(rs.next()) {
				oblistCourse.add(new Course(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			return oblistCourse;
		}
		catch(SQLException e) {
			throw e;
		}
	}	
	
	public ObservableList<HasStudied> selectAllFromGrade(String courseID) throws SQLException{
		con = dbc.getConnection();
		ObservableList<HasStudied> oblistGrade = FXCollections.observableArrayList();

		try {
			String queryGrade = "SELECT hs.grade FROM HasStudied hs JOIN Student s ON s.studentID = hs.studentID WHERE hs.courseID ='"+courseID +"'";		
			ResultSet rs = con.createStatement().executeQuery(queryGrade); 	
			while(rs.next()) {
				HasStudied tmpHasStudied = new HasStudied(rs.getString(1));
				oblistGrade.add(tmpHasStudied);
			}
			return oblistGrade;
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	public void insertCourse(String cCode, String cName, String cCredit) throws SQLException {
		con = dbc.getConnection();
		String insert = "INSERT INTO Course VALUES('"+ cCode + "','"  + cName + "','" + cCredit + "')";

		ps = con.prepareStatement(insert);
		ps.executeUpdate();
		con.close();
	}
	
	public void insertStudentToCourse(String studentID, String courseID) throws SQLException {
		con = dbc.getConnection();
		String insert = "INSERT INTO Studies VALUES('"+ studentID+"','"+ courseID+"')";
		
		ps = con.prepareStatement(insert);
		ps.executeUpdate();
		con.close();
	}

	public void removeCourse(String courseID) throws SQLException {
		con = dbc.getConnection();
		String queryRemove = "DELETE HasStudied WHERE courseID ='"+courseID+"'\n"
				+ "DELETE Studies WHERE courseID ='"+courseID+"'\n"
				+ "DELETE Course WHERE courseID ='"+courseID+"'";
		
		ps = con.prepareStatement(queryRemove);
		ps.executeUpdate();
		con.close();
	}

	public void moveCourse(Course course, ObservableList<Student> studentOblist) throws SQLException {
		con =dbc.getConnection();
		String tmpCourseID = course.getCourseCode();
		String tmpStudentID;
		for(Student s : studentOblist) {
			tmpStudentID = s.getStudentID();
			String queryMove = "DELETE Studies WHERE courseID ='"+tmpCourseID+"' INSERT INTO HasStudied VALUES('"+tmpStudentID+"','"+tmpCourseID+"',NULL)";
			ps = con.prepareStatement(queryMove);
			ps.executeUpdate();
		}
		con.close();
	}

	public void addGrade(Student student, Course course, String grade) throws SQLException {
		con = dbc.getConnection();
		String studentID = student.getStudentID();
		String courseID = course.getCourseCode();		
		String addGrade = "DELETE HasStudied WHERE studentID ='"+studentID+"'INSERT INTO HasStudied VALUES('"+studentID+"','"+courseID+"','"+grade+"')";
		
		ps = con.prepareStatement(addGrade);
		ps.executeUpdate();
		con.close();
	}
}
