package ec.com.jaapz.controlador;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import ec.com.jaapz.modelo.Anio;
import ec.com.jaapz.modelo.AnioDAO;
import ec.com.jaapz.modelo.AperturaLectura;
import ec.com.jaapz.modelo.AperturaLecturaDAO;
import ec.com.jaapz.modelo.CuentaCliente;
import ec.com.jaapz.modelo.CuentaClienteDAO;
import ec.com.jaapz.modelo.Me;
import ec.com.jaapz.modelo.MeDAO;
import ec.com.jaapz.modelo.Planilla;
import ec.com.jaapz.modelo.PlanillaDetalle;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.ControllerHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class LecturasAperturaCierreC {

    @FXML private ComboBox<Me> cboMes;
    @FXML private TextField txtTotalClientes;
    @FXML private Button btnCerrarCiclo;
    @FXML private Button btnQuitarAsig;
    @FXML private TextField txtMes;
    @FXML private TabPane tpAsignaciones;
    @FXML private TableView<?> tvNuevosAsig;
    @FXML private Button btnGrabarApertura;
    @FXML private TableView<?> tvAsignados;
    @FXML private Button btnGrabarAsig;
    @FXML private Button btnAsignar;
    @FXML private TextField txtClientesRegistrados;
    @FXML private TableView<AperturaLectura> tvAperturas;
    @FXML private TableView<?> tvPersonalLectura;
    @FXML private Tab tpRealizadas;
    @FXML private TextField txtAnio;
    @FXML private Tab tpNuevas;
    @FXML private ComboBox<Anio> cboAnio;
    @FXML private TextField txtClientesFaltantes;
    @FXML private Button btnImprimirAsig;
    @FXML private TextField txtFecha;
    
    MeDAO mesDAO = new MeDAO();
    AnioDAO anioDAO = new AnioDAO();
	ControllerHelper helper = new ControllerHelper();
	CuentaClienteDAO cuentaDAO = new CuentaClienteDAO();
	AperturaLecturaDAO aperturaDAO = new AperturaLecturaDAO();
	AperturaLectura aperturaActual = new AperturaLectura();
	
    public void initialize() {
    	try {
    		cargarCombos();
    		recuperarDatos();
    		obtenerCicloActual();
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
    @SuppressWarnings("unchecked")
	void recuperarDatos() {
		try {
			tvAperturas.getItems().clear();
			tvAperturas.getColumns().clear();
			List<AperturaLectura> listaPrecios;
			ObservableList<AperturaLectura> datos = FXCollections.observableArrayList();
			listaPrecios = aperturaDAO.getListaAperturas();
			datos.setAll(listaPrecios);
			
			//llenar los datos en la tabla
			TableColumn<AperturaLectura, String> idColum = new TableColumn<>("Id Apertura");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(90);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AperturaLectura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AperturaLectura, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdApertura()));
				}
			});
			
			TableColumn<AperturaLectura, String> mesColum = new TableColumn<>("Mes");
			mesColum.setMinWidth(10);
			mesColum.setPrefWidth(250);
			mesColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AperturaLectura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AperturaLectura, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getMe().getDescripcion()));
				}
			});

			TableColumn<AperturaLectura, String> anioColum = new TableColumn<>("Año");
			anioColum.setMinWidth(10);
			anioColum.setPrefWidth(90);
			anioColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AperturaLectura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AperturaLectura, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getAnio().getDescripcion()));
				}
			});
			TableColumn<AperturaLectura, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(90);
			estadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AperturaLectura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AperturaLectura, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getEstadoApertura());
				}
			});
			
			tvAperturas.getColumns().addAll(idColum,mesColum,anioColum,estadoColum);
			tvAperturas.setItems(datos);

		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
    
    private void cargarCombos() {
    	try {
    		ObservableList<Me> listaMeses = FXCollections.observableArrayList();
    		List<Me> datMes = mesDAO.getListaMeses();
    		listaMeses.addAll(datMes);
			cboMes.setItems(listaMeses);
			
			ObservableList<Anio> listaAnios = FXCollections.observableArrayList();
			List<Anio> datAn = anioDAO.getListaAnios();
			listaAnios.setAll(datAn);
			cboAnio.setItems(listaAnios);
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
    //faltan validaciones ------------------------------
    public void grabarApertura(ActionEvent event) {
    	try {
    		Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				//declarar el objeto a grabar
				AperturaLectura aperturaGrabar = new AperturaLectura();
				aperturaGrabar.setEstado("A");
				aperturaGrabar.setEstadoApertura("EN PROCESO");
				Date date = new Date();
				Timestamp fecha = new Timestamp(date.getTime());
				aperturaGrabar.setFecha(fecha);
				aperturaGrabar.setAnio(cboAnio.getSelectionModel().getSelectedItem());
				aperturaGrabar.setIdApertura(null);
				aperturaGrabar.setMe(cboMes.getSelectionModel().getSelectedItem());
				aperturaGrabar.setObservacion("");
				
				//aperturar todos los clientes 
				List<CuentaCliente> listaCuentasActivas = new ArrayList<CuentaCliente>();
				listaCuentasActivas = cuentaDAO.getListaCuentasActivas();
				aperturaDAO.getEntityManager().getTransaction().begin();
				//recorrer las cuentas para asignar las aperturas
				for(CuentaCliente cuentas : listaCuentasActivas) {
					List<Planilla> listaAdd = new ArrayList<Planilla>();
					Planilla planilla = new Planilla(); // planilla nueva para todos los clientes
					planilla.setIdPlanilla(null);
					planilla.setFecha(fecha);
					planilla.setConvenio("NO");
					planilla.setConsumo(0);
					//falta programar--------------------------------------------------------
					planilla.setLecturaAnterior(0);
					planilla.setLecturaActual(0);
					planilla.setEstado("A");
					//enlace entre planilla y apertura
					planilla.setAperturaLectura(aperturaGrabar);
					listaAdd.add(planilla);
					aperturaGrabar.setPlanillas(listaAdd);
					//enlace entre cliente y planilla
					planilla.setCuentaCliente(cuentas);
					cuentas.setPlanillas(listaAdd);
					
					//enlace entre detalle de planilla y planilla
					PlanillaDetalle detallePlanilla = new PlanillaDetalle();
					detallePlanilla.setIdPlanillaDet(null);
					detallePlanilla.setDescripcion("Por consumo del mes de: " + cboMes.getSelectionModel().getSelectedItem().getDescripcion() + " del año: " + txtAnio.getText());
					detallePlanilla.setEstado("A");
					detallePlanilla.setCantidad(0);
					detallePlanilla.setPlanilla(planilla);
					List<PlanillaDetalle> det = new ArrayList<PlanillaDetalle>();
					det.add(detallePlanilla);
					planilla.setPlanillaDetalles(det);
					
					aperturaDAO.getEntityManager().persist(aperturaGrabar);
				}
				aperturaDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
				recuperarDatos();
				obtenerCicloActual();
			}
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    		aperturaDAO.getEntityManager().getTransaction().rollback();
    	}
    }

    /*
     * para el cierre de ciclo ------------------------------------------------------------------------------------------------------------------------
     */
    public void cerrarCiclo(ActionEvent event) {
    	try {
    		Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Se procedera a cerrar el proceso de facturación, desea continuar??",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				aperturaActual.setEstadoApertura("REALIZADO");
				aperturaDAO.getEntityManager().getTransaction().begin();
				aperturaDAO.getEntityManager().merge(aperturaActual);
				aperturaDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Cierre ejecutado con exito!!!!", Context.getInstance().getStage());
				aperturaActual = null;
				recuperarDatos();
				obtenerCicloActual();
			}
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    		aperturaDAO.getEntityManager().getTransaction().rollback();
    	}
    }

    private void obtenerCicloActual() {
    	try {
    		int numeroTomados = 0;
    		int numApertura = 0;
    		AperturaLectura apertura = new AperturaLectura();
    		numApertura = aperturaDAO.getListaAperturasEnProceso().size(); 
    		if(numApertura > 0) {
    			apertura = aperturaDAO.getListaAperturasEnProceso().get(0);
    			aperturaActual = apertura;
        		txtAnio.setText(String.valueOf(apertura.getAnio().getDescripcion()));
        		txtMes.setText(String.valueOf(apertura.getMe().getDescripcion()));
        		txtFecha.setText(String.valueOf(apertura.getFecha()));
        		
        		txtTotalClientes.setText(String.valueOf(apertura.getPlanillas().size()));
        		for(Planilla planilla : apertura.getPlanillas()) {
        			if(planilla.getConsumo() > 0)
        				numeroTomados = numeroTomados + 1;
        		}
        		txtClientesRegistrados.setText(String.valueOf(numeroTomados));
        		txtClientesFaltantes.setText(String.valueOf(apertura.getPlanillas().size() - numeroTomados));	
    		}else {
    			aperturaActual = null;
        		txtAnio.setText("");
        		txtMes.setText("");
        		txtFecha.setText("");
        		txtTotalClientes.setText("");
        		txtClientesRegistrados.setText("");
        		txtClientesFaltantes.setText("");
    		}
    		
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
    public void grabarAsig(ActionEvent event) {

    }

    public void imprimirAsignaciones(ActionEvent event) {

    }

    public void asignarBarrios(ActionEvent event) {

    }

    public void quitarBarrios(ActionEvent event) {

    }
}
