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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller implements Initializable{
	
	private Course course;
	private Student student;
	private HasStudied hasStudied;
	private DbConnection dbcon; 
	private Connection con;
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
	@FXML private TabPane tabPaneCourse;
	
	@FXML private Tab tabActiveCourse;

	@FXML private Tab tabFinishedCourse;

	@FXML private Tab tabRegCourse;

	@FXML private Tab tabFindCourse;

	@FXML private TableView<Course> tableActiveCourse;

	@FXML private TableView<Course> tableFinishedCourse;

	@FXML private TableView<Course> tableRegCourse;

	@FXML private TableView<Course> tableFindCourse;

	@FXML private TableView<HasStudied> tableFinishedGrade;
	
	@FXML private TableView<Student> tableActiveStudent;

	@FXML private TableView<Student> tableFinishedStudent;

	@FXML private TableColumn<HasStudied, String> columnFinishedGrade;

	@FXML private TableColumn<Course, String> columnActiveCourseID;

	@FXML private TableColumn<Course, String> columnActiveCourseName;

	@FXML private TableColumn<Course, String> columnActiveCredit;

	@FXML private TableColumn<Course, String> columnRegCourseID;

	@FXML private TableColumn<Course, String> columnRegCourseName;

	@FXML private TableColumn<Course, String> columnRegCredit;

	@FXML private TableColumn<Course, String> columnFinishedCourseID;

	@FXML private TableColumn<Course, String> columnFinishedCourseName;

	@FXML private TableColumn<Course, String> columnFinishedCredit;

	@FXML private TableColumn<Course, String> columnFindCourseID;

	@FXML private TableColumn<Course, String> columnFindCourseName;

	@FXML private TableColumn<Course, String> columnFindCredit;

	@FXML private TableColumn<Student, String> columnActiveStudentID;

	@FXML private TableColumn<Student, String> columnActiveStudentName;

	@FXML private TableColumn<Student, String> columnFinishedStudentID;

	@FXML private TableColumn<Student, String> columnFinishedStudentName;

	@FXML private TextField textCourseID;

	@FXML private TextField textCourseName;

	@FXML private TextField textCredit;

	@FXML private TextField textFindCourse;
	
	@FXML private Button btnCourseView;

	@FXML private Button btnStudentView;

	@FXML private Button btnAddCourse;

	@FXML private Button btnMoveCourse;

	@FXML private Button btnRemoveActiveCourse;

	@FXML private Button btnRemoveFinishedCourse;

	@FXML private Button btnAddPartisipant;

	@FXML private Button btnFindCourse;

	@FXML private Button btnAddGrade;
	
	@FXML private ComboBox<String> cmbStudentID;

	@FXML private ComboBox<String> cmbCourseID;

	@FXML private ComboBox<String> cmbGrade;

	@FXML private Label	lblAnswercCourseReg;

	@FXML private Label lblAnswerFindCourse;
	
	@FXML private Label lblAddParticipantAnswer;

	@FXML private Label lblGradeStudentAnswer;
	
	@Override
	public void initialize(URL url, ResourceBundle resources) {

		// set columns in tableview
		columnActiveCourseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
		columnActiveCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnActiveCredit.setCellValueFactory(new PropertyValueFactory<>("credits"));

		columnRegCourseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
		columnRegCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnRegCredit.setCellValueFactory(new PropertyValueFactory<>("credits"));

		columnFinishedCourseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
		columnFinishedCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnFinishedCredit.setCellValueFactory(new PropertyValueFactory<>("credits"));

		columnFindCourseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
		columnFindCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnFindCredit.setCellValueFactory(new PropertyValueFactory<>("credits"));

		columnActiveStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		columnActiveStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));

		columnFinishedStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		columnFinishedStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnFinishedGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

		cmbGrade.getItems().add("A");
		cmbGrade.getItems().add("B");
		cmbGrade.getItems().add("C");
		cmbGrade.getItems().add("D");
		cmbGrade.getItems().add("E");
		cmbGrade.getItems().add("F");

		dbcon = new DbConnection();
		con = dbcon.getConnection();
		
		tabPaneCourse.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() { 
			@Override 
			public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
				if(newTab.equals (tabActiveCourse)) {            
					tableActiveStudent.getItems().clear();
					tableActiveStudent.setDisable(true);
					populateTableViewActiveCourse();
				}
				else if(newTab.equals(tabFinishedCourse)) {
					tableFinishedStudent.getItems().clear();
					tableFinishedGrade.getItems().clear();
					tableFinishedGrade.setDisable(true);
					cmbGrade.setDisable(true);
					btnAddGrade.setDisable(true);
					tableFinishedStudent.setDisable(true);
					populateTableViewFinishedCourse();
				}
				else if(newTab.equals(tabRegCourse)) {
					populateTableViewRegCourse();
				}
				else if(newTab.equals(tabFindCourse)) {
					populateCmbBoxCourseID();
				}
			}
		});

		textFindCourse.textProperty().addListener((observable) -> cmbCourseID.setDisable(true));
		cmbCourseID.valueProperty().addListener((observable) -> textFindCourse.setDisable(true));
		cmbCourseID.valueProperty().addListener((observable) -> btnFindCourse.setDisable(false));
		textFindCourse.textProperty().addListener((observable) -> btnFindCourse.setDisable(false));
		populateTableViewActiveCourse();
		populateCmbBoxStudentID();
		populateCmbBoxCourseID();
	}

	@FXML
	public void showStudentView(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Students.fxml"));
			Parent root = (Parent) loader.load();

			Scene ExamViewScene = new Scene(root);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

			window.setScene(ExamViewScene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void populateTableViewActiveCourse() {
		tableActiveCourse.getItems().clear();
		try {
			tableActiveCourse.setItems(dal.selectAllActiveCourses());	
		}
		catch(SQLException e) {
			e.printStackTrace();
		}	
	}
	@FXML
	public void populateTableViewRegCourse() {
		tableRegCourse.getItems().clear();
		try {
			tableRegCourse.setItems(dal.selectAllActiveCourses());
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
				tableActiveStudent.getItems().clear();
				tableActiveStudent.setItems(dal.selectAllFromStudies(tempC.getCourseID()));
			}
			if(tabFinishedCourse.isSelected()) {
				Course tempC = tableFinishedCourse.getSelectionModel().getSelectedItem();	
				tableFinishedStudent.getItems().clear();
				tableFinishedStudent.setItems(dal.selectAllFromHasStudied(tempC.getCourseID()));
			}
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	@FXML
	private void populateTableViewGrade() {
		tableFinishedGrade.getItems().clear();
		try {
			Course tempC = tableFinishedCourse.getSelectionModel().getSelectedItem();		
			tableFinishedGrade.setItems(dal.selectAllFromGrade(tempC.getCourseID()));
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	@FXML
	private void populateTableViewFinishedCourse() {
		tableFinishedCourse.getItems().clear();
		try {	
			tableFinishedCourse.setItems(dal.selectAllFinishedCourses());
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	@FXML
	private void populateCmbBoxStudentID() {	
			cmbStudentID.getItems().clear();
		try {
			cmbStudentID.getItems().addAll(dal.selectAllStudentID());
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	@FXML
	private void populateCmbBoxCourseID() {
		cmbCourseID.getItems().clear();
		try {
			cmbCourseID.getItems().addAll(dal.selectAllCourseID());
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	@FXML
	public void populateFindCourseTable(String c) {

		try {
			if(cmbCourseID.getValue() != null ) {
				tableFindCourse.setItems(dal.selectCourseByCode(c));
			}
			else if(textFindCourse.getText() != null) {
				tableFindCourse.setItems(dal.selectCoursebyName(c));	
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void btnRemoveCourse_Click(ActionEvent event){
		try {
			if(tabActiveCourse.isSelected()) {
				Course tempC = tableActiveCourse.getSelectionModel().getSelectedItem();
				dal.removeCourse(tempC.getCourseID());
				tableActiveStudent.getItems().clear();
				populateTableViewActiveCourse();
				populateCmbBoxStudentID();
			}
			if(tabFinishedCourse.isSelected()) {
				Course tempC = tableFinishedCourse.getSelectionModel().getSelectedItem();
				dal.removeCourse(tempC.getCourseID());
				tableFinishedStudent.getItems().clear();
				populateTableViewFinishedCourse();
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void btnAddCourse_Click(ActionEvent event) {
		String cCode = textCourseID.getText();
		String cName = textCourseName.getText();
		String cCredit = textCredit.getText(); 
		if (!cCode.isEmpty() && !cName.isEmpty() && !cCredit.isEmpty()) {	

			try {
				dal.insertCourse(cCode, cName, cCredit);
				lblAnswercCourseReg.setText("Course: "+cName+" added.");
				textCourseID.clear();
				textCourseName.clear();
				textCredit.clear();
				populateTableViewRegCourse();
				populateCmbBoxCourseID();
			} 
			catch (SQLException SQLException) {		
				if ( SQLException.getErrorCode() == 2627) {
					lblAnswercCourseReg.setText("That coursecode already exists");
				}
				else if (SQLException.getErrorCode() == 0) {
					lblAnswercCourseReg.setText("There was a problem conecting to the database, please check your interntet connection");
				}
			}
		}
		else {
			lblAnswercCourseReg.setText("Please fill out all the fields.");
		}
	}

	@FXML
	public void btnMoveCourse_Click(ActionEvent event) {
		Course tmpCourse = tableActiveCourse.getSelectionModel().getSelectedItem();
		ObservableList<Student> tmpOblist = tableActiveStudent.getItems();

		try {
			dal.moveCourse(tmpCourse, tmpOblist);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tableActiveStudent.getItems().clear();
		populateTableViewActiveCourse();
	}

	@FXML
	public void selectCourse(MouseEvent event) {		

		if(tabActiveCourse.isSelected()) {	
			btnRemoveActiveCourse.setDisable(false);
			btnMoveCourse.setDisable(false);
			tableActiveStudent.setDisable(false);
			cmbStudentID.setDisable(false);
			btnAddPartisipant.setDisable(false);
			populateTableViewStudentCourse();
		}
		else if(tabFinishedCourse.isSelected()) {	
			tableFinishedGrade.setDisable(false);
			tableFinishedStudent.setDisable(false);
			btnRemoveFinishedCourse.setDisable(false);
			populateTableViewGrade();	
			populateTableViewStudentCourse();
		}
	}
	@FXML
	public void selectStudent(MouseEvent event) {
		cmbGrade.setDisable(false);
		btnAddGrade.setDisable(false);
	}

	@FXML
	public void btnAddStudentStudy_Click(ActionEvent event) {
		String sID = cmbStudentID.getValue();
		String cID = tableActiveCourse.getSelectionModel().getSelectedItem().getCourseID();
		if (sID != null) {
		try {
			dal.insertStudentToCourse(sID, cID);
			populateTableViewStudentCourse();
		} catch (SQLException e) {		
			if (e.getErrorCode() == 2627) {
				lblAddParticipantAnswer.setText("The selected student is already part of the selected course");
			}
			else if (e.getErrorCode() == 0) {
				lblAddParticipantAnswer.setText("There was a problem conecting to the database, please check your interntet connection");
		}
		
			
		}
		
		}
		else {
			lblAddParticipantAnswer.setText("Please select a studentID");
		}
	}
	@FXML
	public void btnAddGrade_Click(ActionEvent event) {
		Course tmpCourse = tableFinishedCourse.getSelectionModel().getSelectedItem();
		Student tmpStudent = tableFinishedStudent.getSelectionModel().getSelectedItem();
		String tmpGrade = cmbGrade.getSelectionModel().getSelectedItem();
		if (tmpGrade !=null) {
		try {
			dal.addGrade(tmpStudent, tmpCourse, tmpGrade);
			lblGradeStudentAnswer.setText("Grade " +  tmpGrade + " inserted");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		populateTableViewGrade();
		}
		else {
			lblGradeStudentAnswer.setText("Please select a grade");
		}
			
	}
	@FXML
	public void findCourse(ActionEvent event)  {
		String cID = cmbCourseID.getValue();
		String name = textFindCourse.getText();

		if(cmbCourseID.getValue() != null ) {
			populateFindCourseTable(cID);
		}
		else if(textFindCourse.getText() != null) {
			populateFindCourseTable(name);
		}

		tableFindCourse.setDisable(false);
		textFindCourse.clear();
		populateCmbBoxCourseID();
		cmbCourseID.setDisable(false);
		textFindCourse.setDisable(false);
		btnFindCourse.setDisable(true);	
	}

}



