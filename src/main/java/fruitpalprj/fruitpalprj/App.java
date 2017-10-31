package fruitpalprj.fruitpalprj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class App 
{
	 private static Connection connect = null;
	  private static PreparedStatement preparedStatement = null;
	  private static final String DB_CONNECTION = "jdbc:mysql://localhost/fruitpal";
	  private static final String DB_USER = "root";
	  private static final String DB_PASSWORD = "root";
   
	  public static void main( String[] args ) throws Exception
    {  
    	int result=importJson();
    	int result2=importTxt();
    	
        System.out.println( "json data import status is " +result);
        System.out.println( "json data import status is " +result2);
    }     

	 public static int importJson() throws Exception {
	   int status = 0;  
	   
	    try {
	      //Class.forName("com.mysql.jdbc.Driver");

	      // DB connection setup 
	      //connect = DriverManager.getConnection("jdbc:mysql://localhost/fruitpal?" + "user=root&password=root");
	      connect=getDBConnection();	
	      
	      preparedStatement = connect
		          .prepareStatement("insert ignore into vendors (COMMODITY, COUNTRY, VARIABLE_OVERHEAD, FIXED_OVERHEAD) VALUES (?, ?, ?, ?)");
		          		
	      
	      JSONParser parser = new JSONParser();
	      Object obj = parser.parse(new FileReader("C:/resources/jsonfile/vendor1.json"));
		     
	      JSONArray jsonArray = (JSONArray) obj;
	      
	     
	      int length = jsonArray.size();
	      System.out.println(length);
   
	      for (int i =0; i< length; i++) {
	    	  JSONObject jsonObject = (JSONObject) jsonArray.get(i);
	          
	    	  String COMMODITY = (String) jsonObject.get("COMMODITY");
	   	      preparedStatement.setString(1, COMMODITY);
	   	      System.out.println(COMMODITY);
	   	      
	   	      String COUNTRY = (String) jsonObject.get("COUNTRY");
	   	      preparedStatement.setString(2, COUNTRY);
	   	      System.out.println(COUNTRY);
	   	     
	   	      String variable = (String) jsonObject.get("VARIABLE_OVERHEAD");
	   	      Double VARIABLE_OVERHEAD=Double.parseDouble(variable);
	   	      preparedStatement.setDouble(3, VARIABLE_OVERHEAD);
	   	      System.out.println(VARIABLE_OVERHEAD);
	   	      
	   	      String fixed= (String) jsonObject.get("FIXED_OVERHEAD");
	   	      Double FIXED_OVERHEAD=Double.parseDouble(fixed);
	   	      preparedStatement.setDouble(4, FIXED_OVERHEAD);
	   	      System.out.println(FIXED_OVERHEAD);
	   	      		      
	   	      status = preparedStatement.executeUpdate();
	         }
	      

	    } catch (Exception e) {
	      throw e;
	    } finally {
	      try {
	          if (connect != null) {
	             connect.close();
	           }

	         } catch (Exception e) {

	         }
	    }
	    return status;
	  }
	 
	 public static int importTxt()throws Exception{
		 int status=0;
	    try{
	    	
	    	// DB connection setup 
		      connect = DriverManager.getConnection("jdbc:mysql://localhost/fruitpal?" + "user=root&password=root");
		      
		      preparedStatement = connect
			          .prepareStatement("insert ignore into vendors (COMMODITY, COUNTRY, VARIABLE_OVERHEAD, FIXED_OVERHEAD) VALUES (?, ?, ?, ?)");
			          		
		      // PreparedStatements 
		      /*preparedStatement = connect
		          .prepareStatement("insert into vendors (COMMODITY, COUNTRY, VARIABLE_OVERHEAD, FIXED_OVERHEAD) VALUES (?, ?, ?, ?)");
		 */
	    	 preparedStatement = connect
			          .prepareStatement("insert ignore into vendors (COMMODITY, COUNTRY, VARIABLE_OVERHEAD, FIXED_OVERHEAD) VALUES (?, ?, ?, ?)");
			          		
	    	 
		      FileReader input = new FileReader("C:/resources/textfile/vendor2.txt");
		 
		 BufferedReader br= new BufferedReader(input);
		 String myLine;

		 while ( (myLine = br.readLine()) != null){
		     System.out.println(myLine);
		     String[] array = myLine.split(" ");
		    
		     System.out.println(array[0]);
		     preparedStatement.setString(1, array[0]);
	   	      
		     System.out.println(array[1]);
		     preparedStatement.setString(2, array[1]);
	   	      
		     System.out.println(array[3]);
	   	      Double VARIABLE_OVERHEAD=Double.parseDouble(array[3]);
	   	      preparedStatement.setDouble(3, VARIABLE_OVERHEAD);
	   	     	
	   	   System.out.println(array[2]);
	   	      Double FIXED_OVERHEAD=Double.parseDouble(array[2]);
	   	      preparedStatement.setDouble(4, FIXED_OVERHEAD);
	   	    
	   	   status = preparedStatement.executeUpdate();
		 } 
		 br.close();
	    }catch (Exception e) {
		      throw e;
		    } finally {
		      try {
		          if (connect != null) {
		             connect.close();
		           }

		         } catch (Exception e) {

		         }
		    }
		     return status;
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

