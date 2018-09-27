package ec.com.jaapz.controlador;

import java.util.ArrayList;
import java.util.List;

import ec.com.jaapz.modelo.Inspeccion;
import ec.com.jaapz.modelo.InspeccionDAO;
import ec.com.jaapz.util.Context;
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

public class ClientesOrdePendienteC {
	@FXML TextField txtBuscar;
	@FXML TableView<Inspeccion> tvDatos;
	InspeccionDAO inspeccionDAO = new InspeccionDAO();
	List<Inspeccion> listadoInspecciones = new ArrayList<Inspeccion>();
	public void initialize() {
		listadoInspecciones = Context.getInstance().getListaInspecciones();
		//poner nuevamente a null
		Context.getInstance().setListaInspecciones(null);
		llenarTablaInspecciones("");
		tvDatos.setRowFactory(tv -> {
            TableRow<Inspeccion> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                	if(tvDatos.getSelectionModel().getSelectedItem() != null){
    					Context.getInstance().setInspeccion(tvDatos.getSelectionModel().getSelectedItem());
    					Context.getInstance().getStageModal().close();
    				}
                }
            });
            return row ;
        });
	}
	public void buscarCliente() {
		llenarTablaInspecciones(txtBuscar.getText());
	}
	@SuppressWarnings("unchecked")
	void llenarTablaInspecciones(String patron) {
		try{
			tvDatos.getColumns().clear();
			boolean bandera;
			List<Inspeccion> listaInspecciones;
			List<Inspeccion> listaAgregar = new ArrayList<Inspeccion>();
			if(Context.getInstance().getIdPerfil() == 1) {
				listaInspecciones = inspeccionDAO.getListaInspeccionPendiente(patron);
			}else {
				listaInspecciones = inspeccionDAO.getListaInspeccionPerfilPendiente(patron);
			}
			for(Inspeccion inspeccionAdd : listaInspecciones) {
				bandera = false;
				for(Inspeccion inspeccionLst : listadoInspecciones) {
					if(inspeccionAdd.getIdInspeccion() == inspeccionLst.getIdInspeccion())
						bandera = true;
				}
				if(bandera == false)
					listaAgregar.add(inspeccionAdd);
			}
			
			ObservableList<Inspeccion> datos = FXCollections.observableArrayList();
			datos.setAll(listaAgregar);

			//llenar los datos en la tabla
			TableColumn<Inspeccion, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(50);
			idColum.setCellValueFactory(new PropertyValueFactory<Inspeccion, String>("idInspeccion"));

			TableColumn<Inspeccion, String> fechaColum = new TableColumn<>("Fecha");
			fechaColum.setMinWidth(10);
			fechaColum.setPrefWidth(150);
			fechaColum.setCellValueFactory(new PropertyValueFactory<Inspeccion, String>("fecha"));

			TableColumn<Inspeccion, String> clienteColum = new TableColumn<>("Cliente");
			clienteColum.setMinWidth(10);
			clienteColum.setPrefWidth(250);
			clienteColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inspeccion,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Inspeccion, String> param) {
					String cliente = "";
					cliente = param.getValue().getCliente().getNombres() + " " + param.getValue().getCliente().getApellidos();
					return new SimpleObjectProperty<String>(cliente);
				}
			});

			TableColumn<Inspeccion, String> referenciaColum = new TableColumn<>("Referencia");
			referenciaColum.setMinWidth(10);
			referenciaColum.setPrefWidth(350);
			referenciaColum.setCellValueFactory(new PropertyValueFactory<Inspeccion, String>("referencia"));

			TableColumn<Inspeccion, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(90);
			estadoColum.setCellValueFactory(new PropertyValueFactory<Inspeccion, String>("estadoInspeccion"));


			tvDatos.getColumns().addAll(idColum, fechaColum,clienteColum,referenciaColum,estadoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}
