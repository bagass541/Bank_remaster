package _Bank_remaster.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * The database connection class which establishes connection with database.
 */
public class DatabaseConnection {
	
	private static final String URL = "jdbc:postgresql://localhost:5432/clever_bank";
	private static final String USERNAME = "bagas";
	private static final String PASSWORD = "12345";
	
	/*
	 * Establishes connection with the database.
	 * 
	 * @return connection to the database.
	 */
	public static Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Error with connecting to database");
		} 
	}
	
	/*
	 * Close the connection with the database.
	 * If connection equals null, method does nothing.
	 * 
	 * @param connection The connection which had opened before.
	 */
	public static void closeConnection(Connection connection) {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
