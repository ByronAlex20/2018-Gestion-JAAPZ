package ec.com.jaapz.controlador;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import ec.com.jaapz.modelo.Categoria;
import ec.com.jaapz.modelo.CategoriaDAO;
import ec.com.jaapz.modelo.Cliente;
import ec.com.jaapz.modelo.CuentaCliente;
import ec.com.jaapz.modelo.EstadoInspeccion;
import ec.com.jaapz.modelo.Factible;
import ec.com.jaapz.modelo.Inspeccion;
import ec.com.jaapz.modelo.InspeccionDAO;
import ec.com.jaapz.modelo.LiquidacionDetalle;
import ec.com.jaapz.modelo.LiquidacionOrden;
import ec.com.jaapz.modelo.LiquidacionOrdenDAO;
import ec.com.jaapz.modelo.PrecioUnitario;
import ec.com.jaapz.modelo.PrecioUnitarioDAO;
import ec.com.jaapz.modelo.Rubro;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.ControllerHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ClientesCierreInspeccionC {
	@FXML TextField txtCodigo;
	@FXML TextField txtFecha;
	@FXML TextField txtReferencia;
	@FXML TextField txtHabitar;
	@FXML TextField txtC�dula;
	@FXML TextField txtNombres;
	@FXML TextField txtTelefono;
	@FXML TextField txtGenero;
	@FXML TextField txtLatitud;
	@FXML TextField txtLongitud;
	@FXML TextArea txtObservacion;
	@FXML ComboBox<EstadoInspeccion> cboEstado;
	@FXML ComboBox<Factible> cboFactible;
	@FXML Button btnBuscar;
	@FXML Button btnGrabar;
	@FXML Button btnEliminar;
	@FXML Button btnAgregar;
	@FXML Button btnQuitar;
	@FXML Button btnBuscarRubro;
	@FXML TextField txtTipoRubro;
	@FXML TextField txtDescripcion;
	@FXML TextField txtStock;
	@FXML TextField txtCantidad;
	@FXML TextField txtPrecio;

	@FXML TableView<LiquidacionDetalle> tvDatosOrdenPrevia;


	EstadoInspeccion[] estadoInspeccion = EstadoInspeccion.values();
	Inspeccion inspeccionSeleccionado = new Inspeccion();
	ControllerHelper helper = new ControllerHelper();
	LiquidacionOrdenDAO ordenPreviaDAO = new LiquidacionOrdenDAO();
	InspeccionDAO inspeccionDAO = new InspeccionDAO();
	CategoriaDAO categoriaDAO = new CategoriaDAO();
	Rubro rubroSeleccionado;
	DecimalFormat decimales = new DecimalFormat("#0.00");
	PrecioUnitarioDAO preciosDAO = new PrecioUnitarioDAO();
	
	public void initialize() {
		limpiarCliente();
		bloquear();
		limpiarOrden();
		llenarCombo();	
		bloquearResultados();
		cboFactible.getSelectionModel().select(Factible.NO_FACTIBLE);
		cboFactible.setDisable(true);
	}
	@SuppressWarnings("unchecked")
	void recuperarDatos(Inspeccion inspeccion) {
		try {
			tvDatosOrdenPrevia.getItems().clear();
			tvDatosOrdenPrevia.getColumns().clear();
			if(inspeccion.getEstadoInspeccion().equals("REALIZADO")) {
				cboFactible.getSelectionModel().select(Factible.NO_FACTIBLE);
				cboFactible.setDisable(true);
			}else
				cboFactible.setDisable(false);
			txtC�dula.setText(inspeccion.getCliente().getCedula());
			txtNombres.setText(inspeccion.getCliente().getNombres() + " " + inspeccion.getCliente().getApellidos());
			txtTelefono.setText(inspeccion.getCliente().getTelefono());
			txtGenero.setText(inspeccion.getCliente().getGenero());
			txtCodigo.setText(String.valueOf(inspeccion.getIdInspeccion()));
			txtFecha.setText(String.valueOf(inspeccion.getFecha()));
			txtReferencia.setText(inspeccion.getReferencia());
			txtHabitar.setText(inspeccion.getUsoMedidor());
			for(EstadoInspeccion g : estadoInspeccion){
				if(g.toString().equals(inspeccion.getEstadoInspeccion()))
					cboEstado.getSelectionModel().select(g);
			}
			if(cboEstado.getValue().toString() == "PENDIENTE")
				cboEstado.setDisable(false);
			else
				cboEstado.setDisable(true);

			//recupera detalle si esta realizado
			if(inspeccion.getEstadoInspeccion().equals("REALIZADO")) {
				//llenar los datos en la tabla
				TableColumn<LiquidacionDetalle, String> cantidadColum = new TableColumn<>("Cantidad");
				cantidadColum.setMinWidth(10);
				cantidadColum.setPrefWidth(90);
				cantidadColum.setCellValueFactory(new PropertyValueFactory<LiquidacionDetalle, String>("cantidad"));

				TableColumn<LiquidacionDetalle, String> descipcionColum = new TableColumn<>("Descripcion");
				descipcionColum.setMinWidth(10);
				descipcionColum.setPrefWidth(250);
				descipcionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LiquidacionDetalle,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<LiquidacionDetalle, String> param) {
						return new SimpleObjectProperty<String>(param.getValue().getRubro().getDescripcion());
					}
				});

				TableColumn<LiquidacionDetalle, String> precioColum = new TableColumn<>("Costo U.");
				precioColum.setMinWidth(10);
				precioColum.setPrefWidth(90);
				precioColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LiquidacionDetalle,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<LiquidacionDetalle, String> param) {
						return new SimpleObjectProperty<String>(String.valueOf(decimales.format(param.getValue().getRubro().getPrecio())));
					}
				});

				TableColumn<LiquidacionDetalle, String> costoColum = new TableColumn<>("Costo Total");
				costoColum.setMinWidth(10);
				costoColum.setPrefWidth(90);
				costoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LiquidacionDetalle,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<LiquidacionDetalle, String> param) {
						return new SimpleObjectProperty<String>(String.valueOf(decimales.format(param.getValue().getTotal())));
					}
				});

				ObservableList<LiquidacionDetalle> datos = FXCollections.observableArrayList();
				datos.setAll(inspeccion.getLiquidacionOrdens().get(0).getLiquidacionDetalles());

				tvDatosOrdenPrevia.getColumns().addAll(cantidadColum,descipcionColum,precioColum,costoColum);
				tvDatosOrdenPrevia.setItems(datos);
			}
			//tambien recupera la latitud, longitud y observaciones
			txtObservacion.setText(inspeccion.getObservacion());
			if(inspeccion.getLiquidacionOrdens().size() != 0) {
				txtLatitud.setText(inspeccion.getLiquidacionOrdens().get(0).getCuentaCliente().getLatitud());
				txtLongitud.setText(inspeccion.getLiquidacionOrdens().get(0).getCuentaCliente().getLongitud());
			}else {
				txtLatitud.setText("");
				txtLongitud.setText("");
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	void llenarCombo() {
		try{
			ObservableList<EstadoInspeccion> listaEstado = FXCollections.observableArrayList(EstadoInspeccion.values());
			cboEstado.setItems(listaEstado);
			cboEstado.setPromptText("Est. de Inspecci�n");

			ObservableList<Factible> listaFactibilidad = FXCollections.observableArrayList(Factible.values());
			cboFactible.setItems(listaFactibilidad);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	void bloquear() {
		txtC�dula.setEditable(false);
		txtNombres.setEditable(false);
		txtTelefono.setEditable(false);
		txtGenero.setEditable(false);
		txtCodigo.setEditable(false);
		txtFecha.setEditable(false);
		txtReferencia.setEditable(false);
		txtHabitar.setEditable(false);
		txtTipoRubro.setEditable(false);
		txtDescripcion.setEditable(false);
		txtStock.setEditable(false);
		txtPrecio.setEditable(false);
	}
	public void buscarOrden() {
		try{
			helper.abrirPantallaModal("/clientes/ClientesListaOrdenes.fxml","Listado de �rdenes", Context.getInstance().getStage());
			if (Context.getInstance().getInspeccion() != null) {
				recuperarDatos(Context.getInstance().getInspeccion());
				inspeccionSeleccionado = Context.getInstance().getInspeccion();
				Context.getInstance().setInspeccion(null);
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public void grabar() {
		try {
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				if(cboFactible.getSelectionModel().getSelectedItem().equals(Factible.FACTIBLE)) {//es factible la instalacion del medidor en la vivienda
					double valorTotal = 0.0;
					//declaro los objetos a grabar
					LiquidacionOrden ordenLiquidacion = new LiquidacionOrden();
					CuentaCliente cuentaCliente = new CuentaCliente();
					Categoria categoria = new Categoria();
					List<LiquidacionDetalle> detalle = new ArrayList<LiquidacionDetalle>();
					
					//lleno los datos de la orden de despacho
					ordenLiquidacion.setIdLiquidacion(null);
					ordenLiquidacion.setEstadoOrden("PENDIENTE");
					ordenLiquidacion.setEstado("A");
					ordenLiquidacion.setUsuarioCrea(Context.getInstance().getIdUsuario());

					Date date = new Date();
					Timestamp fecha = new Timestamp(date.getTime());
					ordenLiquidacion.setFecha(fecha);
					inspeccionSeleccionado.setEstadoInspeccion(String.valueOf(cboEstado.getValue()));
					//lista de detalle de la orden previa de inspeccion a insertar
					
					for(LiquidacionDetalle det : tvDatosOrdenPrevia.getItems()) {
						det.setIdDetalle(null);
						det.setLiquidacionOrden(ordenLiquidacion);//se le agrega a que orden pertenece ese detalle
						valorTotal = valorTotal + det.getTotal();
						detalle.add(det);
					}
					//se realiza el enlace con el detalle, ya que anteriormente se le habia asignado el detalle
					ordenLiquidacion.setLiquidacionDetalles(detalle);
					ordenLiquidacion.setTotal(valorTotal);
					ordenLiquidacion.setInspeccion(inspeccionSeleccionado);

					//tambien hay que asignar la categoria por defecto es la normal
					categoria = categoriaDAO.getListaCatNormal().get(0);//el 0 xq solo es un registro

					Cliente cliente = new Cliente();
					cliente = inspeccionSeleccionado.getCliente();
					cuentaCliente.setIdCuenta(null);
					cuentaCliente.setUsuarioCrea(Context.getInstance().getIdUsuario());
					cuentaCliente.setLatitud(txtLatitud.getText());
					cuentaCliente.setLongitud(txtLongitud.getText());
					cuentaCliente.setCliente(cliente);
					cuentaCliente.setFechaIngreso(fecha);
					cuentaCliente.setEstado("A");
					
					//enlace entre cuenta cliente y categoria
					cuentaCliente.setCategoria(categoria);
					List<CuentaCliente> listaCuenta = new ArrayList<CuentaCliente>();
					listaCuenta.add(cuentaCliente);
					categoria.setCuentaClientes(listaCuenta);
					
					ordenLiquidacion.setCuentaCliente(cuentaCliente);
					List<LiquidacionOrden> listaOrden = new ArrayList<LiquidacionOrden>();
					listaOrden.add(ordenLiquidacion);
					cuentaCliente.setLiquidacionOrdens(listaOrden);
					//enlace entre orden liquidacion y la inspeccion
					ordenLiquidacion.setInspeccion(inspeccionSeleccionado);
					inspeccionSeleccionado.getLiquidacionOrdens().add(ordenLiquidacion);

					//se procede a grabar los daotos
					inspeccionDAO.getEntityManager().getTransaction().begin();
					inspeccionDAO.getEntityManager().merge(inspeccionSeleccionado);
					inspeccionDAO.getEntityManager().getTransaction().commit();

					helper.mostrarAlertaInformacion("Datos Grabados", Context.getInstance().getStage());
					limpiarCliente();
					limpiarOrden();
					limpiarObservaciones();
					tvDatosOrdenPrevia.getColumns().clear();
					tvDatosOrdenPrevia.getItems().clear();
				}else {
					inspeccionSeleccionado.setEstadoInspeccion(cboEstado.getSelectionModel().getSelectedItem().toString());
					inspeccionSeleccionado.setFactibilidad(cboFactible.getSelectionModel().getSelectedItem().toString());
					//se procede a grabar los daotos
					inspeccionDAO.getEntityManager().getTransaction().begin();
					inspeccionDAO.getEntityManager().merge(inspeccionSeleccionado);
					inspeccionDAO.getEntityManager().getTransaction().commit();
				}
				
			}
		}catch(Exception ex) {
			inspeccionDAO.getEntityManager().getTransaction().rollback();
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
			System.out.println(ex.getMessage());
		}
	}
	public void eliminar() {
		try {
			if(inspeccionSeleccionado.getEstadoInspeccion() == "REALIZADO") {
				helper.mostrarAlertaAdvertencia("No se puede eliminar una orden ya realizada", Context.getInstance().getStage());
				return;
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void cambiarFactibilidad() {
		try {
			if(cboFactible.getSelectionModel().getSelectedItem().equals(Factible.FACTIBLE)) {
				desbloquearResultados();
				cargarPreciosUnitarios();
			}
			else {
				bloquearResultados();
				tvDatosOrdenPrevia.getColumns().clear();
				tvDatosOrdenPrevia.getItems().clear();
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	private void cargarPreciosUnitarios() {
		try {
			tvDatosOrdenPrevia.getItems().clear();
			tvDatosOrdenPrevia.getColumns().clear();

			List<PrecioUnitario> listaPrecios;
			listaPrecios = preciosDAO.getListaPrecios();
			
			//llenar los datos en la tabla
			TableColumn<LiquidacionDetalle, String> cantidadColum = new TableColumn<>("Cantidad");
			cantidadColum.setMinWidth(10);
			cantidadColum.setPrefWidth(90);
			cantidadColum.setCellValueFactory(new PropertyValueFactory<LiquidacionDetalle, String>("cantidad"));

			TableColumn<LiquidacionDetalle, String> descipcionColum = new TableColumn<>("Descripcion");
			descipcionColum.setMinWidth(10);
			descipcionColum.setPrefWidth(250);
			descipcionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LiquidacionDetalle,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<LiquidacionDetalle, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getRubro().getDescripcion());
				}
			});

			TableColumn<LiquidacionDetalle, String> precioColum = new TableColumn<>("Costo U.");
			precioColum.setMinWidth(10);
			precioColum.setPrefWidth(90);
			precioColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LiquidacionDetalle,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<LiquidacionDetalle, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(decimales.format(param.getValue().getRubro().getPrecio())));
				}
			});

			TableColumn<LiquidacionDetalle, String> costoColum = new TableColumn<>("Costo Total");
			costoColum.setMinWidth(10);
			costoColum.setPrefWidth(90);
			costoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LiquidacionDetalle,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<LiquidacionDetalle, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(decimales.format(param.getValue().getTotal())));
				}
			});

			ObservableList<LiquidacionDetalle> datos = FXCollections.observableArrayList();
			for(PrecioUnitario p : listaPrecios) {
				LiquidacionDetalle orden = new LiquidacionDetalle();
				orden.setCantidad(p.getCantidad());
				orden.setRubro(p.getRubro());
				orden.setEstado("A");
				orden.setPrecio(p.getRubro().getPrecio());
				orden.setTotal(p.getTotal());
				orden.setIdDetalle(null);
				datos.add(orden);
			}

			tvDatosOrdenPrevia.getColumns().addAll(cantidadColum,descipcionColum,precioColum,costoColum);
			tvDatosOrdenPrevia.setItems(datos);

		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void buscarRubro() {
		try{
			helper.abrirPantallaModal("/clientes/ClientesListadoPrecios.fxml","Listado de Precios Unitarios", Context.getInstance().getStage());
			if (Context.getInstance().getRubro() != null) {
				rubroSeleccionado = Context.getInstance().getRubro();
				recuperarDatosRubros();
			}else
				limpiarRubro();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	void recuperarDatosRubros() {
		try {
			txtStock.setText(String.valueOf(rubroSeleccionado.getStock()));
			txtDescripcion.setText(rubroSeleccionado.getDescripcion());
			txtPrecio.setText(String.valueOf(decimales.format(rubroSeleccionado.getPrecio())));
			txtTipoRubro.setText(rubroSeleccionado.getTipoRubro().getDescripcion());
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	public void agregarPrecioUnitario() {
		try {
			if(txtCantidad.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe Ingresar cantidad!!!", Context.getInstance().getStage());
				return;
			}
			ObservableList<LiquidacionDetalle> datos = tvDatosOrdenPrevia.getItems();
			tvDatosOrdenPrevia.getColumns().clear();
			LiquidacionDetalle datoAnadir = new LiquidacionDetalle();
			datoAnadir.setEstado("A");
			datoAnadir.setIdDetalle(null);
			datoAnadir.setRubro(rubroSeleccionado);
			datoAnadir.setCantidad(Integer.valueOf(txtCantidad.getText()));
			datoAnadir.setPrecio(Double.valueOf(txtCantidad.getText()) * rubroSeleccionado.getPrecio());
			datos.add(datoAnadir);


			//llenar los datos en la tabla
			TableColumn<LiquidacionDetalle, String> cantidadColum = new TableColumn<>("Cantidad");
			cantidadColum.setMinWidth(10);
			cantidadColum.setPrefWidth(90);
			cantidadColum.setCellValueFactory(new PropertyValueFactory<LiquidacionDetalle, String>("cantidad"));

			TableColumn<LiquidacionDetalle, String> descipcionColum = new TableColumn<>("Descripcion");
			descipcionColum.setMinWidth(10);
			descipcionColum.setPrefWidth(250);
			descipcionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LiquidacionDetalle,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<LiquidacionDetalle, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getRubro().getDescripcion());
				}
			});

			TableColumn<LiquidacionDetalle, String> precioColum = new TableColumn<>("Costo U.");
			precioColum.setMinWidth(10);
			precioColum.setPrefWidth(90);
			precioColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LiquidacionDetalle,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<LiquidacionDetalle, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(decimales.format(param.getValue().getRubro().getPrecio())));
				}
			});

			TableColumn<LiquidacionDetalle, String> costoColum = new TableColumn<>("Costo Total");
			costoColum.setMinWidth(10);
			costoColum.setPrefWidth(90);
			costoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LiquidacionDetalle,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<LiquidacionDetalle, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(decimales.format(param.getValue().getPrecio())));
				}
			});


			tvDatosOrdenPrevia.getColumns().addAll(cantidadColum,descipcionColum,precioColum,costoColum);
			tvDatosOrdenPrevia.setItems(datos);
			rubroSeleccionado = null;
			limpiarRubro();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			rubroSeleccionado = null;
			Context.getInstance().setRubro(null);
		}
	}
	public void quitarPrecioUnitario() {
		try {
			LiquidacionDetalle detalleSeleccionado = tvDatosOrdenPrevia.getSelectionModel().getSelectedItem();
			tvDatosOrdenPrevia.getItems().remove(detalleSeleccionado);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void limpiarCliente() {
		txtC�dula.setText("");
		txtNombres.setText("");
		txtTelefono.setText("");
		txtGenero.setText("");
	}
	private void limpiarOrden() {
		txtCodigo.setText("");
		txtFecha.setText("");
		txtReferencia.setText("");
		txtHabitar.setText("");
	}
	private void limpiarRubro() {
		txtStock.setText("");
		txtDescripcion.setText("");
		txtPrecio.setText("");
		txtTipoRubro.setText("");
		txtCantidad.setText("");
	}
	private void limpiarObservaciones() {
		txtObservacion.setText("");
		txtLatitud.setText("");
		txtLongitud.setText("");
	}
	void bloquearResultados() {
		tvDatosOrdenPrevia.setDisable(true);
		btnAgregar.setDisable(true);
		btnQuitar.setDisable(true);
		txtTipoRubro.setDisable(true);
		txtDescripcion.setDisable(true);
		txtStock.setDisable(true);
		txtCantidad.setDisable(true);
		txtPrecio.setDisable(true);
		btnBuscarRubro.setDisable(true);
	}
	void desbloquearResultados() {
		tvDatosOrdenPrevia.setDisable(false);
		btnAgregar.setDisable(false);
		btnQuitar.setDisable(false);
		txtTipoRubro.setDisable(false);
		txtDescripcion.setDisable(false);
		txtStock.setDisable(false);
		txtCantidad.setDisable(false);
		txtPrecio.setDisable(false);
		btnBuscarRubro.setDisable(false);
	}
}
