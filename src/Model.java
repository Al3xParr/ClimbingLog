import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;


public class Model{
	
	private static Connection conn = null;
	
	//Constructor
	public Model() {
		;
	}
	
	//Returns an open connection to the db
	private static Connection connect() {		
		Connection dbConnection = null;
		try {
            String path = "jdbc:sqlite:" + System.getProperty("user.dir")+ "\\src\\data.db";    
            dbConnection = DriverManager.getConnection(path);
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {System.out.println(e.getMessage());}
		return (dbConnection);
				
	}
	
	//Adding a new record to the db
	public static boolean addSession(String date, int dur,  String max, int indoor, String type, LinkedList<String> exercises) {
		
		//StringBuffer to hold the names of the exercises selected for the sql statement
		StringBuffer exString = new StringBuffer();
		StringBuffer exValString = new StringBuffer();
		for (String ex : exercises) {
			
			exString.append(", ");
			ex = Arrays.stream(ex.split("\\s+"))
			        .map(t -> t.substring(0, 1).toUpperCase() + t.substring(1))
			        .collect(Collectors.joining("_"));
			exString.append(ex);
			exValString.append(", ?");
		}
		
		String sql = "INSERT INTO sessions (date, duration, maxGrade, indoor, type" + exString.toString() +") VALUES(?, ?, ?, ?, ?" + exValString.toString() + ")";
		conn = Model.connect();
		PreparedStatement prepState = null;
		
		try {
			//Assigning values to each of the "?" in the sql statement
			prepState = conn.prepareStatement(sql);
			prepState.setString(1, date);
			prepState.setInt(2, dur);
			prepState.setString(3, max);
			prepState.setInt(4, indoor);
			prepState.setString(5, type);
			for (int i = 0; i <exercises.size(); i++) {
				prepState.setInt(i+6, 1);
			}
			
			prepState.executeUpdate();
			System.out.println("Successfully added new record");
			return true;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			//Closes the items relating to the db communication
			Model.closeItem(prepState);
			Model.closeItem(conn);
		}
	}
	
	//Adds new column to db
	public static boolean addExercise(String ex) {
		
		//Capitalises start of each word and replaces spaces with "_"
		String finalString = Arrays.stream(ex.split("\\s+"))
		        .map(t -> t.substring(0, 1).toUpperCase() + t.substring(1))
		        .collect(Collectors.joining("_"));
		
		LinkedList<String> currentEx = Model.getExercises();
		//If the exercises is already in the db function returns false
		if (currentEx.contains(ex)) { return false;}
		
		String sql = "ALTER TABLE sessions ADD COLUMN " + finalString + " INTEGER";
		conn = Model.connect();
		Statement state = null;
		
		try {
			System.out.println("tried");
			state = conn.createStatement();
			System.out.println("tried2");
			state.executeUpdate(sql);
			
			System.out.println("Successfully added new exercise");
			return true;
			
		} catch (SQLException e) {
			System.out.println("catch");
			System.out.println(e.getMessage());
			return false;
		} finally {
			System.out.println("finally");
			//Closes the items relating to the db communication
			Model.closeItem(state);
			Model.closeItem(conn);
			
		}
	}
		
	//Returns all the exercises currently listed in the db
	public static LinkedList<String> getExercises(){
		String sql = "SELECT * FROM sessions";
		conn = Model.connect();
		Statement state = null;
		ResultSet rs = null;
		ResultSetMetaData rsMeta = null;
		
		LinkedList<String> currentEx = new LinkedList<String>();
		
		try {
			state = conn.createStatement();
			
			rs = state.executeQuery(sql);
			
			rsMeta = rs.getMetaData();
			
			int columnCount = rsMeta.getColumnCount();
			
			//Gets all the exercises from the column names
			for (int i = 7; i <= columnCount ; i++) {
				currentEx.add(rsMeta.getColumnName(i));
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			//Closes the items relating to the db communication
			Model.closeItem(rsMeta);
			Model.closeItem(rs);
			Model.closeItem(state);
			Model.closeItem(conn);
		}
		
		return currentEx;
		
	}
	
	//Closes the object provided (For the db connections
	private static void closeItem(Object obj) {
		//Casts the parameter to AutoClosable object
		AutoCloseable closeObj = (AutoCloseable) obj;
		if (closeObj != null) {
			//Attempts to close object
            try { closeObj.close();} 
            catch (Exception e) {e.printStackTrace();}
	    }
		
	}

}