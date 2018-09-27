package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the reparacion_detalle database table.
 * 
 */
@Entity
@Table(name="reparacion_detalle")
@NamedQuery(name="ReparacionDetalle.findAll", query="SELECT r FROM ReparacionDetalle r")
public class ReparacionDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_reparacion_det")
	private Integer idReparacionDet;

	private Integer cantidad;

	private String estado;

	@Column(name="estado_entrega")
	private String estadoEntrega;

	private double precio;

	private double subtotal;

	@Column(name="usuario_crea")
	private Integer usuarioCrea;

	//bi-directional many-to-one association to Reparacion
	@ManyToOne
	@JoinColumn(name="id_reparacion")
	private Reparacion reparacion;

	//bi-directional many-to-one association to Rubro
	@ManyToOne
	@JoinColumn(name="id_rubro")
	private Rubro rubro;

	public ReparacionDetalle() {
	}

	public Integer getIdReparacionDet() {
		return this.idReparacionDet;
	}

	public void setIdReparacionDet(Integer idReparacionDet) {
		this.idReparacionDet = idReparacionDet;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
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

	public double getPrecio() {
		return this.precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public Integer getUsuarioCrea() {
		return this.usuarioCrea;
	}

	public void setUsuarioCrea(Integer usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}

	public Reparacion getReparacion() {
		return this.reparacion;
	}

	public void setReparacion(Reparacion reparacion) {
		this.reparacion = reparacion;
	}

	public Rubro getRubro() {
		return this.rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

}