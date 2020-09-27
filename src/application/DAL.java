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
	private ObservableList<Course> oblistCourse = FXCollections.observableArrayList();
	private ObservableList<Student> oblistStudent = FXCollections.observableArrayList();
	private ObservableList<String> oblistString = FXCollections.observableArrayList();
	private ObservableList<HasStudied> oblistGrade = FXCollections.observableArrayList();

	public ObservableList<Student> selectAllStudent() throws SQLException{

		con = dbc.getConnection();

		try {

			String selectAll = "SELECT * FROM Student";
			rs = con.createStatement().executeQuery(selectAll);

			while(rs.next()) {
				oblistStudent.add(new Student(rs.getString(1), rs.getString(2)));
			}
			return oblistStudent;
		}
		catch(SQLException e) {
			throw e;
		}

	}
	public ObservableList<String> selectAllStudentID() throws SQLException{

		con = dbc.getConnection();
		
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
	public ObservableList<Course> selectAllActiveCourses() throws SQLException{

		con = dbc.getConnection();

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

		try {
			String queryFinishedCourse = "SELECT DISTINCT courseID, courseName, credits FROM Course WHERE courseID NOT IN(SELECT hs.courseID FROM Studies hs)";

			rs = con.createStatement().executeQuery(queryFinishedCourse); 

			while(rs.next()) {
				oblistCourse.add(new Course(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			return oblistCourse;
		}
		catch(SQLException e) {
			throw e;
		}

	}	public ObservableList<Student> selectAllFromHasStudied(String courseID) throws SQLException{

		con = dbc.getConnection();

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
	public ObservableList<HasStudied> selectAllFromGrade(String courseID) throws SQLException{

		con = dbc.getConnection();

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
	public void removeCourse(String courseID) throws SQLException {
		con = dbc.getConnection();
		String queryRemove = "DELETE HasStudied WHERE courseID ='"+courseID+"'\n"
				+ "DELETE Studies WHERE courseID ='"+courseID+"'\n"
				+ "DELETE Course WHERE courseID ='"+courseID+"'";

		//String queryRemove = "DELETE Course WHERE courseID = '" + courseID +"'";
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
	public void insertStudentToCourse(String studentID, String courseID) throws SQLException {
		con = dbc.getConnection();

		String insert = "INSERT INTO Studies VALUES('"+ studentID+"','"+ courseID+"')";
		ps = con.prepareStatement(insert);
		//ps.setString(1,studentID);
		//	ps.setString(2,courseID);
		ps.executeUpdate();
		con.close();

	}
	public ResultSet findStudent(String studentID) {
		try{
			String findS = "SELECT * FROM Student WHERE studentID = "+ "'" + studentID + "'" + ")";

			ps = con.prepareStatement(findS);
			rs = ps.executeQuery();

			if(rs.next()) {
				return rs;
			}

		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public ResultSet findCourse(String courseCode) {


		try{
			String findC = "SELECT * FROM Course WHERE courseCode = "+ "'" + courseCode + "'" + ")";

			ps = con.prepareStatement(findC);
			rs = ps.executeQuery();

			if(rs.next()) {
				return rs;
			}

		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public ResultSet findStudentsFromStudies(String courseCode) {


		try{
			String findStudents = "SELECT studentID FROM Studies WHERE courseCode = "+ "'" + courseCode + "'" + ")";

			ps = con.prepareStatement(findStudents);
			rs = ps.executeQuery();

			if(rs.next()) {
				return rs;
			}

		}	catch(SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	// insert student values 
	public void insertStudent(String studentID, String name) throws SQLException {
		con = dbc.getConnection();

		String insert = "INSERT INTO Student VALUES(?,?)";

		ps = con.prepareStatement(insert);
		ps.setString(1,studentID);
		ps.setString(2,name);
		ps.executeUpdate();
		con.close();

	}

	public void removeStudent(String studentID) throws SQLException {
		con = dbc.getConnection();
		String remove = "DELETE FROM Student WHERE studentID = '" + studentID; //"'DELETE FROM Student WHERE studentID = ' ;
		ps = con.prepareStatement(remove);
		ps.executeUpdate();
		con.close();
	}


	/* EJ GJORT ÄN
	public void generateStudentId() {
		con = dbc.getConnection();

		String generate = "SELECT TOP 1 studentID  FROM Student ORDER BY studentID DESC";

	}
	 */


}
