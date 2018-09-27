package ec.com.jaapz.main;

import ec.com.jaapz.controlador.InicioSesionC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
public class LaunchSystem extends Application {
	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader root = new FXMLLoader();
			root.setLocation(getClass().getResource("/principal/InicioSesion.fxml"));
			AnchorPane page = (AnchorPane) root.load();
			Scene scene = new Scene(page);
			stage.setScene(scene);
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Inicio de Sesion");
			InicioSesionC inicio = (InicioSesionC) root.getController();
			inicio.setDialogStage(stage);
			stage.setMaximized(true);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}