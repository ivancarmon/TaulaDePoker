package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import application.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

	}

	@FXML
	void Salir(ActionEvent event) {

		System.exit(0);

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
