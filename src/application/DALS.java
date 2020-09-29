package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALS {
	
	private DbConnection dbc = new DbConnection();
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	
	//--------------------------------Select methods-------------------------------------------
	
	// Receive an list of all the students in the database
	public ObservableList<Student> selectAllStudent() throws SQLException{
		
		con = dbc.getConnection();
		ObservableList<Student> oblistS = FXCollections.observableArrayList();
		
		try {
			
			String selectAll = "SELECT * FROM Student";
			rs = con.createStatement().executeQuery(selectAll);

			while(rs.next()) {
				oblistS.add(new Student(rs.getString(1), rs.getString(2)));
			}
			return oblistS;
		}
		catch(SQLException e) {
			throw e;
		}
		
	}
	// Receive all courses from Studies tabel based on a specific student id
	public ObservableList<Course> selectStudies(String studentID) throws SQLException{
		con = dbc.getConnection();
		ObservableList<Course> oblistC = FXCollections.observableArrayList();
		
		try {
			String selectC = "SELECT * FROM Course WHERE courseID IN (SELECT courseID FROM Studies WHERE studentID = '" + studentID + "')";
				
			rs = con.createStatement().executeQuery(selectC);

			while(rs.next()) {
				oblistC.add(new Course(rs.getString(1), rs.getString(2),rs.getString(3)));
			}
			return oblistC;
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	// Receive all courses from HasStudied tabel based on a specific student id
	public ObservableList<Course> selectHasStudied(String studentID) throws SQLException{
		con = dbc.getConnection();
		ObservableList<Course> oblistC = FXCollections.observableArrayList();
		
		try {
			String selectC = "SELECT * FROM Course WHERE courseID IN (SELECT courseID FROM HasStudied WHERE studentID = '" + studentID + "')";
				
			rs = con.createStatement().executeQuery(selectC);

			while(rs.next()) {
				oblistC.add(new Course(rs.getString(1), rs.getString(2),rs.getString(3)));
			}
			return oblistC;
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
			String selectG = "SELECT grade FROM HasStudied WHERE studentID = '" + studentID + "'";
			rs = con.createStatement().executeQuery(selectG);
			while(rs.next()) {
				oblistHs.add(new HasStudied(rs.getString(1)));
			}
			return oblistHs;
			
		}
		catch(SQLException e) {
			throw e;
		}
		
	}
	//Receive an list of all courseCodes from Course table
	public ObservableList<String> selectAllCourseID() throws SQLException{

		con = dbc.getConnection();
		ObservableList<String> oblistCC = FXCollections.observableArrayList();

		try {

			String selectCC = "SELECT courseID FROM Course";
			rs = con.createStatement().executeQuery(selectCC);

			while(rs.next()) {
				oblistCC.add(new String(rs.getString(1)));
			}
			return oblistCC;
		}
		catch(SQLException e) {
			throw e;
		}

	}
	//Receive an list of all studentIDs from Student table
	public ObservableList<String> selectAllStudentID() throws SQLException{
		con = dbc.getConnection();
		ObservableList<String> oblistSID = FXCollections.observableArrayList();
		
		try {
		String selectSID = "SELECT studentID FROM Student";
		rs = con.createStatement().executeQuery(selectSID);
		
		while(rs.next()) {
			oblistSID.add(new String(rs.getString(1)));
			
		}
		return oblistSID;
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	//Receive an list of Students from Student table based on a specific student id
	public ObservableList<Student> selectStudentbyID(String studentID) throws SQLException{
		con = dbc.getConnection();
		ObservableList<Student> oblistS = FXCollections.observableArrayList();
		
		try {
			String selectS = "SELECT * FROM Student WHERE studentID = '" + studentID + "'";
				
			rs = con.createStatement().executeQuery(selectS);

			while(rs.next()) {
				oblistS.add(new Student(rs.getString(1), rs.getString(2)));
			}
			return oblistS;
		}
		catch(SQLException e) {
			throw e;
		}
		
	}
	
	//Receive an list of Students from Student table based on a specific student name
	public ObservableList<Student> selectStudentbyName(String name) throws SQLException{
		con = dbc.getConnection();
		ObservableList<Student> oblistS = FXCollections.observableArrayList();
		
		try {
			String selectS = "SELECT * FROM Student WHERE studentName LIKE '%" + name + "%'";
				
			rs = con.createStatement().executeQuery(selectS);

			while(rs.next()) {
				oblistS.add(new Student(rs.getString(1), rs.getString(2)));
			}
			return oblistS;
		}
		catch(SQLException e) {
			throw e;
		}
		
	}
	
	//--------------------------------Insert methods-------------------------------------------
	
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
	
	public void insertCourseToStudent(String studentID, String courseID) throws SQLException {
		con = dbc.getConnection();

		String insert = "INSERT INTO Studies VALUES(?,?)";
		ps = con.prepareStatement(insert);
		ps.setString(1,studentID);
		ps.setString(2,courseID);
		ps.executeUpdate();
		con.close();
		

	}
	
	//--------------------------------Remove method-------------------------------------------

	public void removeStudent(String studentID) throws SQLException {
		con = dbc.getConnection();
		String remove = "DELETE FROM Studies WHERE studentID = '" + studentID + "'\n"
				+ "DELETE FROM HasStudied WHERE studentID = '" + studentID + "'\n"
				+ "DELETE FROM Student WHERE studentID = '" + studentID + "'\n";
		
		ps = con.prepareStatement(remove);	
		ps.executeUpdate();
		con.close();
	}
	
	
// Generate StudentID
	public String generateStudentId() throws SQLException {
		con = dbc.getConnection();
		String newID = null;

		try {
			
			String generate = "SELECT TOP 1 studentID  FROM Student ORDER BY studentID DESC";
			rs = con.createStatement().executeQuery(generate);
			
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
