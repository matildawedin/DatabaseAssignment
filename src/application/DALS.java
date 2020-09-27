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
	// get all courses in studies class from a selected student
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
	
	// get all courses in Hasstudied class from a selected student
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
	
	// get grade for selected HasStudied course
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
	
	public ObservableList<String> selectAllCourseCode() throws SQLException{

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
	
	

	
	
	// Ej prio men fungerar ej att ta bort objekt som lagts till i databas
	public void removeStudent(String studentID) throws SQLException {
		con = dbc.getConnection();
		String remove = "DELETE FROM Student WHERE studentID = '" + studentID + "'"; 
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
