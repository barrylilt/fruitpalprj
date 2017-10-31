package fruitpalprj.fruitpalprj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Fruitpal {
	  private static Connection connect = null;
	  private static PreparedStatement preparedStatement = null;
	  private static final String DB_CONNECTION = "jdbc:mysql://localhost/fruitpal";
	  private static final String DB_USER = "root";
	  private static final String DB_PASSWORD = "root";
		
		
		
		public static void main(String[] args) {
			String commodity = args[0];
		    Double price = Double.parseDouble(args[1]);
		    Double unit = Double.parseDouble(args[2]);
		    
		    
			try {

				selectRecordsFromTable(commodity, price, unit);

			} catch (SQLException e) {

				System.out.println(e.getMessage());

			}

		}
			
		 public static void selectRecordsFromTable(String commodity, Double price, Double unit) throws SQLException {

				
				
				String selectSQL = "select COUNTRY, (round(((?+VARIABLE_OVERHEAD)*? +FIXED_OVERHEAD ), 2)) AS COST, (? + VARIABLE_OVERHEAD) AS PRICE, FIXED_OVERHEAD " 
						+"FROM vendors where COMMODITY=? order by COST desc";
	 
				try {
					// DB connection setup 
				      //connect = DriverManager.getConnection("jdbc:mysql://localhost/fruitpal?" + "user=root&password=root");
				      
				    connect=getDBConnection();	
				      
					preparedStatement = connect.prepareStatement(selectSQL);
					preparedStatement.setDouble(1, price);
					preparedStatement.setDouble(2, unit);
					preparedStatement.setDouble(3, price);
					preparedStatement.setString(4, commodity);

					// execute select SQL stetement
					ResultSet rs = preparedStatement.executeQuery();

					while (rs.next()) {

						Double cost = rs.getDouble("COST");
						Double unitprice= rs.getDouble("PRICE");
						Double fixed_overhead=rs.getDouble("FIXED_OVERHEAD");
						String country=rs.getString("COUNTRY");

						System.out.println(country + " " +cost + " | (" + unitprice +"x " + unit +") + " + fixed_overhead);
						

					}

				} catch (SQLException e) {

					System.out.println(e.getMessage());

				} finally {

					if (preparedStatement != null) {
						preparedStatement.close();
					}

					if (connect != null) {
						connect.close();
					}

				}

			}

	
		private static Connection getDBConnection() {

			Connection dbConnection = null;

			try {

				dbConnection = DriverManager.getConnection(
	                             DB_CONNECTION, DB_USER,DB_PASSWORD);
				return dbConnection;

			} catch (SQLException e) {

				System.out.println(e.getMessage());

			}

			return dbConnection;

		}
}
