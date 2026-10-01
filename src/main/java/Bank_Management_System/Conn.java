package Bank_Management_System;

import java.sql.*;
//import java.sql.DriverManager;

public class Conn
{
	private static String url = "jdbc:postgresql://localhost:5433/BankManagementSystem"; // default port 5432
	private static String user = "postgres";  
	private static String pass = "123"; 


	Connection connect;
	Statement s;
	
	
	public Conn() {
		try {
            // Load PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Connect to your DB (update URL, user, password)
            connect = DriverManager.getConnection(url,user,pass);
//                    "jdbc:postgresql://localhost:5433/BankManagementSystem",
//                    "postgres",
//                    "123"
//            );

            // Create statement
            s = connect.createStatement();

            System.out.println("Database connected successfully!");

        } catch (Exception e) {
//            System.out.println("PostgreSQL JDBC Driver not found!");
            e.printStackTrace();
        } 
	}

}
