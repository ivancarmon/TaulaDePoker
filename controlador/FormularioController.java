package controlador;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import application.Usuario;
import excepciones.LoginFalladoExcepcion;
import excepciones.RegistroFalladoException;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utilidades.I18N;

public class FormularioController {

	private boolean passwordvisible = false;
	
	@FXML
	private Label IrALogin;

	@FXML
	private Button btn_imagen;

	@FXML
	private Button btn_registrar;

	@FXML
	private ImageView img_foto;

	@FXML
	private Label lbl_apellidos;

	@FXML
	private Label lbl_fecha;

	@FXML
	private Label lbl_nombre;

	@FXML
	private Label lbl_password;

	@FXML
	private Label lbl_password1;

	@FXML
	private Label lbl_titulo;

	@FXML
	private Label lbl_usuario;
	
	@FXML
	private Label lbl_confirmpassword;
	
	@FXML
	private Label lbl_selectImage;
	
    @FXML
    private Button btn_password;

    @FXML
    private Button btn_confirmpassword;
    
    @FXML 
    private Label lbl_yatienecuenta;
    
    @FXML
    private TextField fd_password;
    
    @FXML
    private TextField fd_confirmpassword,fd_nombre,fd_apellidos,fd_user;
    
    @FXML
    private PasswordField pd_password;
    
    @FXML
    private PasswordField pd_confirmpassword;
    
    @FXML
    public Text lbl_Error;
    
    @FXML
    private ImageView img_perfil;
    
    Usuario us = new Usuario();
    
    @FXML
    public void initialize() {

		//titulo de la ventana
		lbl_titulo.textProperty().bind(I18N.createStringBinding("form.titulo"));
		lbl_nombre.textProperty().bind(I18N.createStringBinding("form.nombre"));
		lbl_apellidos.textProperty().bind(I18N.createStringBinding("form.apellidos"));
		lbl_usuario.textProperty().bind(I18N.createStringBinding("form.usuario"));
		lbl_password.textProperty().bind(I18N.createStringBinding("form.password"));
		lbl_confirmpassword.textProperty().bind(I18N.createStringBinding("form.confirmPassword"));
		lbl_selectImage.textProperty().bind(I18N.createStringBinding("form.selectImagen"));
		lbl_yatienecuenta.textProperty().bind(I18N.createStringBinding("form.yatienecuenta"));
		IrALogin.textProperty().bind(I18N.createStringBinding("form.login"));
		btn_imagen.textProperty().bind(I18N.createStringBinding("form.selectImagen"));
		btn_registrar.textProperty().bind(I18N.createStringBinding("form.registrar"));
		
		Usuario.asignarBoton(btn_confirmpassword,"/vista/img/ojoabierto.png");
		Usuario.asignarBoton(btn_password , "/vista/img/ojoabierto.png");
		
		

    }
    
    public void asignarMostrarOcultar(ActionEvent event) {
    	
    	passwordvisible = !passwordvisible;
        
        Usuario.asignarMostrarOcultar(passwordvisible, pd_password, fd_password, btn_password);
        Usuario.asignarMostrarOcultar(passwordvisible, pd_confirmpassword, fd_confirmpassword, btn_confirmpassword);
        
    }



	@FXML
	void escogerImagen(ActionEvent event) {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("PNG Files", "*.png")
				,new FileChooser.ExtensionFilter("JPG Files", "*.jpg")
				);    	
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null && selectedFile.exists()) {    		
			Image image = new Image(selectedFile.toURI().toString());
			us.setImage(image);
			img_perfil.setImage(image);
			
		}  
	}
	@FXML
	void irALogin(MouseEvent event) {

		try {
			
			String fxml = "/vista/Login.fxml";
			// Cargar la ventana
			Parent root = FXMLLoader.load(getClass().getResource(fxml));
			// Cargar la Scene
			Scene scene = new Scene(root);
			// Asignar propiedades al Stage
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			
			stage.setResizable(true);
			stage.titleProperty().unbind();
			// Asignar la scene y mostrar
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		

	}

	@FXML
	void registrar(ActionEvent event) {
		
		String user,pass,confirmpass, aux = "", nombre,apellidos;
		
		user = fd_user.getText();
		if(passwordvisible) {
			
			pass = fd_password.getText();
			confirmpass = fd_confirmpassword.getText();
			
		}else {
			
			pass = pd_password.getText();
			confirmpass = pd_confirmpassword.getText();
			
		}
		nombre = fd_nombre.getText();
		apellidos = fd_apellidos.getText();
		
		
		ArrayList<String> Errores = Usuario.comprobarContraseña(pass, confirmpass);
		
		if(Errores.size() == 0) {
			
			us.setUser(user);
			us.setPassword(pass);
			us.setNombre(nombre);
			us.setApellidos(apellidos);
			us.setFichas(500);
			try {
				us.Registrarse();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (RegistroFalladoException e) {
				// TODO Auto-generated catch block
				lbl_Error.setText(e.mensajeError());
			}
			
		}
		else {
			
			for (String s : Errores) {
				
				aux += String.format("%s%n", s);
				
			}
			lbl_Error.setText(aux);

			
		}
		
		
	
		
	}

	@FXML
	void setCastellano(MouseEvent event) {
		I18N.setLocale(new Locale("es"));
	}

	@FXML
	void setIngles(MouseEvent event) {
		I18N.setLocale(new Locale("en"));
	}

	@FXML
	void setValenciano(MouseEvent event) {
		I18N.setLocale(new Locale("ca"));
	}

}
