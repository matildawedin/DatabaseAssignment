package application;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
public class Controller implements Initializable{


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

	// FXML Functions

	@FXML private Button btnCourseView;

	@FXML private Button btnStudentView;

	@FXML private TableView<Course> tableCourse;

	@FXML private TableView<HasStudied> tableGrade;
	
	@FXML private TableColumn<HasStudied, String> columnGrade;

	@FXML private TableColumn<Course, String> columnCourseCode;

	@FXML private TableColumn<Course, String> coulmnCourseName;

	@FXML private TableColumn<Course, String> columnCredit;

	@FXML private TableView<Student> tableStudent;


	@FXML private TableColumn<Student, String> columnStudentID;

	@FXML private TableColumn<Student, String> columnStudentName;

	

	@FXML private TextField textCourseCode;

	@FXML private TextField textCourseName;

	@FXML private TextField textCredit;

	@FXML private Button btnAddCourse;

	@FXML private Button btnEditCourse;

	@FXML private Button btnRemoveCourse;

	@FXML private Button btnAddPartisipant;

	@FXML private ComboBox<String> cmbStudentID;

	@FXML private Label lblResponseCourse;

	@FXML private TextField textAddGrade;

	@FXML private Button btnAddGrade;

	@FXML private RadioButton rdbtnActiveCourse;

	@FXML private RadioButton rdbtnFinishedCourse;


	private ObservableList<Course> oblistCourse = FXCollections.observableArrayList();
	private ObservableList<Student> oblistStudent = FXCollections.observableArrayList();
	private ObservableList<String> oblistcmbStudentID = FXCollections.observableArrayList();
	private ObservableList<HasStudied> oblistGrade = FXCollections.observableArrayList();




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
		populateTableViewActiveCourse();
		populateCmbBoxStudentID();
		rdbtnActiveCourse.setSelected(true);
	}
	@FXML
	public void populateTableViewActiveCourse() {
		
		try {
			tableCourse.setItems(dal.selectAllActiveCourses());
			
		}
		catch(SQLException e) {
			
			e.printStackTrace();

		}	
	}
	@FXML
	public void populateTableViewHasStudied() {
		Course tempC = tableCourse.getSelectionModel().getSelectedItem();	
		
		try {

			tableStudent.setItems(dal.selectAllFromHasStudied(tempC.getCourseCode()));
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null,e);

	}
	}
	@FXML
	public void populateTableViewStudies() {
		try {

			Course tempC = tableCourse.getSelectionModel().getSelectedItem();
			
			tableStudent.setItems(dal.selectAllFromStudies(tempC.getCourseCode()));
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	@FXML
	private void populateTableViewGrade() {
		try {

			Course tempC = tableCourse.getSelectionModel().getSelectedItem();
			
			tableGrade.setItems(dal.selectAllFromGrade(tempC.getCourseCode()));
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	@FXML
	private void populateTableViewFinishedCourse() {
		try {	
			tableCourse.setItems(dal.selectAllFinishedCourses());
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	@FXML
	private void populateCmbBoxStudentID() {
		try {
		cmbStudentID.getItems().addAll(dal.selectAllStudentID());
		}
		catch(SQLException e) {
		Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	@FXML
	public void radioButtonCourse(ActionEvent event) {
		if(rdbtnActiveCourse.isSelected()) {
			tableCourse.getItems().clear();
			tableStudent.getItems().clear();
			tableGrade.getItems().clear();
			tableGrade.setDisable(true);
			tableStudent.setDisable(true);
			populateTableViewActiveCourse();
			populateCmbBoxStudentID();
		}
		else if(rdbtnFinishedCourse.isSelected()) {
			tableCourse.getItems().clear();
			tableStudent.getItems().clear();
			tableGrade.setDisable(true);
			tableStudent.setDisable(true);
			populateTableViewFinishedCourse();
		}
	}


	public void btnRemoveCourse_Click(ActionEvent event){

		try {
			Course tempC = tableCourse.getSelectionModel().getSelectedItem();
			dal.removeCourse(tempC.getCourseCode());
			oblistCourse.clear();
			lblResponseCourse.setText("Course: "+tempC.getName()+ " removed.");
		if(rdbtnActiveCourse.isSelected()) {
			populateTableViewActiveCourse();
			populateCmbBoxStudentID();
		}
		else if(rdbtnFinishedCourse.isSelected()) {	
			populateTableViewFinishedCourse();
		}
		}
		catch (SQLException e) {
			e.printStackTrace();
			}
	}

	@FXML
	public void btnAddCourse_Click(ActionEvent event) {
		String cCode = textCourseCode.getText();
		String cName = textCourseName.getText();
		String cCredit = textCredit.getText(); 
		
		if (cCode !=null && cName !=null && cCredit !=null) {	
			try {
				dal.insertCourse(cCode, cName, cCredit);
				lblResponseCourse.setText("Course: "+cName+" added.");
				oblistCourse.clear();
				textCourseCode.clear();
				textCourseName.clear();
				textCredit.clear();
				if(rdbtnActiveCourse.isSelected()) {	
					populateTableViewActiveCourse();
				}
				else if(rdbtnFinishedCourse.isSelected()) {
					populateTableViewFinishedCourse();
				}
			} catch (SQLException e) {		
				e.printStackTrace();
			}
		}
		else {
			lblResponseCourse.setText("Please fill out the fields.");
		}
	}
	
	@FXML
	public void selectCourse(MouseEvent event) {
				
		oblistStudent.clear();
		btnEditCourse.setDisable(false);
		btnRemoveCourse.setDisable(false);
		tableStudent.setDisable(false);
		
		lblResponseCourse.setText("Course selected");
		if(rdbtnActiveCourse.isSelected()) {
			tableGrade.setDisable(true);
			populateTableViewStudies();
			cmbStudentID.setDisable(false);
			btnAddPartisipant.setDisable(false);
		}
			else if(rdbtnFinishedCourse.isSelected()) {
				tableGrade.setDisable(false);
				oblistGrade.clear();
				populateTableViewGrade();			
				populateTableViewHasStudied();	
				
				
			}
			else {
				lblResponseCourse.setText("ERROR");
			}
		}
	
	//HÅLLER PÅ
	@FXML
	public void btnAddStudentStudy_Click(ActionEvent event) {
		
		String sID = cmbStudentID.getValue();
		String cID = tableCourse.getSelectionModel().getSelectedItem().getCourseCode();
		
			try {
				dal.insertStudentToCourse(sID, cID);
				tableStudent.getItems().clear();
				populateTableViewStudies();
			} catch (SQLException e) {		
				e.printStackTrace();
			}
		
	}





	



	}


