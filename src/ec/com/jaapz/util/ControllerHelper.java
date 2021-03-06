package ec.com.jaapz.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.controlsfx.control.Notifications;

import ec.com.jaapz.main.LaunchSystem;
import javafx.animation.FadeTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ControllerHelper {
	public void abrirPantallaModal(String uriVista, String titulo,Stage parent){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(LaunchSystem.class.getResource(uriVista));
			Parent page = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setTitle(titulo);
			stage.initOwner(parent);
			stage.initModality(Modality.APPLICATION_MODAL);
			Scene scene = new Scene(page);
			stage.setScene(scene);
			Context.getInstance().setStageModal(stage);
			stage.showAndWait();
		} catch(Exception e) {
			e.printStackTrace(); //Retorna Connection reset cuando demora mucho
		}
	}
	public void abrirPantallaPrincipal(String titulo,String uriVista,Stage stage) {
		try {
			Stage nuevo = new Stage();
			FXMLLoader root = new FXMLLoader();
			root.setLocation(getClass().getResource(uriVista));
			AnchorPane page = (AnchorPane) root.load();
			Scene scene = new Scene(page);
			nuevo.getIcons().add(new Image("/icon.png"));
			nuevo.setScene(scene);
			nuevo.setMaximized(true);
			nuevo.setTitle(titulo);
			nuevo.show();
			Context.getInstance().setStage(nuevo);
			stage.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Parent ventanaParent(String uriVista){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(LaunchSystem.class.getResource(uriVista));
			Parent page = (Parent) loader.load();
			return page;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return null;
		}
	}
	public void mostrarAlertaError(String mensaje,Stage stage)
	{
		Image img = new Image("/icon_error.png", 70, 70,false,false);
		Notifications not = Notifications.create()
				.title("Información")
				.text(mensaje)
				.graphic(new ImageView(img))
				.hideAfter(Duration.seconds(2))
				.position(Pos.BOTTOM_RIGHT);
		not.darkStyle();
		not.show();
	}

	public void mostrarAlertaInformacion(String mensaje,Stage stage)
	{
		Image img = new Image("/icon_confirm.png", 70, 70,false,false);
		Notifications not = Notifications.create()
				.title("Información")
				.text(mensaje)
				.graphic(new ImageView(img))
				.hideAfter(Duration.seconds(2))
				.position(Pos.BOTTOM_RIGHT);
		not.darkStyle();
		not.show();
	}
	public void mostrarAlertaAdvertencia(String mensaje,Stage stage)
	{
		Alert dialogoAlert = new Alert(AlertType.WARNING);
		dialogoAlert.setTitle("Advertencia");
		dialogoAlert.setContentText(mensaje);
		dialogoAlert.initOwner(stage);
		dialogoAlert.show();
	}
	public Optional<ButtonType> mostrarAlertaConfirmacion(String mensaje,Stage stage){
		Alert dialogoAlert = new Alert(AlertType.CONFIRMATION);
		dialogoAlert.setTitle("Confirmación");
		dialogoAlert.setHeaderText(null);
		dialogoAlert.initStyle(StageStyle.UTILITY);
		dialogoAlert.setContentText(mensaje);
		dialogoAlert.initOwner(stage);
		return dialogoAlert.showAndWait();
	}
	public void mostrarVentanaContenedor(String uriVista,AnchorPane ap_contenedor){
		try{
			ap_contenedor.getChildren().removeAll();
			FXMLLoader loader = new FXMLLoader(LaunchSystem.class.getResource(uriVista));
			AnchorPane page=(AnchorPane) loader.load();

			FadeTransition ft = new FadeTransition(Duration.millis(1000));
			ft.setNode(page);
			ft.setFromValue(0.1);
			ft.setToValue(1);
			ft.setCycleCount(1);
			ft.setAutoReverse(false);
			ft.play();
			AnchorPane.setBottomAnchor(page, 00.0);
			AnchorPane.setLeftAnchor(page, 00.0);
			AnchorPane.setTopAnchor(page, 00.0);
			AnchorPane.setRightAnchor(page, 00.0);
			ap_contenedor.getChildren().setAll(page);

		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public String encodeFileToBase64Binary(Image imagen) throws IOException{
		BufferedImage bImage = SwingFXUtils.fromFXImage(imagen, null);
		ByteArrayOutputStream s = new ByteArrayOutputStream();
		ImageIO.write(bImage, "png", s);
		byte[] res  = s.toByteArray();
		s.close(); //especially if you are using a different output stream.
		return Base64.encodeBase64URLSafeString(res);
	}
	public ImageView getImageFromBase64String(String imageDataString) throws IOException {
		byte[] imgByte = Base64.decodeBase64(imageDataString);
		InputStream in = new ByteArrayInputStream(imgByte);
		BufferedImage bf = ImageIO.read(in);
		WritableImage wr = null;
		if (bf != null) {
			wr = new WritableImage(bf.getWidth(), bf.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < bf.getWidth(); x++) {
				for (int y = 0; y < bf.getHeight(); y++) {
					pw.setArgb(x, y, bf.getRGB(x, y));
				}
			}
		}
		ImageView imView = new ImageView(wr);
		return imView;
	}
	public boolean validarDeCedula(String cedula) {
		boolean cedulaCorrecta = false;
		try {
			if (cedula.length() == 10) // ConstantesApp.LongitudCedula
			{
				int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
				if (tercerDigito < 6) {
					// Coeficientes de validación cédula
					// El decimo digito se lo considera dígito verificador
					int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
					int verificador = Integer.parseInt(cedula.substring(9,10));
					int suma = 0;
					int digito = 0;
					for (int i = 0; i < (cedula.length() - 1); i++) {
						digito = Integer.parseInt(cedula.substring(i, i + 1))* coefValCedula[i];
						suma += ((digito % 10) + (digito / 10));
					}

					if ((suma % 10 == 0) && (suma % 10 == verificador)) {
						cedulaCorrecta = true;
					}
					else if ((10 - (suma % 10)) == verificador) {
						cedulaCorrecta = true;
					} else {
						cedulaCorrecta = false;
					}
				} else {
					cedulaCorrecta = false;
				}
			} else {
				cedulaCorrecta = false;
			}
		} catch (NumberFormatException nfe) {
			cedulaCorrecta = false;
		} catch (Exception err) {
			System.out.println("Una excepcion ocurrio en el proceso de validadcion");
			cedulaCorrecta = false;
		}

		if (!cedulaCorrecta) {
			System.out.println("La Cédula ingresada es Incorrecta");
		}
		return cedulaCorrecta;
	}
}

