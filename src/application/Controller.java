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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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

	@FXML private TableView<Course> tableFindCourse;

	@FXML private TableView<HasStudied> tableGrade;

	@FXML private TableColumn<HasStudied, String> columnGrade;

	@FXML private TableColumn<Course, String> columnCourseCode;

	@FXML private TableColumn<Course, String> columnCourseName;

	@FXML private TableColumn<Course, String> columnCredit;

	@FXML private TableColumn<Course, String> columnCourseCodeR;

	@FXML private TableColumn<Course, String> columnCourseNameR;

	@FXML private TableColumn<Course, String> columnCreditR;

	@FXML private TableColumn<Course, String> columnCourseCodeF;

	@FXML private TableColumn<Course, String> columnCourseNameF;

	@FXML private TableColumn<Course, String> columnCreditF;

	@FXML private TableColumn<Course, String> columnFindCourseCode;

	@FXML private TableColumn<Course, String> columnFindCourseName;

	@FXML private TableColumn<Course, String> columnFindCredit;



	@FXML private TableView<Student> tableActiveStudent;

	@FXML private TableView<Student> tableFinishedStudent;

	@FXML private TabPane tabPaneCourse;


	@FXML private TableColumn<Student, String> columnStudentID;

	@FXML private TableColumn<Student, String> columnStudentName;

	@FXML private TableColumn<Student, String> columnStudentIDF;

	@FXML private TableColumn<Student, String> columnStudentNameF;

	@FXML private Tab tabActiveCourse;

	@FXML private Tab tabFinishedCourse;

	@FXML private Tab tabRegistrationCourse;

	@FXML private Tab tabFindCourse;

	@FXML private TextField textCourseCode;

	@FXML private TextField textCourseName;

	@FXML private TextField textCredit;

	@FXML private Button btnAddCourse;

	@FXML private Button btnMoveCourse;

	@FXML private Button btnRemoveCourse;

	@FXML private Button btnRemoveCourseF;

	@FXML private Button btnAddPartisipant;

	@FXML private ComboBox<String> cmbStudentID;

	@FXML private ComboBox<String> cmbCourseCode;

	@FXML private Label lblResponseCourse;

	@FXML private TextField textAddGrade;

	@FXML private Button btnAddGrade;

	//tabPaneCourse.getTabs().add(tabActiveCourse);	

	@Override
	public void initialize(URL url, ResourceBundle resources) {

		// set columns in tableview
		columnCourseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
		columnCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnCredit.setCellValueFactory(new PropertyValueFactory<>("credits"));

		columnCourseCodeR.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
		columnCourseNameR.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnCreditR.setCellValueFactory(new PropertyValueFactory<>("credits"));

		columnCourseCodeF.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
		columnCourseNameF.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnCreditF.setCellValueFactory(new PropertyValueFactory<>("credits"));

		columnStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		columnStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

		columnStudentIDF.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		columnStudentNameF.setCellValueFactory(new PropertyValueFactory<>("name"));

		dbcon = new DbConnection();
		con = dbcon.getConnection();
		tabPaneCourse.getSelectionModel().select(tabActiveCourse);
		tabPaneCourse.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() { 
			@Override 
			public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
				if(newTab.equals (tabActiveCourse)) {            
					tableActiveCourse.getItems().clear();
					tableActiveStudent.getItems().clear();
					tableActiveStudent.setDisable(true);
					cmbStudentID.getItems().clear();
					populateTableViewActiveCourse();
					populateCmbBoxStudentID();
				}
				else if(newTab.equals(tabFinishedCourse)) {
					tableFinishedCourse.getItems().clear();
					tableFinishedStudent.getItems().clear();
					tableFinishedStudent.setDisable(true);
					tableGrade.setDisable(true);
					tableGrade.getItems().clear();
					populateTableViewFinishedCourse();
				}
				else if(newTab.equals(tabRegistrationCourse)) {
					tableRegisterCourse.getItems().clear();
				}
				else if(newTab.equals(tabFindCourse)) {
					cmbCourseCode.getItems().clear();
					populateCmbBoxCourseCode();
				}
			}
		});
		populateTableViewActiveCourse();
		populateCmbBoxStudentID();
		
	}
	@FXML
	public void populateTableViewActiveCourse() {

		try {
				tableActiveCourse.setItems(dal.selectAllActiveCourses());
			
		}
		catch(SQLException e) {

			e.printStackTrace();

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
	private void populateCmbBoxCourseCode() {
		try {
			cmbCourseCode.getItems().addAll(dal.selectAllStudentID());
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}

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
				lblResponseCourse.setText("Course: "+cName+" added.");
				tableRegisterCourse.getItems().clear();
				tableActiveCourse.getItems().clear();
				textCourseCode.clear();
				textCourseName.clear();
				textCredit.clear();
				populateTableViewActiveCourse();
				//populateRegisterCourse();

			} catch (SQLException e) {		
				e.printStackTrace();
			}
		}
		else {
			lblResponseCourse.setText("Please fill out the fields.");
		}
	}
	public void btnMoveCourse_Click(ActionEvent event) {
		Course tmpCourse = tableActiveCourse.getSelectionModel().getSelectedItem();
		ObservableList<Student> tmpOblist = tableActiveStudent.getItems();

		try {
			dal.moveCourse(tmpCourse, tmpOblist);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tableActiveCourse.getItems().clear();
		tableActiveStudent.getItems().clear();
		populateTableViewActiveCourse();

	}
	@FXML
	public void selectCourse(MouseEvent event) {		
		tableActiveStudent.getItems().clear();	
		tableFinishedStudent.getItems().clear();	
		btnRemoveCourse.setDisable(false);
		lblResponseCourse.setText("Course selected");

		if(tabActiveCourse.isSelected()) {	
			btnMoveCourse.setDisable(false);
			tableActiveStudent.setDisable(false);
			cmbStudentID.setDisable(false);
			btnAddPartisipant.setDisable(false);
			btnRemoveCourse.setDisable(false);
			populateTableViewStudentCourse();

		}
		else if(tabFinishedCourse.isSelected()) {	
			tableGrade.setDisable(false);
			tableGrade.getItems().clear();
			tableFinishedStudent.setDisable(false);
			btnRemoveCourseF.setDisable(false);
			populateTableViewGrade();	
			populateTableViewStudentCourse();
		}
		else {
			lblResponseCourse.setText("ERROR");
		}
	}


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


