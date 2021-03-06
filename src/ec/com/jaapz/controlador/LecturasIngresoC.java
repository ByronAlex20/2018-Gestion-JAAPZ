package ec.com.jaapz.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ec.com.jaapz.modelo.AnioDAO;
import ec.com.jaapz.modelo.AperturaLectura;
import ec.com.jaapz.modelo.AperturaLecturaDAO;
import ec.com.jaapz.modelo.MeDAO;
import ec.com.jaapz.modelo.Planilla;
import ec.com.jaapz.modelo.PlanillaDetalle;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.ControllerHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.util.Callback;

public class LecturasIngresoC {
	@FXML
    private TableView<PlanillaDetalle> tvDatosLecturas;

    @FXML
    private Button btnGrabar;

    @FXML
    private Button btnEditar;
    @FXML
    private TextField txtMes;

    @FXML
    private TextField txtAnio;
    
    AperturaLecturaDAO aperturaDAO = new AperturaLecturaDAO();
    MeDAO mesDAO = new MeDAO();
    AnioDAO anioDAO = new AnioDAO();
    AperturaLectura aperturaActual = new AperturaLectura();
    ControllerHelper helper = new ControllerHelper();
    
    public void initialize() {
    	try {
    		cargarCiclo();
    		cargarClientes();
    		tvDatosLecturas.setEditable(true);
    	}catch(Exception ex){
    		System.out.println(ex.getMessage());
    	}
    }
    @SuppressWarnings("unchecked")
	private void cargarClientes() {
    	try {
			tvDatosLecturas.getItems().clear();
			tvDatosLecturas.getColumns().clear();
			List<PlanillaDetalle> listaDetalle = new ArrayList<PlanillaDetalle>();
			ObservableList<PlanillaDetalle> datos = FXCollections.observableArrayList();
			for(Planilla planilla : aperturaActual.getPlanillas()) {
				listaDetalle.add(planilla.getPlanillaDetalles().get(0));
			}
			datos.setAll(listaDetalle);
			
			//llenar los datos en la tabla
			TableColumn<PlanillaDetalle, String> medidorColum = new TableColumn<>("C�d. Medidor");
			medidorColum.setMinWidth(10);
			medidorColum.setPrefWidth(90);
			medidorColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PlanillaDetalle,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<PlanillaDetalle, String> param) {
					String medidor = "";
					if(param.getValue().getPlanilla().getCuentaCliente().getMedidor() != null)
						medidor = param.getValue().getPlanilla().getCuentaCliente().getMedidor().getCodigo();
					else
						medidor = "NO ASIGNADO";
					return new SimpleObjectProperty<String>(medidor);
				}
			});
			
			TableColumn<PlanillaDetalle, String> clienteColum = new TableColumn<>("Cliente");
			clienteColum.setMinWidth(10);
			clienteColum.setPrefWidth(200);
			clienteColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PlanillaDetalle,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<PlanillaDetalle, String> param) {
					String nombre = param.getValue().getPlanilla().getCuentaCliente().getCliente().getNombres();
					String apellido = param.getValue().getPlanilla().getCuentaCliente().getCliente().getApellidos();
					return new SimpleObjectProperty<String>(nombre + " " + apellido);
				}
			});

			TableColumn<PlanillaDetalle, String> antColum = new TableColumn<>("Lec.  Anterior");
			antColum.setMinWidth(10);
			antColum.setPrefWidth(90);
			antColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PlanillaDetalle,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<PlanillaDetalle, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getPlanilla().getLecturaAnterior()));
				}
			});
			TableColumn<PlanillaDetalle, String> actColum = new TableColumn<>("Lec. Actual");
			actColum.setMinWidth(10);
			actColum.setPrefWidth(90);
			actColum.setCellFactory(TextFieldTableCell.<PlanillaDetalle>forTableColumn());
			actColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PlanillaDetalle,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<PlanillaDetalle, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getPlanilla().getLecturaActual()));
				}
			});
			actColum.setOnEditCommit(
				    new EventHandler<CellEditEvent<PlanillaDetalle, String>>() {
				        @Override
				        public void handle(CellEditEvent<PlanillaDetalle, String> t) {
				            ((PlanillaDetalle) t.getTableView().getItems().get(
				                t.getTablePosition().getRow())
				                ).getPlanilla().setLecturaActual(Integer.parseInt(t.getNewValue()));
				        }
				    }
				);
			
			tvDatosLecturas.getColumns().addAll(medidorColum,clienteColum,antColum,actColum);
			tvDatosLecturas.setItems(datos);

		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
    }
    private void cargarCiclo() {
    	try {
    		int numApertura = 0;
    		AperturaLectura apertura = new AperturaLectura();
    		numApertura = aperturaDAO.getListaAperturasEnProceso().size(); 
    		if(numApertura > 0) {
    			apertura = aperturaDAO.getListaAperturasEnProceso().get(0);
    			aperturaActual = apertura;
        		txtAnio.setText(apertura.getAnio().getDescripcion());
        		txtMes.setText(apertura.getMe().getDescripcion());
    		}else {
        		txtAnio.setText("");
        		txtMes.setText("");
    		}
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
    public void editarLectura() {
    	
    }

    public void grabarLecturas() {
    	try {
    		Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				List<PlanillaDetalle> listaDetalle = tvDatosLecturas.getItems();
	    		aperturaDAO.getEntityManager().getTransaction().begin();
	    		for(PlanillaDetalle det : listaDetalle) {
	    			Double categoriaPrecio = det.getPlanilla().getCuentaCliente().getCategoria().getValorM3();
	    			Integer lecturaActual = det.getPlanilla().getLecturaActual();
	    			Integer lecturaAnterior = det.getPlanilla().getLecturaAnterior();
	    			Integer consumo = lecturaActual - lecturaAnterior;
	    			
	    			det.setCantidad(lecturaActual);
	    			det.getPlanilla().setConsumo(consumo);
	    			det.getPlanilla().setTotalPagar(consumo * categoriaPrecio);
	    			aperturaDAO.getEntityManager().merge(det);
	    		}
	    		aperturaDAO.getEntityManager().getTransaction().commit();
	    		helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
			}
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    		aperturaDAO.getEntityManager().getTransaction().rollback();
    	}
    }
}
