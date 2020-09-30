package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Runners extends Application{	
	
	//Start method that connects to the fxml file
	public void start(Stage primaryStage) throws Exception{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Coursess.fxml"));
		
			Parent root = (Parent) loader.load();
			
			loader.getController();
				
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.show();
			
			
		}
		catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	// Main method that launch the program
	public static void main(String[] args) {
		
		launch(args);
	}

}
