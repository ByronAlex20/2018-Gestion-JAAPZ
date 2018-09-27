package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the inspeccion database table.
 * 
 */
@Entity
@Table(name="inspeccion")
@NamedQueries({
	@NamedQuery(name="Inspeccion.findAll", query="SELECT i FROM Inspeccion i "
			+ "where (lower(i.cliente.apellidos) like :patron  or lower(i.cliente.nombres) like :patron) and i.estadoInspeccion = 'PENDIENTE' "
			+ "and i.estado = 'A' order by i.estadoInspeccion asc"),
	@NamedQuery(name="Inspeccion.buscarInspeccionesTodas", query="SELECT i FROM Inspeccion i "
			+ "where (lower(i.cliente.apellidos) like :patron  or lower(i.cliente.nombres) like :patron) "
			+ "and i.estado = 'A' order by i.estadoInspeccion asc"),
	@NamedQuery(name="Inspeccion.findAllPendiente", query="SELECT i FROM Inspeccion i "
			+ "where (lower(i.cliente.apellidos) like :patron  or lower(i.cliente.nombres) like :patron) "
			+ "and i.estadoInspeccion = 'PENDIENTE' and i.idUsuEncargado = null order by i.idInspeccion desc"),

	@NamedQuery(name="Inspeccion.buscarInspeccionPerfil", query="SELECT i FROM Inspeccion i "
			+ "where (lower(i.cliente.apellidos) like :patron  or lower(i.cliente.nombres) like :patron) "
			+ " and i.idUsuEncargado = :idPerfilUsuario order by i.idInspeccion desc"),

	@NamedQuery(name="Inspeccion.buscarInspeccionPerfilPendiente", query="SELECT i FROM Inspeccion i "
			+ "where lower(i.cliente.apellidos) like :patron  or lower(i.cliente.nombres) like :patron "
			+ " and i.idUsuEncargado = :idPerfilUsuario and i.idUsuEncargado = null "
			+ " and i.estadoInspeccion = 'PENDIENTE' order by i.idInspeccion desc"),

	@NamedQuery(name="Inspeccion.buscarInspeccionAsignada", query="SELECT i FROM Inspeccion i "
			+ "where i.idUsuEncargado = :idPerfilUsuario order by i.idInspeccion desc")
})
public class Inspeccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_inspeccion")
	private Integer idInspeccion;

	private String estado;

	@Column(name="estado_inspeccion")
	private String estadoInspeccion;

	private String factibilidad;

	private Timestamp fecha;

	@Column(name="id_usu_encargado")
	private Integer idUsuEncargado;

	
	@Column(name="fecha_aprobacion")
	private Timestamp fechaAprobacion;
	
	
	private String observacion;

	private String referencia;

	@Column(name="uso_medidor")
	private String usoMedidor;

	@Column(name="usuario_crea")
	private Integer usuarioCrea;

	//bi-directional many-to-one association to Barrio
	@ManyToOne
	@JoinColumn(name="id_barrio")
	private Barrio barrio;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to Instalacion
	@OneToMany(mappedBy="inspeccion")
	private List<Instalacion> instalacions;

	//bi-directional many-to-one association to LiquidacionOrden
	@OneToMany(mappedBy="inspeccion")
	private List<LiquidacionOrden> liquidacionOrdens;

	public Inspeccion() {
	}

	public Integer getIdInspeccion() {
		return this.idInspeccion;
	}

	public void setIdInspeccion(Integer idInspeccion) {
		this.idInspeccion = idInspeccion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoInspeccion() {
		return this.estadoInspeccion;
	}

	public void setEstadoInspeccion(String estadoInspeccion) {
		this.estadoInspeccion = estadoInspeccion;
	}

	public String getFactibilidad() {
		return this.factibilidad;
	}

	public void setFactibilidad(String factibilidad) {
		this.factibilidad = factibilidad;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public Integer getIdUsuEncargado() {
		return this.idUsuEncargado;
	}

	public void setIdUsuEncargado(Integer idUsuEncargado) {
		this.idUsuEncargado = idUsuEncargado;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getUsoMedidor() {
		return this.usoMedidor;
	}

	public void setUsoMedidor(String usoMedidor) {
		this.usoMedidor = usoMedidor;
	}

	public Integer getUsuarioCrea() {
		return this.usuarioCrea;
	}

	public void setUsuarioCrea(Integer usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}

	public Barrio getBarrio() {
		return this.barrio;
	}

	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Instalacion> getInstalacions() {
		return this.instalacions;
	}

	public void setInstalacions(List<Instalacion> instalacions) {
		this.instalacions = instalacions;
	}

	public Instalacion addInstalacion(Instalacion instalacion) {
		getInstalacions().add(instalacion);
		instalacion.setInspeccion(this);

		return instalacion;
	}

	public Instalacion removeInstalacion(Instalacion instalacion) {
		getInstalacions().remove(instalacion);
		instalacion.setInspeccion(null);

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
		liquidacionOrden.setInspeccion(this);

		return liquidacionOrden;
	}

	public LiquidacionOrden removeLiquidacionOrden(LiquidacionOrden liquidacionOrden) {
		getLiquidacionOrdens().remove(liquidacionOrden);
		liquidacionOrden.setInspeccion(null);

		return liquidacionOrden;
	}

	public Timestamp getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Timestamp fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

}