package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import application.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class InicioController{

	@FXML
	private Button btn_jugar;

	@FXML
	private Button btn_salir;

	@FXML
	private ImageView img_perfil;

	@FXML ImageView img_fichas;

	@FXML
	private Label lb_fichas;

	public Usuario us;



	@FXML
	void Jugar(ActionEvent event) {
	    try {

	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Juego.fxml"));
	        Parent root = loader.load();

	        JuegoController juegoController = loader.getController();
	        juegoController.setUsuario(this.us);

	       
	        Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stageActual.close();

	        
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setTitle("Mesa de Poker - Jugando");
	        stage.setResizable(true);
	        scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
	        stage.setScene(scene);
	        stage.show();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		
	}

	@FXML
	void Salir(ActionEvent event) {

		System.exit(0);

	}
	
	@FXML
	void Ajustes(ActionEvent event) {

		

	}




	public void setUsuario(Usuario usuario) {
		this.us = usuario;
		cargarDatos(); 
	}

	private void cargarDatos() {
		Image img = new Image("/vista/img/sinFoto.png");

		lb_fichas.setText(String.valueOf(us.getFichas()));
		if(us.getImage() == null) {
			img_perfil.setImage(img);
		}
		else {
			
			img_perfil.setImage(us.getImage());
			
		}

	}


}
