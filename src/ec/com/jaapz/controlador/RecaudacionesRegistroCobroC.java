package ec.com.jaapz.controlador;

import ec.com.jaapz.modelo.PlanillaDetalle;
import ec.com.jaapz.modelo.Planilla;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.ControllerHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class RecaudacionesRegistroCobroC {
	Planilla planillaSeleccionada = new Planilla();
	@FXML private TextField txtNumMedidor;
	@FXML private TextField txtCedula;
	@FXML private TextField txtNumFactura;
	@FXML private TextField txtIdCuenta;
	@FXML private TextField txtCliente;
	@FXML private TextField txtDireccion;
	@FXML private TextField txtConsumo;
	@FXML private TextField txtSaldoAnt;
	@FXML private TextField txtTotalPagar;
	@FXML private TextField txtFechaUltPago;
	@FXML private TextField txtUltimoPago;
	@FXML private TextField txtMesMora;
	@FXML private TextField txtLectAnt;
	@FXML private TextField txtLectActual;
	@FXML private Button btnBuscaFacturas;
	
	private @FXML TableView<PlanillaDetalle> tvDatos;
	
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize(){
		
	}	
	
	public void buscarPlanilla() {
		try{
			helper.abrirPantallaModal("/recaudaciones/RecaudacionesListadoPlanillasEmitidas.fxml","Facturas Sin Cobrar", Context.getInstance().getStage());
			if (Context.getInstance().getPlanillas() != null) {
				planillaSeleccionada = Context.getInstance().getPlanillas();
				llenarDatos(planillaSeleccionada);
				Context.getInstance().setPlanillas(null);;
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	void llenarDatos(Planilla datoSeleccionado){
		try {
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			if(datoSeleccionado.getCuentaCliente().getMedidor().getCodigo() == null)
				txtNumMedidor.setText("");
			else
				txtNumMedidor.setText(datoSeleccionado.getCuentaCliente().getMedidor().getCodigo());

			if(datoSeleccionado.getIdPlanilla() == null)
				txtNumFactura.setText("");
			else
				txtNumFactura.setText(String.valueOf(datoSeleccionado.getIdPlanilla()));
			
			if(datoSeleccionado.getCuentaCliente().getIdCuenta() == null)
				txtIdCuenta.setText("");
			else
				txtIdCuenta.setText(String.valueOf(datoSeleccionado.getCuentaCliente().getIdCuenta()));
			
			if(datoSeleccionado.getCuentaCliente().getCliente().getCedula() == null)
				txtCedula.setText("");
			else
				txtCedula.setText(datoSeleccionado.getCuentaCliente().getCliente().getCedula());
			
			if(datoSeleccionado.getCuentaCliente().getCliente().getNombres() + " " + datoSeleccionado.getCuentaCliente().getCliente().getApellidos() == null)
				txtCliente.setText("");
			else
				txtCliente.setText(datoSeleccionado.getCuentaCliente().getCliente().getNombres() + " " + datoSeleccionado.getCuentaCliente().getCliente().getApellidos());
			
			if(datoSeleccionado.getCuentaCliente().getDireccion() == null)
				txtDireccion.setText("");
			else
				txtDireccion.setText(datoSeleccionado.getCuentaCliente().getDireccion());
			
			if(datoSeleccionado.getConsumo() == null)
				txtConsumo.setText("");
			else
				txtConsumo.setText(String.valueOf(datoSeleccionado.getConsumo()*datoSeleccionado.getCuentaCliente().getCategoria().getValorM3()));
			/*
			if(datoSeleccionado.getTotalPagar()	.equals(null))
				txtTotalPagar.setText("");
			else
				txtTotalPagar.setText(String.valueOf(datoSeleccionado.getTotalPagar()));
			*/
			if(datoSeleccionado.getLecturaAnterior() == null)
				txtLectAnt.setText("");
			else
				txtLectAnt.setText(String.valueOf(datoSeleccionado.getLecturaAnterior()));
			
			if(datoSeleccionado.getLecturaActual() == null)
				txtLectActual.setText("");
			else
				txtLectActual.setText(String.valueOf(datoSeleccionado.getLecturaActual()));
			
			if (!datoSeleccionado.getPlanillaDetalles().isEmpty()) {
				ObservableList<PlanillaDetalle> detallesPlanilla = FXCollections.observableArrayList();
				detallesPlanilla.setAll(datoSeleccionado.getPlanillaDetalles()); 
				
				TableColumn<PlanillaDetalle, String> idColum = new TableColumn<>("Id");
				idColum.setMinWidth(10);
				idColum.setPrefWidth(80);
				idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PlanillaDetalle, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<PlanillaDetalle, String> param) {
						return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdPlanillaDet()));
					}
				});
				
				TableColumn<PlanillaDetalle, String> idPlaniCabColum = new TableColumn<>("Id Planilla Cab");
				idPlaniCabColum.setMinWidth(10);
				idPlaniCabColum.setPrefWidth(80);
				idPlaniCabColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PlanillaDetalle, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<PlanillaDetalle, String> param) {
						return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getPlanilla().getIdPlanilla()));
					}
				});
				
				TableColumn<PlanillaDetalle, String> descripcionColum = new TableColumn<>("Descripción");
				descripcionColum.setMinWidth(10);
				descripcionColum.setPrefWidth(150);
				descripcionColum.setCellValueFactory(new PropertyValueFactory<PlanillaDetalle, String>("descripcion"));
				
				TableColumn<PlanillaDetalle, String> subtotalColum = new TableColumn<>("Subtotal");
				subtotalColum.setMinWidth(10);
				subtotalColum.setPrefWidth(100);
				subtotalColum.setCellValueFactory(new PropertyValueFactory<PlanillaDetalle, String>("subtotal"));
				
				tvDatos.getColumns().addAll(idColum, idPlaniCabColum, descripcionColum, subtotalColum);
				tvDatos.setItems(detallesPlanilla);
				
				//recuperar total
				/*double t
				for(JunDetallePlanilla det : tvDatos.getItems()) {
					
				}*/
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}	
}