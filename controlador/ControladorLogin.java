package controlador;

import application.Usuario;
import excepciones.LoginFalladoExcepcion;
import javafx.animation.PauseTransition;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import utilidades.*;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControladorLogin{

	private boolean passwordvisible = false;

	@FXML private Button loginButton;
	@FXML private TextField Usuario;
	@FXML private PasswordField pass;
	@FXML private TextField passfd;
	@FXML private Text Registrarse;
	@FXML private Button btn_password;
	@FXML private Text lb_LoginFallado;

	public void initialize(){

		loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: darkgreen;"));
		loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: green;"));
		application.Usuario.asignarBoton(btn_password, "/vista/img/ojoabierto.png");
		ConexionDB con = new ConexionDB();
		con.conectar(); 
	}

	@FXML
	void login() {

		String user,pass;

		user = Usuario.getText();
		if(passwordvisible) {

			pass = passfd.getText();

		}else {

			pass = this.pass.getText();

		}
		Usuario us = new Usuario(user,pass);

		lb_LoginFallado.setText("");

		PauseTransition pause = new PauseTransition(Duration.millis(500));
		pause.setOnFinished(event -> {
			try {

				us.LoginSuccesfull();
				onLogin(us);

			} catch (LoginFalladoExcepcion e) {

				lb_LoginFallado.setText(e.mensajeError());

			}
		});
		pause.play();



	}
	@FXML
	void registrarse(){

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
	@FXML
	void MostrarOcultarPass(ActionEvent event) {

		passwordvisible = !passwordvisible;

		application.Usuario.asignarMostrarOcultar(passwordvisible, pass, passfd, btn_password);

	}

	void onLogin(Usuario user) {


		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Inicio.fxml"));
			Parent root = loader.load();

			InicioController inicioController = loader.getController();
			inicioController.setUsuario(user); // ¡Aquí pasamos el usuario!

			Stage stageanterior = (Stage) loginButton.getScene().getWindow();
			stageanterior.close();

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("Mesa de Poker");
			stage.setResizable(false);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();

		}
		catch (Exception e) {

			e.printStackTrace();

		}

	}



}
