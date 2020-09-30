package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection extends UrlLogin {

	Connection dbcon;


	public Connection getConnection() {


		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");


			dbcon = DriverManager.getConnection(url, user, password);


		} 
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();

		}
		return dbcon;

	}


}
