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

	@FXML private Tab tabRegistrationCourse;

	@FXML private Tab tabFindCourse;

	@FXML private TableView<Course> tableActiveCourse;

	@FXML private TableView<Course> tableFinishedCourse;

	@FXML private TableView<Course> tableRegisterCourse;

	@FXML private TableView<Course> tableFindCourse;

	@FXML private TableView<HasStudied> tableGrade;
	
	@FXML private TableView<Student> tableActiveStudent;

	@FXML private TableView<Student> tableFinishedStudent;

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

	@FXML private TableColumn<Student, String> columnStudentID;

	@FXML private TableColumn<Student, String> columnStudentName;

	@FXML private TableColumn<Student, String> columnStudentIDF;

	@FXML private TableColumn<Student, String> columnStudentNameF;

	@FXML private TextField textCourseCode;

	@FXML private TextField textCourseName;

	@FXML private TextField textCredit;

	@FXML private TextField textFindCourse;
	
	@FXML private Button btnCourseView;

	@FXML private Button btnStudentView;

	@FXML private Button btnAddCourse;

	@FXML private Button btnMoveCourse;

	@FXML private Button btnRemoveCourse;

	@FXML private Button btnRemoveCourseF;

	@FXML private Button btnAddPartisipant;

	@FXML private Button btnFindCourse;

	@FXML private Button btnAddGrade;
	
	@FXML private ComboBox<String> cmbStudentID;

	@FXML private ComboBox<String> cmbCourseCode;

	@FXML private ComboBox<String> cmbGrade;

	@FXML private Label	lblAnswercCourseReg;

	@FXML private Label lblAnswerFindCourse;

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

		columnFindCourseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
		columnFindCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnFindCredit.setCellValueFactory(new PropertyValueFactory<>("credits"));

		columnStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		columnStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

		columnStudentIDF.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		columnStudentNameF.setCellValueFactory(new PropertyValueFactory<>("name"));

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
					tableGrade.getItems().clear();
					tableGrade.setDisable(true);
					cmbGrade.setDisable(true);
					btnAddGrade.setDisable(true);
					tableFinishedStudent.setDisable(true);
					populateTableViewFinishedCourse();
				}
				else if(newTab.equals(tabRegistrationCourse)) {
					populateTableViewRegCourse();
				}
				else if(newTab.equals(tabFindCourse)) {
					populateCmbBoxCourseCode();
				}
			}
		});

		textFindCourse.textProperty().addListener((observable) -> cmbCourseCode.setDisable(true));
		cmbCourseCode.valueProperty().addListener((observable) -> textFindCourse.setDisable(true));
		cmbCourseCode.valueProperty().addListener((observable) -> btnFindCourse.setDisable(false));
		textFindCourse.textProperty().addListener((observable) -> btnFindCourse.setDisable(false));
		populateTableViewActiveCourse();
		populateCmbBoxStudentID();
		populateCmbBoxCourseCode();
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
		tableRegisterCourse.getItems().clear();
		try {
			tableRegisterCourse.setItems(dal.selectAllActiveCourses());
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
				tableActiveStudent.setItems(dal.selectAllFromStudies(tempC.getCourseCode()));
			}
			if(tabFinishedCourse.isSelected()) {
				Course tempC = tableFinishedCourse.getSelectionModel().getSelectedItem();	
				tableFinishedStudent.getItems().clear();
				tableFinishedStudent.setItems(dal.selectAllFromHasStudied(tempC.getCourseCode()));
			}
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	@FXML
	private void populateTableViewGrade() {
		tableGrade.getItems().clear();
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
		try {
			cmbStudentID.getItems().addAll(dal.selectAllStudentID());
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	@FXML
	private void populateCmbBoxCourseCode() {
		cmbCourseCode.getItems().clear();
		try {
			cmbCourseCode.getItems().addAll(dal.selectAllCourseCode());
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	@FXML
	public void populateFindCourseTable(String c) {

		try {
			if(cmbCourseCode.getValue() != null ) {
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
				dal.removeCourse(tempC.getCourseCode());
				populateTableViewActiveCourse();
				populateCmbBoxStudentID();
			}
			if(tabFinishedCourse.isSelected()) {
				Course tempC = tableFinishedCourse.getSelectionModel().getSelectedItem();
				dal.removeCourse(tempC.getCourseCode());
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
		if (!cCode.isEmpty() && !cName.isEmpty() && !cCredit.isEmpty()) {	

			try {
				dal.insertCourse(cCode, cName, cCredit);
				lblAnswercCourseReg.setText("Course: "+cName+" added.");
				textCourseCode.clear();
				textCourseName.clear();
				textCredit.clear();
				populateTableViewRegCourse();
				populateCmbBoxCourseCode();
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
			btnRemoveCourse.setDisable(false);
			btnMoveCourse.setDisable(false);
			tableActiveStudent.setDisable(false);
			cmbStudentID.setDisable(false);
			btnAddPartisipant.setDisable(false);
			btnRemoveCourse.setDisable(false);
			populateTableViewStudentCourse();
		}
		else if(tabFinishedCourse.isSelected()) {	
			tableGrade.setDisable(false);
			tableFinishedStudent.setDisable(false);
			btnRemoveCourseF.setDisable(false);
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
		String cID = tableActiveCourse.getSelectionModel().getSelectedItem().getCourseCode();

		try {
			dal.insertStudentToCourse(sID, cID);
			populateTableViewStudentCourse();
		} catch (SQLException e) {		
			e.printStackTrace();
		}

	}
	@FXML
	public void btnAddGrade_Click(ActionEvent event) {
		Course tmpCourse = tableFinishedCourse.getSelectionModel().getSelectedItem();
		Student tmpStudent = tableFinishedStudent.getSelectionModel().getSelectedItem();
		String tmpGrade = cmbGrade.getSelectionModel().getSelectedItem();		
		try {
			dal.addGrade(tmpStudent, tmpCourse, tmpGrade);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		populateTableViewGrade();
	}
	@FXML
	public void findCourse(ActionEvent event)  {
		String cID = cmbCourseCode.getValue();
		String name = textFindCourse.getText();

		if(cmbCourseCode.getValue() != null ) {
			populateFindCourseTable(cID);
		}
		else if(textFindCourse.getText() != null) {
			populateFindCourseTable(name);
		}

		tableFindCourse.setDisable(false);
		cmbCourseCode.getItems().clear();
		textFindCourse.clear();
		populateCmbBoxCourseCode();
		cmbCourseCode.setDisable(false);
		textFindCourse.setDisable(false);
		btnFindCourse.setDisable(true);	
	}

}



