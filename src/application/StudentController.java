package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StudentController implements Initializable {

	//Declare new objects 
	private Course course;
	private Student student;
	private HasStudied hasStudied;
	
	private DAL dal = new DAL();



	// Getter and Setters
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

	// FXML Objects
	@FXML private Button btnCourseView;

	@FXML private Button btnStudentView;

	@FXML private TableView<Course> tableCourse;

	@FXML private TableColumn<Course, String> columnCourseID;

	@FXML private TableColumn<Course, String> coulmnCourseName;

	@FXML private TableColumn<Course, String> columnCredit;

	@FXML private TableView<HasStudied> tabelGrade;

	@FXML private TableColumn<HasStudied,String> columnGrade;

	@FXML private TableView<Student> tableStudent;


	@FXML private TableColumn<Student, String> columnStudentID;

	@FXML private TableColumn<Student, String> columnStudentName;

	@FXML private Button btnAddStudent;

	@FXML private TextField textStudentName;

	@FXML private Label lblResponseStudent;


	@FXML private Button btnRemoveStudent;

	@FXML private RadioButton rbtnCompleted;

	@FXML private RadioButton rbtnActive;

	@FXML private Label lblToRbn;

	@FXML private Label lblAddCourse;

	@FXML private ComboBox<String> cmbCourseID;

	@FXML private Button btnAddNewCourse;

	@FXML private TabPane tabPaneStudent;

	@FXML private Tab regStudentTab;

	@FXML private Tab findStudentTab;


	@FXML private ComboBox<String> cmbStudentID;

	@FXML private TextField textName;

	@FXML private Button btnFindStudent;

	@FXML private TableView<Student> tabelFindStudent;

	@FXML private TableColumn<Student, String> cStudentID;

	@FXML private TableColumn<Student, String> cStudentName;

	@FXML private Label lblAddCourseResponse;

	@FXML private Label lblFindStudentResponse;




	@Override
	public void initialize(URL url, ResourceBundle resources) {
		// set columns in tableview
		columnCourseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
		coulmnCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnCredit.setCellValueFactory(new PropertyValueFactory<>("credits"));
		columnStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		columnStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
		cStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		cStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));

		//populate tabel
		populateStudentTable();
		populateCmbStudentID();
		populateCmbCourse();

		//Add listerner that observs active changes
		textName.textProperty().addListener((observable) -> cmbStudentID.setDisable(true));
		cmbStudentID.valueProperty().addListener((observable) -> textName.setDisable(true));
		cmbStudentID.valueProperty().addListener((observable) -> btnFindStudent.setDisable(false));
		textName.textProperty().addListener((observable) -> btnFindStudent.setDisable(false));

	}

	// Change view to CourseView
	@FXML
	public void showCourseView(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Coursess.fxml"));
			Parent root = (Parent) loader.load();


			Scene ExamViewScene = new Scene(root);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

			window.setScene(ExamViewScene);

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	//--------------------------------Populate methods-------------------------------------------

	// Add all the current student to the studentTable

	public void populateStudentTable() {

		try {
			tableStudent.setItems(dal.selectAllStudent());


		} catch (SQLException e) {
			e.printStackTrace();

			if(e.getErrorCode() == 0) {
				tableStudent.setAccessibleText("Error!\nThere was a problem connecting to the database\nPlease check your connection");
			}
		}

	}

	// Add all active courses to the course table based on a specific student id
	public void populateActiveCourseTable(String studentID) {

		try {

			tableCourse.setItems(dal.selectStudies(studentID));

		}
		catch(SQLException e) {
			e.printStackTrace();

			if(e.getErrorCode() == 0) {
				tableCourse.setAccessibleText("Error!\nThere was a problem connecting to the database\nPlease check your connection");
			}
		}
	}

	// Add all completed courses to the course table based on a specific student id
	public void populateFinishedCourseTable(String studentID) {

		try {
			tableCourse.setItems(dal.selectHasStudied(studentID));

		}
		catch(SQLException e) {
			e.printStackTrace();

			if(e.getErrorCode() == 0) {
				tableCourse.setAccessibleText("Error!\nThere was a problem connecting to the database\nPlease check your connection");
			}
		}
	}


	// Adds the grade from a certaint course to the grade table based on a specific student id
	public void populateGradeTable(String studentID) {

		try {
			tabelGrade.setItems(dal.selectGrade(studentID));
		}
		catch(SQLException e) {
			e.printStackTrace();

			if(e.getErrorCode() == 0) {
				tabelGrade.setAccessibleText("Error!\nThere was a problem connecting to the database\nPlease check your connection");
			}
		}

	}

	// Add all the current courses to the combobox
	public void populateCmbCourse() {
		cmbCourseID.getItems().clear();
		try {
			cmbCourseID.getItems().addAll(dal.selectAllCourseID());
		}
		catch(SQLException e) {
			e.printStackTrace();

			if(e.getErrorCode() == 0) {
				lblAddCourseResponse.setText("Error!\nThere was a problem connecting to the database\nPlease check your connection");
			}


		}
	}

	// Add all the current students to the combobox
	public void populateCmbStudentID() {
		cmbStudentID.getItems().clear();
		try {
			cmbStudentID.getItems().addAll(dal.selectAllStudentID());
		}
		catch(SQLException e) {
			e.printStackTrace();

			if(e.getErrorCode() == 0) {
				lblFindStudentResponse.setText("Error!\nThere was a problem connecting to the database\nPlease check your connection");
			}
		}


	}
	// Add student to findstudent table based on a specific student id or name
	public void populateFindStudentTable(String s) {

		try {
			if(cmbStudentID.getValue() != null ) {
				tabelFindStudent.setItems(dal.selectStudentbyID(s));

			}
			else if(textName.getText() != null) {
				tabelFindStudent.setItems(dal.selectStudentbyName(s));

			}

		} catch (SQLException e) {
			e.printStackTrace();

			if(e.getErrorCode() == 0) {
				tabelFindStudent.setAccessibleText("Error!\nThere was a problem connecting to the database\nPlease check your connection");
			}
		}

	}

	//--------------------------------Select methods-------------------------------------------


	// Mouse event that enable buttons and populate combobox
	@FXML
	public void selectStudent(MouseEvent event) {

		Student s = tableStudent.getSelectionModel().getSelectedItem();

		if(s != null) {

			lblResponseStudent.setText("Student selected");
		} 

		// behövs alla dessa????
		cmbCourseID.getItems().clear();
		tableCourse.getItems().clear();
		tabelGrade.getItems().clear();
		tableCourse.setDisable(true);
		tabelGrade.setDisable(true);
		btnRemoveStudent.setDisable(false);
		rbtnActive.setDisable(false);
		rbtnCompleted.setDisable(false);
		lblToRbn.setDisable(false);
		lblAddCourse.setDisable(false);
		cmbCourseID.setDisable(false);
		btnAddNewCourse.setDisable(false);
		populateCmbCourse();
		lblAddCourseResponse.setText(null);
		lblResponseStudent.setText(null);
	}

	// Action event that populate the course table and grade table depending on which radio button that is selected
	@FXML
	public void selectTypeOfCourse(ActionEvent event) throws SQLException {

		Student tempS = tableStudent.getSelectionModel().getSelectedItem();
		String sID = tempS.getStudentID();

		if(rbtnActive.isSelected()) {
			populateActiveCourseTable(sID);
			rbtnActive.setSelected(false);
		}

		else if(rbtnCompleted.isSelected()) {
			populateFinishedCourseTable(sID);
			tabelGrade.setDisable(false);
			populateGradeTable(sID);
			rbtnCompleted.setSelected(false);
		}

		tableCourse.setDisable(false);

	}



	//--------------------------------Button methods-------------------------------------------



	// Create new student with the name value given in textfield
	@FXML
	public void btnAddStudent(ActionEvent event) throws SQLException {
		String name = textStudentName.getText();

		if(!name.isEmpty() && name.matches("^[a-zA-Z]+$")) {

			try {
				dal.insertStudent(dal.generateStudentId(),name);
				lblResponseStudent.setText( "Student is added!");
				populateStudentTable();


			} catch (SQLException e) {
				e.printStackTrace();
				if(e.getErrorCode() == 0) {
					lblResponseStudent.setText("Error!\nThere was a problem connecting to the database\nPlease check your connection");
				}
			}
			textStudentName.clear();
			populateCmbStudentID();

		}
		else if(name.isEmpty()) {
			lblResponseStudent.setText("Error!\nPlease fill out the field");
		}
		else if(!name.matches("^[a-zA-Z]+$")) {
			lblResponseStudent.setText("Error!\nName can only contain letters");

		}
	}


	// Add a new active course to the selected student
	@FXML
	public void addCourse(ActionEvent event) {

		String cc = cmbCourseID.getValue();
		Student tempS = tableStudent.getSelectionModel().getSelectedItem();

		lblAddCourseResponse.setText(null);


		try {

			if(cc != null) {
				dal.insertStudentToCourse(tempS.getStudentID(), cc);
				lblAddCourseResponse.setText("New course added!");



			}
			else {
				lblAddCourseResponse.setText("Error!\nPlease select a course");

			}
		} catch (SQLException e) {
			e.printStackTrace();
			if(e.getErrorCode() == 2627) {
				lblAddCourseResponse.setText("Error!\n" + tempS.getName() + " already studies/has studied that course!\nPlease choose another course!");
			}
			else if(e.getErrorCode() == 0) {
				lblAddCourseResponse.setText("Error!\nThere was a problem connecting to the database\nPlease check your connection.");
			} 

		}
		cmbCourseID.getItems().clear();
		populateCmbCourse();
		populateActiveCourseTable(tempS.getStudentID()); // syns även om course table är disable, ändra?
		tableCourse.setDisable(false);


	}

	// Show student on the student table matching to the value in the given fields/combobox
	@FXML
	public void findStudent(ActionEvent event)  {
		lblFindStudentResponse.setText(null);
		String sID = cmbStudentID.getValue();
		String name = textName.getText();

		if(sID != null ) {
			populateFindStudentTable(sID);

		}
		else if(name.isEmpty( )){ 
			lblFindStudentResponse.setText("Please pick a student ID or\nenter a Student Name");

		}
		else if(!name.matches("^[a-zA-Z]+$")) {
			lblFindStudentResponse.setText("Error!\nKeep in mind that\nStudent name can only contain \nletters");
		}
		else if(!name.isEmpty( )&& name.matches("^[a-zA-Z]+$")) {
			populateFindStudentTable(name);
		}
		else {
			lblFindStudentResponse.setText("No student with that name exists");
		}
		tabelFindStudent.setDisable(false);
		cmbStudentID.getItems().clear();
		textName.clear();
		populateCmbStudentID();
		cmbStudentID.setDisable(false);
		textName.setDisable(false);
		btnFindStudent.setDisable(true);

	}

	// Remove the selected student and then update the student table 
	@FXML
	public void btnRemoveStudent(ActionEvent event) {

		Student tempS = tableStudent.getSelectionModel().getSelectedItem();
		String sID = tempS.getStudentID();

		if(tempS != null) {

			try {

				lblResponseStudent.setText( tempS.getName() + " is removed");
				dal.removeStudent(sID);
				tableStudent.getItems().clear();

			}
			catch (SQLException e) {
				e.printStackTrace();
				if(e.getErrorCode() == 0) {
					lblResponseStudent.setText("Error!\nThere was a problem connecting to the database\nPlease check your connection.");
				} 
			}
		}
		populateStudentTable();
		btnRemoveStudent.setDisable(true);
		cmbCourseID.setDisable(true);
		btnAddNewCourse.setDisable(true);
		rbtnActive.setDisable(true);
		rbtnCompleted.setDisable(true);

	}

}





