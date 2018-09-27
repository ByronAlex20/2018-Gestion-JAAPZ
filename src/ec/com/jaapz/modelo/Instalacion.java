package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the instalacion database table.
 * 
 */
@Entity
@NamedQuery(name="Instalacion.findAll", query="SELECT i FROM Instalacion i")
public class Instalacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_instalacion")
	private Integer idInstalacion;

	private double descuento;

	private String estado;

	@Column(name="estado_valor")
	private String estadoValor;

	@Column(name="fecha_inst")
	private Timestamp fechaInst;

	@Column(name="foto_predio")
	private byte[] fotoPredio;

	private double subtotal;

	private double total;

	@Column(name="usuario_crea")
	private Integer usuarioCrea;

	//bi-directional many-to-one association to CuentaCliente
	@ManyToOne
	@JoinColumn(name="id_cuenta")
	private CuentaCliente cuentaCliente;

	//bi-directional many-to-one association to Inspeccion
	@ManyToOne
	@JoinColumn(name="id_inspeccion")
	private Inspeccion inspeccion;

	//bi-directional many-to-one association to InstalacionDetalle
	@OneToMany(mappedBy="instalacion")
	private List<InstalacionDetalle> instalacionDetalles;

	//bi-directional many-to-one association to PlanillaDetalle
	@OneToMany(mappedBy="instalacion")
	private List<PlanillaDetalle> planillaDetalles;

	public Instalacion() {
	}

	public Integer getIdInstalacion() {
		return this.idInstalacion;
	}

	public void setIdInstalacion(Integer idInstalacion) {
		this.idInstalacion = idInstalacion;
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

	public String getEstadoValor() {
		return this.estadoValor;
	}

	public void setEstadoValor(String estadoValor) {
		this.estadoValor = estadoValor;
	}

	public Timestamp getFechaInst() {
		return this.fechaInst;
	}

	public void setFechaInst(Timestamp fechaInst) {
		this.fechaInst = fechaInst;
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

	public List<InstalacionDetalle> getInstalacionDetalles() {
		return this.instalacionDetalles;
	}

	public void setInstalacionDetalles(List<InstalacionDetalle> instalacionDetalles) {
		this.instalacionDetalles = instalacionDetalles;
	}

	public InstalacionDetalle addInstalacionDetalle(InstalacionDetalle instalacionDetalle) {
		getInstalacionDetalles().add(instalacionDetalle);
		instalacionDetalle.setInstalacion(this);

		return instalacionDetalle;
	}

	public InstalacionDetalle removeInstalacionDetalle(InstalacionDetalle instalacionDetalle) {
		getInstalacionDetalles().remove(instalacionDetalle);
		instalacionDetalle.setInstalacion(null);

		return instalacionDetalle;
	}

	public List<PlanillaDetalle> getPlanillaDetalles() {
		return this.planillaDetalles;
	}

	public void setPlanillaDetalles(List<PlanillaDetalle> planillaDetalles) {
		this.planillaDetalles = planillaDetalles;
	}

	public PlanillaDetalle addPlanillaDetalle(PlanillaDetalle planillaDetalle) {
		getPlanillaDetalles().add(planillaDetalle);
		planillaDetalle.setInstalacion(this);

		return planillaDetalle;
	}

	public PlanillaDetalle removePlanillaDetalle(PlanillaDetalle planillaDetalle) {
		getPlanillaDetalles().remove(planillaDetalle);
		planillaDetalle.setInstalacion(null);

		return planillaDetalle;
	}

}