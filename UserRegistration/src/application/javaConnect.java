package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class javaConnect {
	
		
		public static Connection connectDB() throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/lab_assist", "root", "");
			return conn;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
