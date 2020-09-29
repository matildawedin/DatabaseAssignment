package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
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
	
	//Attribute 
	private Course course;
	private Student student;
	private HasStudied hasStudied;
	private DbConnection dbcon; 
	private DALS dal = new DALS();
	private Connection con;
	
	
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
	
	public DbConnection getDbcon() {
		return dbcon;
	}
	public void setDbcon(DbConnection dbcon) {
		this.dbcon = dbcon;
	}
	
	// 
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
	
	@FXML private Label lblFindStudentAnswer;
	
	//anv√§nds denna?
	@FXML private Group studentGroup;
	
	
	
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle resources) {
		// set columns in tableview
		columnCourseID.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
		coulmnCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnCredit.setCellValueFactory(new PropertyValueFactory<>("credits"));
		columnStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		columnStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
		cStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		cStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));

		
		
		dbcon = new DbConnection();
		con = dbcon.getConnection();
		
		populateStudentTable();
		populateCmbStudentID();
		populateCmbCourse();
		
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
	@FXML
	public void populateStudentTable() {
		
		try {
			tableStudent.setItems(dal.selectAllStudent());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// Add all active courses to the course table based on a specific student id
	public void populateActiveCourseTable(String studentID) {
		
		try {
			
			tableCourse.setItems(dal.selectStudies(studentID));
			
		}
		catch(SQLException e) {
		e.printStackTrace();
		}
	}
	
	// Add all completed courses to the course table based on a specific student id
	public void populatecompletedCourseTable(String studentID) {

		try {
			tableCourse.setItems(dal.selectHasStudied(studentID));

		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// Adds the grade from a certaint course to the grade table based on a specific student id
	public void populateGradeTable(String studentID) {
		
		try {
			tabelGrade.setItems(dal.selectGrade(studentID));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// Add all the current courses to the combobox
	public void populateCmbCourse() {
		cmbCourseID.getItems().clear();
		try {
			cmbCourseID.getItems().addAll(dal.selectAllCourseID());
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);


		}
	}
	
	// Add all the current students to the combobox
	public void populateCmbStudentID() {
		cmbStudentID.getItems().clear();
		try {
			cmbStudentID.getItems().addAll(dal.selectAllStudentID());
		}
		catch(SQLException e) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
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
		}

	}
	
	//--------------------------------Select methods-------------------------------------------
	
	
	//
	@FXML
	public String selectStudent(MouseEvent event) {

		Student s = tableStudent.getSelectionModel().getSelectedItem();
		String sID = s.getStudentID();
		
		
		
		if(s != null) {
			
			lblResponseStudent.setText("Student selected");
			
			
		} 
		
		// beh√∂vs alla dessa????
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
		
		
		
		return sID;
		
	}
	@FXML
	public void selectTypeOfCourse(ActionEvent event) throws SQLException {
		
		String sID = selectStudent(null);
		
		
		
		if(rbtnActive.isSelected()) {
			populateActiveCourseTable(sID);
			rbtnActive.setSelected(false);
			
			
		}
		else if(rbtnCompleted.isSelected()) {
			populatecompletedCourseTable(sID);
			tabelGrade.setDisable(false);
			populateGradeTable(sID);
			rbtnCompleted.setSelected(false);
			
			
		}
		tableCourse.setDisable(false);
		
	}
	
	
	
	//--------------------------------Button methods-------------------------------------------



	//get the value from the TextField then use insertstudent to add the student
	
	@FXML
	public void btnAddStudent(ActionEvent event) throws SQLException {
		
		

		if(!textStudentName.getText().isEmpty()&& textStudentName.getText().matches("^[a-zÂ‰ˆA-Z≈ƒ÷]+$")) {

			try {
				dal.insertStudent(dal.generateStudentId(),textStudentName.getText());
				lblResponseStudent.setText( "Student is added!");
				
			} catch (SQLException e) {
				if(e.getErrorCode() == 0) {
					lblResponseStudent.setText("There was a problem connecting to the database\nPlease check your connection");
				}
			}
		}
		else {
			lblResponseStudent.setText("Please fill out the field \nand a name can only contain\n letters");
		}
		
		populateStudentTable();
		textStudentName.clear();
		populateCmbStudentID();
		
		
	}
	
	
	
	
	
	
	@FXML
	public void addCourse(ActionEvent event) {
		
		String cc = cmbCourseID.getValue();
		Student s = tableStudent.getSelectionModel().getSelectedItem();
		
		lblAddCourseResponse.setText(null);
		
		
		try {
			if(cmbCourseID.getValue() != null) {
			dal.insertCourseToStudent(s.getStudentID(), cc);
			lblAddCourseResponse.setText("New course added!");
			
			}
			else {
				lblAddCourseResponse.setText("Please select a course");
				
			}
		} catch (SQLException e) {
			if(e.getErrorCode() == 2627) {
				lblAddCourseResponse.setText(s.getName() + " already studies/has studied that course!\nPlease choose another course!");
				}
				else if(e.getErrorCode() == 0) {
					lblAddCourseResponse.setText("There was a problem connecting to the database, please check your connection.");
				} 
			
		}
		cmbCourseID.getItems().clear();
		populateCmbCourse();
		populateActiveCourseTable(s.getStudentID()); // syns √§ven om course table √§r disable, √§ndra?
		
		
		
	}
	
	@FXML
	public void findStudent(ActionEvent event)  {
		
		String sID = cmbStudentID.getValue();
		String name = textName.getText();
		
		if(cmbStudentID.getValue() != null ) {
			populateFindStudentTable(sID);
		}
		else if(!textName.getText().isEmpty( )&& textStudentName.getText().matches("^[a-zÂ‰ˆA-Z≈ƒ÷]+$")) {
			populateFindStudentTable(name);
		}
		else { 
			lblFindStudentAnswer.setText("Please fill in field or studentID \nand keep in mind that a\nStudent name can only contain \nletters");
		}
		tabelFindStudent.setDisable(false);
		cmbStudentID.getItems().clear();
		textName.clear();
		populateCmbStudentID();
		cmbStudentID.setDisable(false);
		textName.setDisable(false);
		btnFindStudent.setDisable(true);
		
	}
	
	
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


	
	
	
