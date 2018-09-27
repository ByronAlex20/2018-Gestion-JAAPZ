package ec.com.jaapz.controlador;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import ec.com.jaapz.modelo.Barrio;
import ec.com.jaapz.modelo.BarrioDAO;
import ec.com.jaapz.modelo.Categoria;
import ec.com.jaapz.modelo.CategoriaDAO;
import ec.com.jaapz.modelo.Inspeccion;
import ec.com.jaapz.modelo.InspeccionDAO;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.ControllerHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ClienteInspeccionEditarC {
	@FXML private TextField txtTelefonoIns;
	@FXML private TextField txtGeneroIns;
	@FXML private Button btnGrabarIns;
	@FXML private TextField txtEstadoCli;
	@FXML private ComboBox<Categoria> cboUsoMedidor;
	@FXML private TextField txtApellidosIns;
	@FXML private Button btnSalir;
	@FXML private TextField txtNombresIns;
	@FXML private TextField txtReferenciaIns;
	@FXML private TextField txtEstadoIns;
	@FXML private TextField txtCedulaIns;
	@FXML private DatePicker dtpFechaIns;
	@FXML private ComboBox<Barrio> cboBarrio;
	Inspeccion inspeccionSeleccionada = new Inspeccion();
	BarrioDAO barrioDAO = new BarrioDAO();
	CategoriaDAO categoriaDAO = new CategoriaDAO();
	ControllerHelper helper = new ControllerHelper();
	InspeccionDAO inspeccionDAO = new InspeccionDAO();
	public void initialize() {
		try {
			txtApellidosIns.setEditable(false);
			txtTelefonoIns.setEditable(false);
			txtGeneroIns.setEditable(false);
			txtNombresIns.setEditable(false);
			txtEstadoIns.setEditable(false);
			txtEstadoCli.setEditable(false);
			txtCedulaIns.setEditable(false);
			llenarCombosIns();
			llenarComboBarrio();
			cboUsoMedidor.getSelectionModel().select(-1);
			cboBarrio.getSelectionModel().select(-1);
			if(Context.getInstance().getInspeccion() != null) {
				inspeccionSeleccionada = Context.getInstance().getInspeccion();
				Context.getInstance().setInspeccion(null);
				txtApellidosIns.setText(inspeccionSeleccionada.getCliente().getApellidos());
				txtTelefonoIns.setText(inspeccionSeleccionada.getCliente().getTelefono());
				txtGeneroIns.setText(inspeccionSeleccionada.getCliente().getGenero());
				txtNombresIns.setText(inspeccionSeleccionada.getCliente().getNombres());
				txtEstadoIns.setText(inspeccionSeleccionada.getEstadoInspeccion());
				if(inspeccionSeleccionada.getCliente().getEstado().equals("A"))
					txtEstadoCli.setText("Activo");
				else
					txtEstadoCli.setText("Inactivo");
				txtCedulaIns.setText(inspeccionSeleccionada.getCliente().getCedula());
				if(categoriaDAO.getCategoria(inspeccionSeleccionada.getUsoMedidor()).size() > 0)
					cboUsoMedidor.getSelectionModel().select(categoriaDAO.getCategoria(inspeccionSeleccionada.getUsoMedidor()).get(0));
				else
					cboUsoMedidor.getSelectionModel().select(-1);
				cboBarrio.getSelectionModel().select(inspeccionSeleccionada.getBarrio());
				txtReferenciaIns.setText(inspeccionSeleccionada.getReferencia());
				dtpFechaIns.setValue(inspeccionSeleccionada.getFecha().toLocalDateTime().toLocalDate());
			}
			txtReferenciaIns.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtReferenciaIns.getText().toUpperCase();
					txtReferenciaIns.setText(cadena);
				}
			});
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	private void llenarCombosIns() {
		try{
			cboUsoMedidor.setPromptText("Seleccione Uso Medidor");
			List<Categoria> listaCategoria;
			listaCategoria = categoriaDAO.getListaCategorias("");
			ObservableList<Categoria> datos = FXCollections.observableArrayList();

			datos.addAll(listaCategoria);
			cboUsoMedidor.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	private void llenarComboBarrio(){
		try{
			cboBarrio.setPromptText("Seleccione Barrio");
			List<Barrio> listaBarrio;
			listaBarrio = barrioDAO.getListaBarriosActivos();
			ObservableList<Barrio> datos = FXCollections.observableArrayList();

			datos.addAll(listaBarrio);
			cboBarrio.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public void grabarIns() {
		try {
			if(dtpFechaIns.getValue() == null) {
				helper.mostrarAlertaAdvertencia("Debe registrar Fecha de la Inspecci�n", Context.getInstance().getStage());
				return;
			}
			if(cboUsoMedidor.getSelectionModel().getSelectedIndex() == -1) {
				helper.mostrarAlertaAdvertencia("Debe Seleccionar el uso del medidor", Context.getInstance().getStage());
				return;
			}
			if(cboBarrio.getSelectionModel().getSelectedIndex() == -1) {
				helper.mostrarAlertaAdvertencia("Debe Seleccionar el Barrio del Cliente", Context.getInstance().getStage());
				return;
			}
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				inspeccionSeleccionada.setBarrio(cboBarrio.getSelectionModel().getSelectedItem());
				inspeccionSeleccionada.setUsoMedidor(cboUsoMedidor.getSelectionModel().getSelectedItem().getDescripcion());
				inspeccionSeleccionada.setReferencia(txtReferenciaIns.getText());
				Date date = Date.from(dtpFechaIns.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
				Timestamp fecha = new Timestamp(date.getTime());
				inspeccionSeleccionada.setFecha(fecha);
				inspeccionDAO.getEntityManager().getTransaction().begin();
				inspeccionDAO.getEntityManager().merge(inspeccionSeleccionada);
				inspeccionDAO.getEntityManager().getTransaction().commit();
				Context.getInstance().getStageModal().close();
				helper.mostrarAlertaInformacion("Datos Grabados con exito", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void salir() {
		try {
			Context.getInstance().getStageModal().close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
