import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;


public class Model{
	
	/*
	private int sessionId;
	private String date;
	private int duration;
	private String maxGrade;
	private boolean indoor;
	private String type;
	private LinkedList<String> exercises;
	*/
	private static Connection conn = null;
	
	
	public Model() {
		;
	}
	/*
	public Model(int sessionId, String date, int duration, String maxGrade, boolean indoor, String type, LinkedList<String> exercises){
		this.sessionId = sessionId;
		this.date = date;
		this.duration = duration;
		this.maxGrade = maxGrade;
		this.indoor = indoor;
		this.type = type;
		this.exercises = exercises;
		
	}*/
	
	private static Connection connect() {		
		Connection dbConnection = null;
		try {
            
            String path = "jdbc:sqlite:" + System.getProperty("user.dir")+ "\\src\\data.db";    
            dbConnection = DriverManager.getConnection(path);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        
        }
		
		return (dbConnection);
				
	}
	
	public static boolean addSession(String date, int dur,  String max, int indoor, String type, LinkedList<String> exercises) {
		
		StringBuffer exString = new StringBuffer();
		StringBuffer exValString = new StringBuffer();
		for (String ex : exercises) {
			exString.append(", ");
			exString.append(ex);
			exValString.append(", ?");
		}
				
		String sql = "INSERT INTO sessions (date, duration, maxGrade, indoor, type" + exString.toString() +") VALUES(?, ?, ?, ?, ?" + exValString.toString() + ")";
		conn = Model.connect();
		PreparedStatement prepState = null;
		try {
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
			Model.closeItem(prepState);
			Model.closeItem(conn);
			
		}
			

	}
	
	public static boolean addExercise(String ex) {
		
		
		String finalString = Arrays.stream(ex.split("\\s+"))
		        .map(t -> t.substring(0, 1).toUpperCase() + t.substring(1))
		        .collect(Collectors.joining("_"));
		
		/*
		for (char ch : ex.toCharArray()) {
			
		}
		
		StringBuffer finalString = new StringBuffer();
		String[] words = ex.split(" ");
		
		for (String str : words) {
			finalString.append(Character.toUpperCase(str.charAt(0)));
			finalString.append(str.substring(1, str.length()));
		}
		*/
		LinkedList<String> currentEx = Model.getExercises();
		
		if (currentEx.contains(ex)) { return false;}
		
		String sql = "ALTER TABLE sessions ADD COLUMN " + finalString + " INTEGER";
		conn = Model.connect();
		Statement state = null;
		
		try {
			state = conn.createStatement();
			
			state.executeQuery(sql);
			
			System.out.println("Successfully added new exercise");
			
			return true;
			
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
			return false;
		} finally {
			Model.closeItem(state);
			Model.closeItem(conn);
			
		}
	}
	
	/*
	public Model getSessionById(int id) {
		sql = "FROM sessions SELECT * WHERE sessionId = " + String.valueOf(id);
		
		conn = Model.connect();
		
		Model result = null;
		
		try {
			Statement state = conn.createStatement();
			
			ResultSet rs = state.executeQuery(sql);
			
	
			String resDate = rs.getString("date");
			int resDur = rs.getInt("duration");
			String resMax = rs.getString("maxGrade");
			boolean resIn;
			if (rs.getInt("indoor") == 1) {resIn = true;}
			else { resIn = false;}
			String resType = rs.getString("type");
			LinkedList<String> resEx = new LinkedList<String>();
			
			ResultSetMetaData rsMeta = rs.getMetaData();
			
			int columnCount = rsMeta.getColumnCount();
			
			for (int i = 7; i < columnCount ; i++) {
				if (rs.getInt(i) == 1) {
					resEx.add(rsMeta.getColumnName(i));
				}
			}
			
			result = new Model(id, resDate, resDur, resMax, resIn, resType, resEx);
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
		
	}
	
	*/
	
	
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
			
			for (int i = 7; i < columnCount ; i++) {
				currentEx.add(rsMeta.getColumnName(i));
				
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			Model.closeItem(rsMeta);
			Model.closeItem(rs);
			Model.closeItem(state);
			Model.closeItem(conn);
			
		}
		return currentEx;
		
	}
	
	private static void closeItem(Object obj) {
		AutoCloseable closeObj = (AutoCloseable) obj;
		if (closeObj != null) {
            try {
				closeObj.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		
	}
	
	
}