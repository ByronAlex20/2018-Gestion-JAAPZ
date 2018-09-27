package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the reparacion database table.
 * 
 */
@Entity
@NamedQuery(name="Reparacion.findAll", query="SELECT r FROM Reparacion r")
public class Reparacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_reparacion")
	private Integer idReparacion;

	private double descuento;

	private String estado;

	@Column(name="estado_entrega")
	private String estadoEntrega;

	@Column(name="estado_valor")
	private String estadoValor;

	@Column(name="fecha_rep")
	private Timestamp fechaRep;

	@Column(name="foto_predio")
	private byte[] fotoPredio;

	private double subtotal;

	private double total;

	@Column(name="usuario_crea")
	private Integer usuarioCrea;

	//bi-directional many-to-one association to PlanillaDetalle
	@OneToMany(mappedBy="reparacion")
	private List<PlanillaDetalle> planillaDetalles;

	//bi-directional many-to-one association to CuentaCliente
	@ManyToOne
	@JoinColumn(name="id_cuenta")
	private CuentaCliente cuentaCliente;

	//bi-directional many-to-one association to ReparacionDetalle
	@OneToMany(mappedBy="reparacion")
	private List<ReparacionDetalle> reparacionDetalles;

	public Reparacion() {
	}

	public Integer getIdReparacion() {
		return this.idReparacion;
	}

	public void setIdReparacion(Integer idReparacion) {
		this.idReparacion = idReparacion;
	}

	public double getDescuento() {
		return this.descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoEntrega() {
		return this.estadoEntrega;
	}

	public void setEstadoEntrega(String estadoEntrega) {
		this.estadoEntrega = estadoEntrega;
	}

	public String getEstadoValor() {
		return this.estadoValor;
	}

	public void setEstadoValor(String estadoValor) {
		this.estadoValor = estadoValor;
	}

	public Timestamp getFechaRep() {
		return this.fechaRep;
	}

	public void setFechaRep(Timestamp fechaRep) {
		this.fechaRep = fechaRep;
	}

	public byte[] getFotoPredio() {
		return this.fotoPredio;
	}

	public void setFotoPredio(byte[] fotoPredio) {
		this.fotoPredio = fotoPredio;
	}

	public double getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
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

	public List<PlanillaDetalle> getPlanillaDetalles() {
		return this.planillaDetalles;
	}

	public void setPlanillaDetalles(List<PlanillaDetalle> planillaDetalles) {
		this.planillaDetalles = planillaDetalles;
	}

	public PlanillaDetalle addPlanillaDetalle(PlanillaDetalle planillaDetalle) {
		getPlanillaDetalles().add(planillaDetalle);
		planillaDetalle.setReparacion(this);

		return planillaDetalle;
	}

	public PlanillaDetalle removePlanillaDetalle(PlanillaDetalle planillaDetalle) {
		getPlanillaDetalles().remove(planillaDetalle);
		planillaDetalle.setReparacion(null);

		return planillaDetalle;
	}

	public CuentaCliente getCuentaCliente() {
		return this.cuentaCliente;
	}

	public void setCuentaCliente(CuentaCliente cuentaCliente) {
		this.cuentaCliente = cuentaCliente;
	}

	public List<ReparacionDetalle> getReparacionDetalles() {
		return this.reparacionDetalles;
	}

	public void setReparacionDetalles(List<ReparacionDetalle> reparacionDetalles) {
		this.reparacionDetalles = reparacionDetalles;
	}

	public ReparacionDetalle addReparacionDetalle(ReparacionDetalle reparacionDetalle) {
		getReparacionDetalles().add(reparacionDetalle);
		reparacionDetalle.setReparacion(this);

		return reparacionDetalle;
	}

	public ReparacionDetalle removeReparacionDetalle(ReparacionDetalle reparacionDetalle) {
		getReparacionDetalles().remove(reparacionDetalle);
		reparacionDetalle.setReparacion(null);

		return reparacionDetalle;
	}

}