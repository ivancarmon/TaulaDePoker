package application;


import java.lang.reflect.Array;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.security.auth.login.LoginException;

import org.mindrot.jbcrypt.BCrypt;

import controlador.FormularioController;
import excepciones.LoginFalladoExcepcion;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utilidades.ConexionDB;

public class Usuario{
	
	/*
	 * Atributos
	 */
	private String user;
	private String password;
	private String nombre;
	private String apellidos;
	private Image image;
	private int fichas;
	
	public Usuario(String user,String password,String nombre,String apellidos,Image image) {
		
		this.user = user;
		this.password = password;
		this.image = image;
		this.fichas = 500;
		
	}
	
	public Usuario(String user, String password) {
		
		this(user, password, null,null,null);
		
	}
	public Usuario(String user,String password,String nombre,String apellidos) {
		
		this(user,password,nombre,apellidos,null);
		
	}
	
	
    public static void asignarMostrarOcultar(boolean passwordvisible, PasswordField pass, TextField passfd, Button b) {
    	
    	
        

        passfd.setVisible(passwordvisible);
  
        passfd.setManaged(passwordvisible);
        
        pass.setVisible(!passwordvisible);
        
        pass.setManaged(!passwordvisible);
       

        if (passwordvisible) {
        	
        	asignarBoton(b, "/vista/img/ojocerrado.png");
            passfd.setText(pass.getText());
            
            
        } else {
        	
        	asignarBoton(b, "/vista/img/ojoabierto.png");
            pass.setText(passfd.getText());
           
        }

		
    	
    	
    	
    }
    public static void asignarBoton(Button b, String ruta) {
    	
        // Crear un ImageView y asignar la imagen
        Image image = new Image(Usuario.class.getResourceAsStream(ruta));
        ImageView imageView = new ImageView(image);
        
        // Ajustar el tamaño de la imagen si es necesario
        imageView.setFitHeight(17);
        imageView.setFitWidth(17);
        
        // Asignar la imagen al botón
        b.setGraphic(imageView);
    	
    }
    
    public void LoginSuccesfull() throws LoginFalladoExcepcion {
		ConexionDB con = new ConexionDB();
		con.conectar();
		
		String sql = "SELECT pass FROM credentials WHERE user= ? AND pass = ?";
		PreparedStatement stmt;
		try {
			stmt = con.cx.prepareStatement(sql);
			
			stmt.setString(1, this.user);
			String passhash = hashearPass(this.password);
			stmt.setString(2, passhash);
			
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.next()) throw new LoginFalladoExcepcion();
			else {
				
				
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		}
		
		
		
		
    	
    	
    }
    
    public void Registrarse() {
    	ConexionDB con = new ConexionDB();
		con.conectar();
		
		String sql = "INSERT INTO cre";
		PreparedStatement stmt;
		try {
			stmt = con.cx.prepareStatement(sql);
			
			stmt.setString(1, this.user);
			String passhash = hashearPass(this.password);
			stmt.setString(2, passhash);
			
			ResultSet rs = stmt.executeQuery();
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		}
    }
    
    public static String hashearPass(String pass) {
    	
    	return BCrypt.hashpw(pass, BCrypt.gensalt());
    	
    }
    
    public static ArrayList<String> comprobarContraseña(String pass, String confirmpass) {
    	
    	ArrayList<String> Errores = new ArrayList<String>();
    	
    	
    	if(pass.equals(confirmpass)) {
    		
    		if(pass.length() < 8) {
    			
    			Errores.add("- La contraseña debe tener almenos 8 caracteres");
    		}
    		if(!pass.matches(".*[^a-zA-Z0-9].*")) {
    			
    			Errores.add("- La contraseña debe tener un simbolo");
    		}
    		if(!pass.matches(".*[A-Z].*")) {
    			
    			Errores.add("- La contraseña debe tener una mayúscula");
    			
    		}
    		
    		
    	}
    	else {
    		
    		Errores.add("");
    		
    		
    	}
    	
    	
    	
    	
    	return Errores;
    	
    }

	
	
}
