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
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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

	@FXML private TableView<Course> tableActiveCourse;
	
	@FXML private TableView<Course> tableFinishedCourse;
	
	@FXML private TableView<Course> tableRegisterCourse;

	@FXML private TableView<HasStudied> tableGrade;
	
	@FXML private TableColumn<HasStudied, String> columnGrade;

	@FXML private TableColumn<Course, String> columnCourseCode;

	@FXML private TableColumn<Course, String> coulmnCourseName;

	@FXML private TableColumn<Course, String> columnCredit;

	@FXML private TableView<Student> tableActiveStudent;

	@FXML private TableView<Student> tableFinishedStudent;
	
	@FXML private TabPane tabPaneCourse;

	
	@FXML private TableColumn<Student, String> columnStudentID;

	@FXML private TableColumn<Student, String> columnStudentName;

	@FXML private Tab tabActiveCourse;
	
	@FXML private Tab tabFinishedCourse;
	
	@FXML private Tab tabRegistrationCourse;
	
	@FXML private Tab tabFindCourse;

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
	
	@FXML private TextField textFieldActiveCourseError;
	
	@FXML private TextField textFieldRegistrationError;
	
	@FXML private TextField textFieldFindError;
	
	@FXML private TextField textFieldFinishedCoursesError;
	
	
	//tabPaneCourse.getTabs().add(tabActiveCourse);	

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
		tabPaneCourse.getSelectionModel().select(tabActiveCourse);
	//	populateCmbBoxStudentID();
		//rdbtnActiveCourse.setSelected(true);
		
	}
	@FXML
	public void populateTableViewActiveCourse() {
		
		try {
			if(tabActiveCourse.isSelected()) {
			tableActiveCourse.setItems(dal.selectAllActiveCourses());
			}
			else {
				tableRegisterCourse.setItems(dal.selectAllActiveCourses());
			}
		}
		catch(SQLException e) {
			
			e.printStackTrace();

		}	
	}
	
	@FXML
	public void populateTableViewHasStudied() {
		Course tempC = tableFinishedCourse.getSelectionModel().getSelectedItem();	
		
		try {
			

			tableFinishedStudent.setItems(dal.selectAllFromHasStudied(tempC.getCourseCode()));
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null,e);

	}
	}
	@FXML
	public void populateTableViewStudentCourse() {
		try {
			if(tabActiveCourse.isSelected()) {
			Course tempC = tableActiveCourse.getSelectionModel().getSelectedItem();	
			tableActiveStudent.setItems(dal.selectAllFromStudies(tempC.getCourseCode()));
			}
			if(tabFinishedCourse.isSelected()) {
			Course tempC = tableFinishedCourse.getSelectionModel().getSelectedItem();	
			tableFinishedStudent.setItems(dal.selectAllFromHasStudied(tempC.getCourseCode()));
			}
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	@FXML
	private void populateTableViewGrade() {
		try {

			Course tempC = tableFinishedCourse.getSelectionModel().getSelectedItem();
			
			tableGrade.setItems(dal.selectAllFromGrade(tempC.getCourseCode()));
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	@FXML
	private void populateTableViewFinishedCourse() {
		try {	
			tableFinishedCourse.setItems(dal.selectAllFinishedCourses());
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
/*
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
 */		


/*	public void tabSelectionCourse(ActionEvent event) {
		
		if(tabActiveCourse.isSelected()) {
			tableActiveCourse.getItems().clear();
			tableActiveStudent.getItems().clear();
			cmbStudentID.getItems().clear();
			populateTableViewActiveCourse();
			populateTableViewStudentCourse();
			populateCmbBoxStudentID();
		}
		else if(tabFinishedCourse.isSelected()) {
			tableFinishedCourse.getItems().clear();
			tableFinishedStudent.getItems().clear();
			populateTableViewFinishedCourse();
			populateTableViewHasStudied();
		}
	}
*/
	public void btnRemoveCourse_Click(ActionEvent event){

		try {
			Course tempC = tableActiveCourse.getSelectionModel().getSelectedItem();
			dal.removeCourse(tempC.getCourseCode());
			tableActiveCourse.getItems().clear();
			lblResponseCourse.setText("Course: "+tempC.getName()+ " removed.");
		if(tabActiveCourse.isSelected()) {
			populateTableViewActiveCourse();
			populateCmbBoxStudentID();
		}
		else if(tabFinishedCourse.isSelected()) {	
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
				textFieldRegistrationError.setText("Course: "+cName+" added.");
				tableRegisterCourse.getItems().clear();
				textCourseCode.clear();
				textCourseName.clear();
				textCredit.clear();
				populateTableViewActiveCourse();
			}
			 
				catch (SQLException SQLException) {		
				
			}
		}
		else {
			textFieldRegistrationError.setText("Please fill out the fields.");
		}
	}
	
	@FXML
	public void selectCourse(MouseEvent event) {
		//if(tableActiveStudent.getItems() != null) {
			
			
		tableActiveStudent.getItems().clear();
		btnEditCourse.setDisable(false);
		btnRemoveCourse.setDisable(false);
		btnAddPartisipant.setDisable(false);
		cmbStudentID.setDisable(false);
		tableActiveStudent.setDisable(false);
		populateTableViewStudentCourse();
		}
		/*else {
			System.out.println("FEEEL");
		
		
	/*	lblResponseCourse.setText("Course selected");
		if(rdbtnActiveCourse.isSelected()) {
			tableGrade.setDisable(true);
			cmbStudentID.setDisable(false);
			btnAddPartisipant.setDisable(false);
		}
			else if(rdbtnFinishedCourse.isSelected()) {
				tableGrade.setDisable(false);
				tableGrade.getItems().clear();
				populateTableViewGrade();			
				populateTableViewHasStudied();	
				
				
			}
			else {
				lblResponseCourse.setText("ERROR");
			}*/
		
	
	@FXML
	public void btnAddStudentStudy_Click(ActionEvent event) {
		String sID = cmbStudentID.getValue();
		String cID = tableActiveCourse.getSelectionModel().getSelectedItem().getCourseCode();
		
			try {
				dal.insertStudentToCourse(sID, cID);
				tableActiveStudent.getItems().clear();
				populateTableViewStudentCourse();
			} catch (SQLException e) {		
				e.printStackTrace();
			}
		
	}





	



	}


