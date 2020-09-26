package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StudentController implements Initializable {


	private Course course;
	private Student student;
	private Result result;
	private HasStudied hasStudied;

	private DbConnection dbcon; 
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private DAL dal = new DAL();
	

	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public HasStudied getHasStudied() {
		return hasStudied;
	}
	public void setHasStudied(HasStudied hasStudied) {
		this.hasStudied = hasStudied;
	}

	public DbConnection getDbcon() {
		return dbcon;
	}
	public void setDbcon(DbConnection dbcon) {
		this.dbcon = dbcon;
	}
	
	
	@FXML private Button btnCourseView;

	@FXML private Button btnStudentView;
	
	@FXML private TableView<Course> tableCourse;
	
	@FXML private TableColumn<Course, String> columnCourseCode;

	@FXML private TableColumn<Course, String> coulmnCourseName;

	@FXML private TableColumn<Course, String> columnCredit;
	
	@FXML private TableView<Result> tabelGrade;
	
	@FXML private TableColumn<Result,String> columnGrade;
	
	@FXML private TableView<Student> tableStudent;


	@FXML private TableColumn<Student, String> columnStudentID;

	@FXML private TableColumn<Student, String> columnStudentName;
	
	@FXML private Button btnAddStudent;
	
	@FXML private TextField textStudentID;
	
	@FXML private TextField textStudentName;
	
	@FXML private Label lblResponseStudent;
	
	@FXML private Button btnEditStudent;
	
	@FXML private Button btnRemoveStudent;
	
	
	private ObservableList<Course> oblistCourse = FXCollections.observableArrayList();
	private ObservableList<Student> oblistStudent = FXCollections.observableArrayList();
	private ObservableList<Result> oblistResult = FXCollections.observableArrayList();
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle resources) {
		// set columns in tableview
		columnCourseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
		coulmnCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnCredit.setCellValueFactory(new PropertyValueFactory<>("credits"));
		columnStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		columnStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

		
		
		dbcon = new DbConnection();
		con = dbcon.getConnection();
		
		populateStudents();
	}

	

    // add all the current student to the studentTable
	@FXML
	public void populateStudents() {
		
		try {
			tableStudent.setItems(dal.selectAllStudent());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	//FUNGERAR EJ
	/*
	public void populateCourse(String studentID) {
		
		try {
			//Student tempS = tableStudent.getSelectionModel().getSelectedItem();
			//String sID = tempS.getStudentID();
			
			tableCourse.setItems(dal.selectCourses(studentID));
			
		}
		catch(SQLException e) {
		e.printStackTrace();
		}
	}*/
	
	
	//get the value from the TextField then use insertstudent to add the student
	@FXML
	public void btnAddStudent(ActionEvent event) {
		
		System.out.println("inne i metod");

		if (textStudentID.getText() != null && textStudentName.getText() != null) {
			try {
				dal.insertStudent(textStudentID.getText(),textStudentName.getText());
				lblResponseStudent.setText("Course: "+(textStudentName.getText())+" added.");

			} catch (SQLException e) {		
				e.printStackTrace();
			}
			
			
		}
		else {
			lblResponseStudent.setText("Please fill out the fields.");
		}
		//vart ska detta vara?
		oblistStudent.clear();
		populateStudents();
		textStudentID.clear();
		textStudentName.clear();
	}
	
	@FXML
	public void selectStudent(MouseEvent event) {

		System.out.println("inne i select");

		Student s = tableStudent.getSelectionModel().getSelectedItem();
		if(s != null) {
			//String sID = s.getStudentID();

			lblResponseStudent.setText("Student selected");
			//populateCourse(sID);
		} else {
			lblResponseStudent.setText("ERROR");
		}
		//populateStudents();
		btnEditStudent.setDisable(false);
		btnRemoveStudent.setDisable(false);
		tableCourse.setDisable(false);
		oblistStudent.clear();
	}
	
	@FXML
	public void btnRemoveStudent(ActionEvent event) {
		System.out.println("inne i remove");

		try {
			Student tempS = tableStudent.getSelectionModel().getSelectedItem();
			String sID = tempS.getStudentID();

			dal.removeStudent(sID);

		}
		catch (SQLException e) {
		e.printStackTrace();
		}
		oblistCourse.clear();
		populateStudents();
	}
	
}


	
	
	
