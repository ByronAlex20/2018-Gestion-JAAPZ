package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the ingreso database table.
 * 
 */
@Entity
@NamedQuery(name="Ingreso.findAll", query="SELECT i FROM Ingreso i")
public class Ingreso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ingreso")
	private Integer idIngreso;

	private String estado;

	private Timestamp fecha;

	@Column(name="numero_ingreso")
	private String numeroIngreso;

	private double subtotal;

	private double total;

	@Column(name="usuario_crea")
	private Integer usuarioCrea;

	//bi-directional many-to-one association to IngresoDetalle
	@OneToMany(mappedBy="ingreso")
	private List<IngresoDetalle> ingresoDetalles;

	public Ingreso() {
	}

	public Integer getIdIngreso() {
		return this.idIngreso;
	}

	public void setIdIngreso(Integer idIngreso) {
		this.idIngreso = idIngreso;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getNumeroIngreso() {
		return this.numeroIngreso;
	}

	public void setNumeroIngreso(String numeroIngreso) {
		this.numeroIngreso = numeroIngreso;
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

	public List<IngresoDetalle> getIngresoDetalles() {
		return this.ingresoDetalles;
	}

	public void setIngresoDetalles(List<IngresoDetalle> ingresoDetalles) {
		this.ingresoDetalles = ingresoDetalles;
	}

	public IngresoDetalle addIngresoDetalle(IngresoDetalle ingresoDetalle) {
		getIngresoDetalles().add(ingresoDetalle);
		ingresoDetalle.setIngreso(this);

		return ingresoDetalle;
	}

	public IngresoDetalle removeIngresoDetalle(IngresoDetalle ingresoDetalle) {
		getIngresoDetalles().remove(ingresoDetalle);
		ingresoDetalle.setIngreso(null);

		return ingresoDetalle;
	}

}