package ec.com.jaapz.controlador;

import java.util.List;

import ec.com.jaapz.modelo.CuentaCliente;
import ec.com.jaapz.modelo.Planilla;
import ec.com.jaapz.modelo.PlanillaDAO;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.ControllerHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class RecaudacionesPlanillasEmitidasC {
	@FXML private TextField txtBuscar;
	@FXML private TableView<CuentaCliente> tvDatos;
	ControllerHelper helper = new ControllerHelper();
	
	PlanillaDAO planillaDao = new PlanillaDAO();
	
	
	public void initialize(){
		try {			
			llenarDatos("");
			tvDatos.setRowFactory(tv -> {
				 TableRow<CuentaCliente> row = new TableRow<>();
		            row.setOnMouseClicked(event -> {
		                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		                	if(tvDatos.getSelectionModel().getSelectedItem() != null){
		                		//Context.getInstance().setCuentaClientes(tvDatos.getSelectionModel().getSelectedItem());
		    					Context.getInstance().getStageModal().close();
		    				}
		                }
		            });
	            /*TableRow<Planilla> row = new TableRow<>();
	            row.setOnMouseClicked(event -> {
	                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
	                	if(tvDatos.getSelectionModel().getSelectedItem() != null){
	    					Context.getInstance().setPlanillas(tvDatos.getSelectionModel().getSelectedItem());
	    					Context.getInstance().getStageModal().close();
	    				}
	                }
	            });*/
	            return row ;
	        });
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void buscarCliente() {
		llenarDatos(txtBuscar.getText());
	}	
	@SuppressWarnings("unchecked")
	void llenarDatos(String patron) {
		try{
			tvDatos.getColumns().clear();
			List<CuentaCliente> listaCuentas;
			/*
			if(Context.getInstance().getIdPerfil() == 1) {
				listaCuentas = planillaDao.getListaPlanilla(patron);
			}else {
				listaCuentas = planillaDao.getListaPlanillaPerfil(patron);
			}*/
			ObservableList<Planilla> datosPlanilla = FXCollections.observableArrayList();
			//datosPlanilla.setAll(listaCuentas);

			//llenar los datos en la tabla
			TableColumn<Planilla, String> idColum = new TableColumn<>("Id");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(40);
			idColum.setCellValueFactory(new PropertyValueFactory<Planilla, String>("idPlanilla"));
			
			TableColumn<Planilla, String> cedulaColum = new TableColumn<>("Cédula");
			cedulaColum.setMinWidth(10);
			cedulaColum.setPrefWidth(80);
			cedulaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getCuentaCliente().getCliente().getCedula()));
				}
			});
			
			TableColumn<Planilla, String> clienteColum = new TableColumn<>("Cliente");
			clienteColum.setMinWidth(10);
			clienteColum.setPrefWidth(80);
			clienteColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getCuentaCliente().getCliente().getNombres() + " " + param.getValue().getCuentaCliente().getCliente().getApellidos()));
				}
			});
			
			TableColumn<Planilla, String> medidorColum = new TableColumn<>("Medidor");
			medidorColum.setMinWidth(10);
			medidorColum.setPrefWidth(80);
			medidorColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getCuentaCliente().getMedidor().getCodigo()));
				}
			});
			
			TableColumn<Planilla, String> direccionColum = new TableColumn<>("Dirección");
			direccionColum.setMinWidth(10);
			direccionColum.setPrefWidth(80);
			direccionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getCuentaCliente().getDireccion()));
				}
			});
			
			TableColumn<Planilla, String> totalColum = new TableColumn<>("Valor a Pagar");
			totalColum.setMinWidth(10);
			totalColum.setPrefWidth(80);
			totalColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getTotalPagar()));
				}
			});
			
			TableColumn<Planilla, String> canceladoColum = new TableColumn<>("Cancelado");
			canceladoColum.setMinWidth(10);
			canceladoColum.setPrefWidth(40);
			canceladoColum.setCellValueFactory(new PropertyValueFactory<Planilla, String>("cancelado"));
			
			//tvDatos.getColumns().addAll(idColum,  cedulaColum, clienteColum, medidorColum, direccionColum, totalColum, canceladoColum);
			//tvDatos.setItems(datosPlanilla);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	
	/*void llenarDatos(String patron) {
		try{
			tvDatos.getColumns().clear();
			List<Planilla> listaPlanillas;
			if(Context.getInstance().getIdPerfil() == 1) {
				listaPlanillas = planillaDao.getListaPlanilla(patron);
			}else {
				listaPlanillas = planillaDao.getListaPlanillaPerfil(patron);
			}
			ObservableList<Planilla> datosPlanilla = FXCollections.observableArrayList();
			datosPlanilla.setAll(listaPlanillas);

			//llenar los datos en la tabla
			TableColumn<Planilla, String> idColum = new TableColumn<>("Id");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(40);
			idColum.setCellValueFactory(new PropertyValueFactory<Planilla, String>("idPlanilla"));
			
			TableColumn<Planilla, String> cedulaColum = new TableColumn<>("Cédula");
			cedulaColum.setMinWidth(10);
			cedulaColum.setPrefWidth(80);
			cedulaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getCuentaCliente().getCliente().getCedula()));
				}
			});
			
			TableColumn<Planilla, String> clienteColum = new TableColumn<>("Cliente");
			clienteColum.setMinWidth(10);
			clienteColum.setPrefWidth(80);
			clienteColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getCuentaCliente().getCliente().getNombres() + " " + param.getValue().getCuentaCliente().getCliente().getApellidos()));
				}
			});
			
			TableColumn<Planilla, String> medidorColum = new TableColumn<>("Medidor");
			medidorColum.setMinWidth(10);
			medidorColum.setPrefWidth(80);
			medidorColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getCuentaCliente().getMedidor().getCodigo()));
				}
			});
			
			TableColumn<Planilla, String> direccionColum = new TableColumn<>("Dirección");
			direccionColum.setMinWidth(10);
			direccionColum.setPrefWidth(80);
			direccionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getCuentaCliente().getDireccion()));
				}
			});
			
			TableColumn<Planilla, String> totalColum = new TableColumn<>("Valor a Pagar");
			totalColum.setMinWidth(10);
			totalColum.setPrefWidth(80);
			totalColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getTotalPagar()));
				}
			});
			
			TableColumn<Planilla, String> canceladoColum = new TableColumn<>("Cancelado");
			canceladoColum.setMinWidth(10);
			canceladoColum.setPrefWidth(40);
			canceladoColum.setCellValueFactory(new PropertyValueFactory<Planilla, String>("cancelado"));
			
			tvDatos.getColumns().addAll(idColum,  cedulaColum, clienteColum, medidorColum, direccionColum, totalColum, canceladoColum);
			tvDatos.setItems(datosPlanilla);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}*/
}