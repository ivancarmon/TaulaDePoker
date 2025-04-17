package excepciones;

public class LoginFalladoExcepcion extends Exception {
	
	
	public LoginFalladoExcepcion() {
		
		
		
	}
	
	public String mensajeError() {
		
		
		
		return "La contraseña no es correcta o el usuario no existe";
		
	}
	
	
}
