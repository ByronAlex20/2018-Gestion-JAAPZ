package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the liquidacion_orden database table.
 * 
 */
@Entity
@Table(name="liquidacion_orden")
@NamedQueries({
	@NamedQuery(name="LiquidacionOrden.findAll", query="SELECT l FROM LiquidacionOrden l "
			+ "where (lower(l.cuentaCliente.cliente.apellidos) like :patron or lower(l.cuentaCliente.cliente.nombres) like :patron "
			+ "or lower(l.cuentaCliente.cliente.cedula) like :patron) and l.inspeccion.estadoInspeccion = 'REALIZADO' and L.estadoOrden = 'PENDIENTE'"
			+ "order by l.idLiquidacion asc"),
	@NamedQuery(name="LiquidacionOrden.buscarLiquidacionOrdenPerfil", query="SELECT l FROM LiquidacionOrden l "
			+ "where (lower(l.cuentaCliente.cliente.apellidos) like :patron or lower(l.cuentaCliente.cliente.nombres) like :patron "
			+ "or lower(l.cuentaCliente.cliente.cedula) like :patron) and l.inspeccion.estadoInspeccion = 'REALIZADO' and l.estadoOrden = 'PENDIENTE'"
			+ "and l.usuarioCrea = :idPerfilUsuario order by l.idLiquidacion asc")
})
public class LiquidacionOrden implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_liquidacion")
	private Integer idLiquidacion;

	private String estado;

	@Column(name="estado_orden")
	private String estadoOrden;

	private Timestamp fecha;

	@Column(name="foto_predio")
	private byte[] fotoPredio;

	private double total;

	@Column(name="usuario_crea")
	private Integer usuarioCrea;

	//bi-directional many-to-one association to LiquidacionDetalle
	@OneToMany(mappedBy="liquidacionOrden")
	private List<LiquidacionDetalle> liquidacionDetalles;

	//bi-directional many-to-one association to CuentaCliente
	@ManyToOne
	@JoinColumn(name="id_cuenta")
	private CuentaCliente cuentaCliente;

	//bi-directional many-to-one association to Inspeccion
	@ManyToOne
	@JoinColumn(name="id_inspeccion")
	private Inspeccion inspeccion;

	public LiquidacionOrden() {
	}

	public Integer getIdLiquidacion() {
		return this.idLiquidacion;
	}

	public void setIdLiquidacion(Integer idLiquidacion) {
		this.idLiquidacion = idLiquidacion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoOrden() {
		return this.estadoOrden;
	}

	public void setEstadoOrden(String estadoOrden) {
		this.estadoOrden = estadoOrden;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public byte[] getFotoPredio() {
		return this.fotoPredio;
	}

	public void setFotoPredio(byte[] fotoPredio) {
		this.fotoPredio = fotoPredio;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Integer getUsuarioCrea() {
		return this.usuarioCrea;
	}

	public void setUsuarioCrea(Integer usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}

	public List<LiquidacionDetalle> getLiquidacionDetalles() {
		return this.liquidacionDetalles;
	}

	public void setLiquidacionDetalles(List<LiquidacionDetalle> liquidacionDetalles) {
		this.liquidacionDetalles = liquidacionDetalles;
	}

	public LiquidacionDetalle addLiquidacionDetalle(LiquidacionDetalle liquidacionDetalle) {
		getLiquidacionDetalles().add(liquidacionDetalle);
		liquidacionDetalle.setLiquidacionOrden(this);

		return liquidacionDetalle;
	}

	public LiquidacionDetalle removeLiquidacionDetalle(LiquidacionDetalle liquidacionDetalle) {
		getLiquidacionDetalles().remove(liquidacionDetalle);
		liquidacionDetalle.setLiquidacionOrden(null);

		return liquidacionDetalle;
	}

	public CuentaCliente getCuentaCliente() {
		return this.cuentaCliente;
	}

	public void setCuentaCliente(CuentaCliente cuentaCliente) {
		this.cuentaCliente = cuentaCliente;
	}

	public Inspeccion getInspeccion() {
		return this.inspeccion;
	}

	public void setInspeccion(Inspeccion inspeccion) {
		this.inspeccion = inspeccion;
	}

}