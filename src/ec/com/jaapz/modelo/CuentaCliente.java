package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the cuenta_cliente database table.
 * 
 */
@Entity
@Table(name="cuenta_cliente")
@NamedQueries({
	@NamedQuery(name="CuentaCliente.findAll", query="SELECT c FROM CuentaCliente c where "
			+ "(lower(c.cliente.nombres) like lower(:patron) or lower(c.cliente.apellidos) like lower(:patron)) order by c.idCuenta asc"),
	@NamedQuery(name="CuentaCliente.bucarTodos", query="SELECT c FROM CuentaCliente c where c.estado = 'A'")
})
public class CuentaCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cuenta")
	private Integer idCuenta;

	private String direccion;

	private String email;

	private String estado;

	@Column(name="fecha_ingreso")
	private Timestamp fechaIngreso;

	private String latitud;

	private String longitud;

	private String observacion;

	@Column(name="usuario_crea")
	private Integer usuarioCrea;

	//bi-directional many-to-one association to Convenio
	@OneToMany(mappedBy="cuentaCliente")
	private List<Convenio> convenios;

	//bi-directional many-to-one association to Barrio
	@ManyToOne
	@JoinColumn(name="id_barrio")
	private Barrio barrio;

	//bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private Categoria categoria;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to Medidor
	@ManyToOne
	@JoinColumn(name="id_medidor")
	private Medidor medidor;

	//bi-directional many-to-one association to Factura
	@OneToMany(mappedBy="cuentaCliente")
	private List<Factura> facturas;

	//bi-directional many-to-one association to Instalacion
	@OneToMany(mappedBy="cuentaCliente")
	private List<Instalacion> instalacions;

	//bi-directional many-to-one association to LiquidacionOrden
	@OneToMany(mappedBy="cuentaCliente")
	private List<LiquidacionOrden> liquidacionOrdens;

	//bi-directional many-to-one association to Planilla
	@OneToMany(mappedBy="cuentaCliente")
	private List<Planilla> planillas;

	//bi-directional many-to-one association to Reparacion
	@OneToMany(mappedBy="cuentaCliente")
	private List<Reparacion> reparacions;

	public CuentaCliente() {
	}

	public Integer getIdCuenta() {
		return this.idCuenta;
	}

	public void setIdCuenta(Integer idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaIngreso() {
		return this.fechaIngreso;
	}

	public void setFechaIngreso(Timestamp fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getLatitud() {
		return this.latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return this.longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Integer getUsuarioCrea() {
		return this.usuarioCrea;
	}

	public void setUsuarioCrea(Integer usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}

	public List<Convenio> getConvenios() {
		return this.convenios;
	}

	public void setConvenios(List<Convenio> convenios) {
		this.convenios = convenios;
	}

	public Convenio addConvenio(Convenio convenio) {
		getConvenios().add(convenio);
		convenio.setCuentaCliente(this);

		return convenio;
	}

	public Convenio removeConvenio(Convenio convenio) {
		getConvenios().remove(convenio);
		convenio.setCuentaCliente(null);

		return convenio;
	}

	public Barrio getBarrio() {
		return this.barrio;
	}

	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Medidor getMedidor() {
		return this.medidor;
	}

	public void setMedidor(Medidor medidor) {
		this.medidor = medidor;
	}

	public List<Factura> getFacturas() {
		return this.facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public Factura addFactura(Factura factura) {
		getFacturas().add(factura);
		factura.setCuentaCliente(this);

		return factura;
	}

	public Factura removeFactura(Factura factura) {
		getFacturas().remove(factura);
		factura.setCuentaCliente(null);

		return factura;
	}

	public List<Instalacion> getInstalacions() {
		return this.instalacions;
	}

	public void setInstalacions(List<Instalacion> instalacions) {
		this.instalacions = instalacions;
	}

	public Instalacion addInstalacion(Instalacion instalacion) {
		getInstalacions().add(instalacion);
		instalacion.setCuentaCliente(this);

		return instalacion;
	}

	public Instalacion removeInstalacion(Instalacion instalacion) {
		getInstalacions().remove(instalacion);
		instalacion.setCuentaCliente(null);

		return instalacion;
	}

	public List<LiquidacionOrden> getLiquidacionOrdens() {
		return this.liquidacionOrdens;
	}

	public void setLiquidacionOrdens(List<LiquidacionOrden> liquidacionOrdens) {
		this.liquidacionOrdens = liquidacionOrdens;
	}

	public LiquidacionOrden addLiquidacionOrden(LiquidacionOrden liquidacionOrden) {
		getLiquidacionOrdens().add(liquidacionOrden);
		liquidacionOrden.setCuentaCliente(this);

		return liquidacionOrden;
	}

	public LiquidacionOrden removeLiquidacionOrden(LiquidacionOrden liquidacionOrden) {
		getLiquidacionOrdens().remove(liquidacionOrden);
		liquidacionOrden.setCuentaCliente(null);

		return liquidacionOrden;
	}

	public List<Planilla> getPlanillas() {
		return this.planillas;
	}

	public void setPlanillas(List<Planilla> planillas) {
		this.planillas = planillas;
	}

	public Planilla addPlanilla(Planilla planilla) {
		getPlanillas().add(planilla);
		planilla.setCuentaCliente(this);

		return planilla;
	}

	public Planilla removePlanilla(Planilla planilla) {
		getPlanillas().remove(planilla);
		planilla.setCuentaCliente(null);

		return planilla;
	}

	public List<Reparacion> getReparacions() {
		return this.reparacions;
	}

	public void setReparacions(List<Reparacion> reparacions) {
		this.reparacions = reparacions;
	}

	public Reparacion addReparacion(Reparacion reparacion) {
		getReparacions().add(reparacion);
		reparacion.setCuentaCliente(this);

		return reparacion;
	}

	public Reparacion removeReparacion(Reparacion reparacion) {
		getReparacions().remove(reparacion);
		reparacion.setCuentaCliente(null);

		return reparacion;
	}

}