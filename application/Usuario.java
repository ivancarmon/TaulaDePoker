package application;



import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;

import org.mindrot.jbcrypt.BCrypt;

import controlador.FormularioController;
import excepciones.LoginFalladoExcepcion;
import excepciones.RegistroFalladoException;
import javafx.embed.swing.SwingFXUtils;
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

	private int id;
	private String user;
	private String password;
	private String nombre;
	private String apellidos;
	private Image image;
	private int fichas;

	public Usuario(int id, String user,String password,String nombre,String apellidos,Image image) {

		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.user = user;
		this.password = password;
		this.image = image;
		this.fichas = 500;

	}

	public Usuario(String user, String password) {

		this(0, user, password, null,null,null);

	}
	public Usuario(String user,String password,String nombre,String apellidos) {

		this(0,user,password,nombre,apellidos,null);

	}



	public Usuario() {



	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getFichas() {
		return fichas;
	}

	public void setFichas(int fichas) {
		this.fichas = fichas;
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

		String sql = "SELECT id,pass,imagen,fichas FROM credentials WHERE user= ?";
		PreparedStatement stmt;
		try {
			stmt = con.cx.prepareStatement(sql);

			stmt.setString(1, this.user);


			ResultSet rs = stmt.executeQuery();

			if(!rs.next()) throw new LoginFalladoExcepcion();
			String hash = rs.getString("pass");

			if(!BCrypt.checkpw(this.password, hash)) throw new LoginFalladoExcepcion();
			else {


				this.setId(rs.getInt("id"));
				if (rs.getBytes("imagen") != null) {
					
					cargarImagenDesdeBlob(rs.getBytes("imagen"));
					
				}
				this.setFichas(rs.getInt("fichas"));
				


			}


		} catch (SQLException e) {

			e.printStackTrace();

		}






	}

	public void Registrarse() throws SQLException, RegistroFalladoException {
		ArrayList<String> erroresCampos = new ArrayList<>();
		
		ConexionDB con = new ConexionDB();
		con.conectar();

		
		if(this.apellidos.equals("")) erroresCampos.add("apellidos");
		if(this.nombre.equals("")) erroresCampos.add("nombre");
		if(this.user.equals("")) erroresCampos.add("usuario");
		
		if(erroresCampos.size() > 1) {
			
			throw new RegistroFalladoException(erroresCampos.toString().replaceAll("[\\[\\]]", ""), true);
			
		}
		else if(erroresCampos.size() == 1) throw new RegistroFalladoException(erroresCampos.toString().replaceAll("[\\[\\]]", ""), false);
		if(this.image != null) {
			String sql = "INSERT INTO credentials(user,pass,imagen,nombre,apellidos) VALUES(?,?,?,?,?)";
			PreparedStatement stmt;

			stmt = con.cx.prepareStatement(sql);

			stmt.setString(1, this.user);
			String passhash = hashearPass(this.password);
			stmt.setString(2, passhash);
			stmt.setBytes(3, imageToByteArray(this.image));
			stmt.setString(4, this.nombre);
			stmt.setString(5, apellidos);

			stmt.executeUpdate();
		}
		else {
			String sql = "INSERT INTO credentials(user,pass,nombre,apellidos) VALUES(?,?,?,?)";
			PreparedStatement stmt;

			stmt = con.cx.prepareStatement(sql);

			stmt.setString(1, this.user);
			String passhash = hashearPass(this.password);
			stmt.setString(2, passhash);
			stmt.setString(3, this.nombre);
			stmt.setString(4, apellidos);

			stmt.executeUpdate();
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

	public static byte[] imageToByteArray(Image image) {

		BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			ImageIO.write(bImage, "png", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			try {
				ImageIO.write(bImage, "jpg", baos);
				return baos.toByteArray();
			} catch (IOException ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}
	public void cargarImagenDesdeBlob(byte[] datosImagen) {
	    if (datosImagen != null) {
	        ByteArrayInputStream bis = new ByteArrayInputStream(datosImagen);
	        Image imagen = new Image(bis);
	        this.image = imagen;
	    }
	}




}
