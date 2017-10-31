package fruitpalprj.fruitpalprj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionUtil {
	    
	    private static PreparedStatement preparedStatement = null;
	  //private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
		private static final String DB_CONNECTION = "jdbc:mysql://localhost/fruitpal";
		private static final String DB_USER = "root";
		private static final String DB_PASSWORD = "root";
		private static Connection dbConnection = null;
		
		private static Connection getDBConnection() {

			 dbConnection = null;

			/*try {

				Class.forName(DB_DRIVER);

			} catch (ClassNotFoundException e) {

				System.out.println(e.getMessage());

			}
*/
			try {

				dbConnection = DriverManager.getConnection(
	                             DB_CONNECTION, DB_USER,DB_PASSWORD);
				return dbConnection;

			} catch (SQLException e) {

				System.out.println(e.getMessage());

			}

			return dbConnection;

		}
		
		public ConnectionUtil(){
			dbConnection = getDBConnection();
		}
		
		
}
