package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
	
	String bd="poker";
	String url="jdbc:mysql://localhost:3306/";
	String user="root";
	String password="";
	String driver="com.mysql.cj.jdbc.Driver";
	public Connection cx;
	
	public ConexionDB() {

		
	}
	
	public Connection conectar(){
		
		try {
			Class.forName(driver);
			cx=DriverManager.getConnection(url+bd, user, password);
			
		} catch (ClassNotFoundException | SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		return cx;
		
		
	}
	public void deconectar() {
		
		try {
			cx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
