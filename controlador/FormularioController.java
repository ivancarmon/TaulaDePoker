package controlador;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;

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
    private TextField fd_confirmpassword;
    
    @FXML
    private PasswordField pd_password;
    
    @FXML
    private PasswordField pd_confirmpassword;

    
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
		
		asignarBoton(btn_confirmpassword);
		asignarBoton(btn_password);

    }
    
    public void asignarBoton(Button b) {
    	
        // Crear un ImageView y asignar la imagen
        Image image = new Image(getClass().getResourceAsStream("/vista/img/ojoabierto.png"));
        ImageView imageView = new ImageView(image);
        
        // Ajustar el tamaño de la imagen si es necesario
        imageView.setFitHeight(17);
        imageView.setFitWidth(17);
        
        // Asignar la imagen al botón
        b.setGraphic(imageView);
    	asignarBoton(b, "/vista/img/ojoabierto.png");
    	
    }
    public void asignarBoton(Button b, String ruta) {
    	
        // Crear un ImageView y asignar la imagen
        Image image = new Image(getClass().getResourceAsStream(ruta));
        ImageView imageView = new ImageView(image);
        
        // Ajustar el tamaño de la imagen si es necesario
        imageView.setFitHeight(17);
        imageView.setFitWidth(17);
        
        // Asignar la imagen al botón
        b.setGraphic(imageView);
    	
    }
    
    public void asignarMostrarOcultar(ActionEvent event) {
    	
    	
        passwordvisible = !passwordvisible;

   
		TextField passfd, passfd1;
    	passfd = fd_password; 
    	passfd1 = fd_confirmpassword;
		PasswordField pass;
		PasswordField pass1;
    	pass = pd_password; 
    	pass1 = pd_confirmpassword; 
        passfd.setVisible(passwordvisible);
        passfd1.setVisible(passwordvisible);
        passfd.setManaged(passwordvisible);
        passfd1.setManaged(passwordvisible);
        pass.setVisible(!passwordvisible);
        pass1.setVisible(!passwordvisible);
        pass.setManaged(!passwordvisible);
        pass1.setManaged(!passwordvisible);

        if (passwordvisible) {
        	 
        	asignarBoton(btn_confirmpassword, "/vista/img/ojocerrado.png");
        	asignarBoton(btn_password, "/vista/img/ojocerrado.png");
            passfd.setText(pass.getText());
            passfd1.setText(pass1.getText());
            
        } else {
        	asignarBoton(btn_confirmpassword);
        	asignarBoton(btn_password);
            pass.setText(passfd.getText());
            pass1.setText(passfd1.getText());
        }

		
    	
    	
    	
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
			img_foto.setImage( image );
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
