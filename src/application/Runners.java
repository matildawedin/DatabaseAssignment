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


// lägger till extends för att göra det till en java fx projekt 
public class Runners extends Application{	
	
	public void start(Stage primaryStage) throws Exception{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Coursess.fxml"));
		
			Parent root = (Parent) loader.load();
			
			// koppla till controller 
			
			loader.getController();
				
		    // vad används detta för?
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.show();
			
			
		}
		catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	
	
/*Note: all kod här borde inte vara i main, utan ska delas upp*/
	public static void main(String[] args) {
		
		launch(args);
	}

}
