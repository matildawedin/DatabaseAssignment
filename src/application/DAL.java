package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAL {

	//Declare new objects 
	private DbConnection dbc = new DbConnection();
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	//--------------------------------Select all-methods-------------------------------------------\\

	//Receive a list of all courses that are not present in the HasStudied table.
	public ObservableList<Course> selectAllActiveCourses() throws SQLException{
		
		con = dbc.getConnection();
		ObservableList<Course> oblistCourse = FXCollections.observableArrayList();

		try {
			String queryActiveCourse = "SELECT DISTINCT courseID, courseName, credits FROM Course WHERE courseID NOT IN(SELECT hs.courseID FROM HasStudied hs)";
			rs = con.createStatement().executeQuery(queryActiveCourse);

			while(rs.next()) {
				oblistCourse.add(new Course(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			return oblistCourse;
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	//Receive a list of all courses that are present in the HasStudied table.
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
	
	// Receive an list of all the students in the database.
	public ObservableList<Student> selectAllStudent() throws SQLException{

		con = dbc.getConnection();
		ObservableList<Student> oblistStudent = FXCollections.observableArrayList();

		try {

			String querySelectAllStudent = "SELECT * FROM Student";
			rs = con.createStatement().executeQuery(querySelectAllStudent);

			while(rs.next()) {
				oblistStudent.add(new Student(rs.getString(1), rs.getString(2)));
			}
			return oblistStudent;
		}
		catch(SQLException e) {
			throw e;
		}
	}

	//--------------------------------Populate comboboxes methods-------------------------------------------\\

	//Receive a list of all studentIDs from the Student table
	public ObservableList<String> selectAllStudentID() throws SQLException{ 
		con = dbc.getConnection();
		ObservableList<String> oblistString = FXCollections.observableArrayList();
		try {
			String querySelectAllStudentID = "SELECT studentID FROM Student";
			rs = con.createStatement().executeQuery(querySelectAllStudentID);

			while(rs.next()) {
				oblistString.add(new String(rs.getString(1)));
			}
			return oblistString;
		}
		catch(SQLException e) {
			throw e;
		}
	}

	//Receive a list of all courseCodes from the Course table
	public ObservableList<String> selectAllCourseID() throws SQLException{ 

		con = dbc.getConnection();
		ObservableList<String> oblistString = FXCollections.observableArrayList();

		try {

			String querySelectAllCourseID = "SELECT courseID FROM Course";
			rs = con.createStatement().executeQuery(querySelectAllCourseID);

			while(rs.next()) {
				oblistString.add(new String(rs.getString(1)));
			}
			return oblistString;
		}
		catch(SQLException e) {
			throw e;
		}

	}
	//--------------------------------Specific select methods-------------------------------------------\\

	//Receive an list of Courses from Course table based on a specific course id
	public ObservableList<Course> selectCourseByID(String courseID) throws SQLException{
		con = dbc.getConnection();
		ObservableList<Course> oblistCourse = FXCollections.observableArrayList();

		try{
			String queryFindCourse = "SELECT * FROM Course WHERE courseID = '" + courseID + "'";
			rs = con.createStatement().executeQuery(queryFindCourse);

			while(rs.next()) {
				oblistCourse.add(new Course(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			return oblistCourse;
		}
		catch(SQLException e) {
			throw e;
		}
	}
	//Receive an list of Students from Student table based on a specific student id
	public ObservableList<Student> selectStudentbyID(String studentID) throws SQLException{
		con = dbc.getConnection();
		ObservableList<Student> oblistStudent = FXCollections.observableArrayList();

		try {
			String queryFindStudent = "SELECT * FROM Student WHERE studentID = '" + studentID + "'";

			rs = con.createStatement().executeQuery(queryFindStudent);

			while(rs.next()) {
				oblistStudent.add(new Student(rs.getString(1), rs.getString(2)));
			}
			return oblistStudent;
		}
		catch(SQLException e) {
			throw e;
		}

	}
	//Receive a list of all students belonging to a certain finished course. 
	public ObservableList<Student> selectAllFromHasStudied(String courseID) throws SQLException{
		con = dbc.getConnection();
		ObservableList<Student> oblistStudent = FXCollections.observableArrayList();

		try {
			String queryFindHasStudied = "SELECT DISTINCT s1.studentID, s2.studentName FROM HasStudied s1 JOIN Student s2 ON s1.studentID = s2.studentID WHERE s1.courseID='" + courseID+ "'";
			rs = con.createStatement().executeQuery(queryFindHasStudied); 

			while(rs.next()) {
				oblistStudent.add(new Student(rs.getString(1), rs.getString(2)));
			}
			return oblistStudent;
		}
		catch(SQLException e) {
			throw e;
		}
	}
	//Receive a list of all students studying a certain active course. 
	public ObservableList<Student> selectAllFromStudies(String courseID) throws SQLException{
		con = dbc.getConnection();
		ObservableList<Student> oblistStudent = FXCollections.observableArrayList();

		try {
			String queryFindStudies = "SELECT DISTINCT s1.studentID, s2.studentName FROM Studies s1 JOIN Student s2 ON s1.studentID = s2.studentID WHERE s1.courseID ='" + courseID +"'"; 
			rs = con.createStatement().executeQuery(queryFindStudies); 
			while(rs.next()) {
				oblistStudent.add(new Student(rs.getString(1), rs.getString(2)));
			}
			return oblistStudent;
		}
		catch(SQLException e) {
			throw e;
		}
	}

	//Receive a list of Courses from Course table based on a specific course name
	public ObservableList<Course> selectCoursebyName(String name) throws SQLException{ 
		con = dbc.getConnection();
		ObservableList<Course> oblistCourse = FXCollections.observableArrayList();

		try {
			String querySelectCourse = "SELECT * FROM Course WHERE courseName LIKE '%" + name + "%'";

			rs = con.createStatement().executeQuery(querySelectCourse);

			while(rs.next()) {
				oblistCourse.add(new Course(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			return oblistCourse;
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	//Receive a list of students who's name contain characters matching the in-value. 
	public ObservableList<Student> selectStudentbyName(String name) throws SQLException{ 
		con = dbc.getConnection();
		ObservableList<Student> oblistStudent = FXCollections.observableArrayList();

		try {
			String querySelectStudent = "SELECT * FROM Student WHERE studentName LIKE '%" + name + "%'";

			rs = con.createStatement().executeQuery(querySelectStudent);

			while(rs.next()) {
				oblistStudent.add(new Student(rs.getString(1), rs.getString(2)));
			}
			return oblistStudent;
		}
		catch(SQLException e) {
			throw e;
		}

	}

	// Receive list of all courses from Studies table based on a specific student id
	public ObservableList<Course> selectStudies(String studentID) throws SQLException{ 
		con = dbc.getConnection();
		ObservableList<Course> oblistCourse = FXCollections.observableArrayList();

		try {
			String querySelectCourse = "SELECT * FROM Course WHERE courseID IN (SELECT courseID FROM Studies WHERE studentID = '" + studentID + "')";

			rs = con.createStatement().executeQuery(querySelectCourse);

			while(rs.next()) {
				oblistCourse.add(new Course(rs.getString(1), rs.getString(2),rs.getString(3)));
			}
			return oblistCourse;
		}
		catch(SQLException e) {
			throw e;
		}
	}
	// Receive list of all courses from HasStudied tabel based on a specific student id
	public ObservableList<Course> selectHasStudied(String studentID) throws SQLException{ 
		con = dbc.getConnection();
		ObservableList<Course> oblistCourse = FXCollections.observableArrayList();

		try {
			String querySelectCourse = "SELECT * FROM Course WHERE courseID IN (SELECT courseID FROM HasStudied WHERE studentID = '" + studentID + "')";

			rs = con.createStatement().executeQuery(querySelectCourse);

			while(rs.next()) {
				oblistCourse.add(new Course(rs.getString(1), rs.getString(2),rs.getString(3)));
			}
			return oblistCourse;
		}
		catch(SQLException e) {
			throw e;
		}

	}
	//Receive a list of all grades registered on a finished course. 
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

	// Receive an list of grade from HasStudied tabel based on a specific student id
	public ObservableList<HasStudied> selectGrade(String studentID) throws SQLException{ 
		con = dbc.getConnection();
		ObservableList<HasStudied> oblistHs = FXCollections.observableArrayList();

		try {
			String querySelectGrade = "SELECT grade FROM HasStudied WHERE studentID = '" + studentID + "'";
			rs = con.createStatement().executeQuery(querySelectGrade);
			while(rs.next()) {
				oblistHs.add(new HasStudied(rs.getString(1)));
			}
			return oblistHs;

		}
		catch(SQLException e) {
			throw e;
		}

	}
	//--------------------------------Insert methods-------------------------------------------

	//create new student with the values presented.
	public void insertStudent(String studentID, String name) throws SQLException {
		con = dbc.getConnection();

		String queryCreateStudent = "INSERT INTO Student VALUES(?,?)";

		ps = con.prepareStatement(queryCreateStudent);
		ps.setString(1,studentID);
		ps.setString(2,name);
		ps.executeUpdate();
		con.close();

	}
	
	//create new course with the values presented.
	public void insertCourse(String cCode, String cName, String cCredit) throws SQLException {
		con = dbc.getConnection();
		String queryCreateCourse = "INSERT INTO Course VALUES('"+ cCode + "','"  + cName + "','" + cCredit + "')";

		ps = con.prepareStatement(queryCreateCourse);
		ps.executeUpdate();
		con.close();
	}
	
	//insert student to a course by adding them to the Studies table. 
	public void insertStudentToCourse(String studentID, String courseID) throws SQLException {
		con = dbc.getConnection();
		String queryInsert = "INSERT INTO Studies VALUES('"+ studentID+"','"+ courseID+"')";

		ps = con.prepareStatement(queryInsert);
		ps.executeUpdate();
		con.close();
	}
	
	//--------------------------------Remove method-------------------------------------------


	//Removing course from the Course table.
	public void removeCourse(String courseID) throws SQLException {
		con = dbc.getConnection();
		String queryRemove = "DELETE HasStudied WHERE courseID ='"+courseID+"'\n"
				+ "DELETE Studies WHERE courseID ='"+courseID+"'\n"
				+ "DELETE Course WHERE courseID ='"+courseID+"'";

		ps = con.prepareStatement(queryRemove);
		ps.executeUpdate();
		con.close();
	}
	//Removing student from the Student table. 
	public void removeStudent(String studentID) throws SQLException {
		con = dbc.getConnection();
		String queryRemove = "DELETE FROM Studies WHERE studentID = '" + studentID + "'\n"
				+ "DELETE FROM HasStudied WHERE studentID = '" + studentID + "'\n"
				+ "DELETE FROM Student WHERE studentID = '" + studentID + "'\n";

		ps = con.prepareStatement(queryRemove);	
		ps.executeUpdate();
		con.close();
	}


	//--------------------------------Alter methods-------------------------------------------\\

	//Change the status of a certain course to "Finished". 
	public void moveCourse(Course course, ObservableList<Student> studentOblist) throws SQLException {
		con = dbc.getConnection();
		String tmpCourseID = course.getCourseID();
		String tmpStudentID;
		for(Student s : studentOblist) {
			tmpStudentID = s.getStudentID();
			String queryMove = "DELETE Studies WHERE courseID ='"+tmpCourseID+"' INSERT INTO HasStudied VALUES('"+tmpStudentID+"','"+tmpCourseID+"',NULL)";
			ps = con.prepareStatement(queryMove);
			ps.executeUpdate();
		}
		con.close();
	}

	//Adding a grade to the selected student. 
	public void addGrade(Student student, Course course, String grade) throws SQLException {
		con = dbc.getConnection();
		String studentID = student.getStudentID();
		String courseID = course.getCourseID();		
		String queryAddGrade = "DELETE HasStudied WHERE studentID ='"+studentID+"'INSERT INTO HasStudied VALUES('"+studentID+"','"+courseID+"','"+grade+"')";

		ps = con.prepareStatement(queryAddGrade);
		ps.executeUpdate();
		con.close();
	}

	//--------------------------------Generate methods-------------------------------------------\\

	// Generate CourseID
	public String generateCourseId() throws SQLException {
		con = dbc.getConnection();
		String newID = null;

		try {
			String queryGenerate = "SELECT TOP 1 courseID  FROM Course ORDER BY courseID DESC";
			rs = con.createStatement().executeQuery(queryGenerate);

			while(rs.next()) {
				String s = rs.getString(1);

				if( s.length() == 5) {
					StringBuilder sb = new StringBuilder();
					sb.append(s.charAt(1));
					sb.append(s.charAt(2));
					sb.append(s.charAt(3));
					sb.append(s.charAt(4));

					String charString = sb.toString();
					int number = Integer.parseInt(charString);

					if(s != null) {
						number++;
					}
					String newString = Integer.toString(number);
					StringBuilder newSb = new StringBuilder();
					newSb.append(s.charAt(0));
					newSb.append(newString);
					newID = newSb.toString();
				}
			}
			return  newID;

		}catch(SQLException e) {
			throw e;
		}
	}
	// Generate StudentID
	public String generateStudentId() throws SQLException {
		con = dbc.getConnection();
		String newID = null;

		try {

			String queryGenerate = "SELECT TOP 1 studentID  FROM Student ORDER BY studentID DESC";
			rs = con.createStatement().executeQuery(queryGenerate);

			while(rs.next()) {
				String s = rs.getString(1);

				if( s.length() == 5) {
					StringBuilder sb = new StringBuilder();
					sb.append(s.charAt(1));
					sb.append(s.charAt(2));
					sb.append(s.charAt(3));
					sb.append(s.charAt(4));

					String charString = sb.toString();
					int number = Integer.parseInt(charString);

					if(s != null) {
						number++;
					}
					String newString = Integer.toString(number);
					StringBuilder newSb = new StringBuilder();
					newSb.append(s.charAt(0));
					newSb.append(newString);
					newID = newSb.toString();
				}

			}
			return  newID;

		}catch(SQLException e) {
			throw e;
		}
	}
}
