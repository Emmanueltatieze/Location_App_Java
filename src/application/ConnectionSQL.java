package application;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQL {
	public Connection cn=null;
	public static Connection connexionDB()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection cn=DriverManager.getConnection("jdbc:mysql://localhost:3306/location_javaFX", "root", "");
			System.out.println("Connexion reussie");
			return cn;

		} catch (ClassNotFoundException| SQLException e) {
			System.out.println("Connexion echouer");
			e.printStackTrace();
			return null;
		}
	}

}
