package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnection {
	
	Connection dbcon;
	
	
	public Connection getConnection() {
		
	
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String url = "jdbc:sqlserver://localhost:1433;database=master";
			String user = "sa";
			String password = "reallyStrongPwd123";


			dbcon = DriverManager.getConnection(url, user, password);
			
			
		} 
		catch(ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			
		}
		return dbcon;
		
	}
	

}
