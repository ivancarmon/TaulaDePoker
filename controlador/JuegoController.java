package controlador;

import java.util.Random;

import application.*;
import enumerados.Ronda;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class JuegoController {

	
	private Juego j;
	
	private Usuario us;

	@FXML
	private ImageView cardsplayer1;

	@FXML
	private ImageView cardsplayer2;

	@FXML
	private ImageView flop1;

	@FXML
	private ImageView flop2;

	@FXML
	private ImageView flop3;

	@FXML
	private ImageView img_perfil;

	@FXML
	private ImageView river;

	@FXML
	private ImageView turn;

	@FXML
	private Label fichas;


	public void setUsuario(Usuario us) {

		this.us = us;
		cargarDatos();
		this.j = new Juego();
	}

	private void cargarDatos() {
		Image img = new Image("/vista/img/sinFoto.png");


		if(us.getImage() == null) {

			img_perfil.setImage(img);

		}
		else {

			img_perfil.setImage(us.getImage());

		}

		fichas.setText(String.valueOf(us.getFichas()));

	}

	public void sacarCartas(Ronda r) {
		
		
		
		switch (r) {
			case Preflop: {
				
				Image img = new Image(j.baraja.getFirst() + ".svg");
				cardsplayer1.setImage(img);
				j.baraja.removeFirst();
				img = new Image(j.baraja.getFirst() + ".svg");
				cardsplayer2.setImage(img);
				j.baraja.removeFirst();
				
				
			}
			case Flop: {

			}
			case Turn: {
				
			}
			case River:{
			
			}
			
		}


		}

	}
