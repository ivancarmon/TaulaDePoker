package excepciones;

import java.util.ArrayList;

public class RegistroFalladoException extends Exception {

	public String campo;
	private boolean Plural;

	public RegistroFalladoException(String s, boolean t) {

		this.campo = s;
		this.Plural = t;

	}


	public String mensajeError() {
		
		
		return String.format("%s %s %s", Plural ? "Los campos": "El campo", this.campo, Plural ? "Estan vacios": "Esta vacio" );
	}

}
