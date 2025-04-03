package controlador;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilidades.I18N;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControladorLogin{
	
	@FXML private Button loginButton;
	@FXML private TextField Usuario;
	@FXML private PasswordField pass;
	@FXML private Text Registrarse;
	
	public void initialize(){
		
		loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: darkgreen;"));
		loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: green;"));
//		try {
//			
//			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "");
//			
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
	}
	
	@FXML
	void login() {
		
		String user = Usuario.getText(); 
		String pass = Usuario.getText();
		
		String sql = String.format("SELECT password FROM credenciales WHERE user=\"%s\"", user);
		
	}
	@FXML
	void registrarse() throws IOException{
		
	    try {
	        // Indicar el idioma
	        Locale locale = new Locale("es");
	        ResourceBundle bundle = ResourceBundle.getBundle("strings", locale);

	        // Cargar la ventana
	        Parent root = FXMLLoader.load(getClass().getResource("/vista/SignUp.fxml"), bundle);

	        // Cargar la Scene
	        Scene scene = new Scene(root);

	        // Obtener el Stage desde el botón o cualquier nodo en la escena actual
	        Stage stage = (Stage) loginButton.getScene().getWindow(); // loginButton es un nodo en tu escena

	        // Asignar propiedades al Stage (esto debe hacerse antes de mostrar el Stage)
	        stage.setTitle("Mesa de Poker");
	        stage.titleProperty().bind(I18N.createStringBinding("form.titulo"));

	        // Establecer el estilo del Stage antes de hacer visible la ventana
	        stage.setResizable(false);
	        
	        
	        // Asignar la scene y mostrar
	        stage.setScene(scene);
	        stage.show();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	
	
}
