package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the apertura_lectura database table.
 * 
 */
@Entity
@Table(name="apertura_lectura")
@NamedQueries({
		@NamedQuery(name="AperturaLectura.findAll", query="SELECT a FROM AperturaLectura a where a.estado = 'A'"),
		@NamedQuery(name="AperturaLectura.buscarCiclo", query="SELECT a FROM AperturaLectura a where a.estado = 'A' and a.estadoApertura = 'EN PROCESO'")
})
public class AperturaLectura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_apertura")
	private Integer idApertura;

	private String estado;

	@Column(name="estado_apertura")
	private String estadoApertura;

	private Timestamp fecha;

	private String observacion;

	@Column(name="usuario_crea")
	private Integer usuarioCrea;

	//bi-directional many-to-one association to Anio
	@ManyToOne
	@JoinColumn(name="id_anio")
	private Anio anio;

	//bi-directional many-to-one association to Me
	@ManyToOne
	@JoinColumn(name="id_mes")
	private Me me;

	//bi-directional many-to-one association to Planilla
	@OneToMany(mappedBy="aperturaLectura")
	private List<Planilla> planillas;

	//bi-directional many-to-one association to ResponsableLectura
	@OneToMany(mappedBy="aperturaLectura")
	private List<ResponsableLectura> responsableLecturas;

	public AperturaLectura() {
	}

	public Integer getIdApertura() {
		return this.idApertura;
	}

	public void setIdApertura(Integer idApertura) {
		this.idApertura = idApertura;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoApertura() {
		return this.estadoApertura;
	}

	public void setEstadoApertura(String estadoApertura) {
		this.estadoApertura = estadoApertura;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
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

	public Anio getAnio() {
		return this.anio;
	}

	public void setAnio(Anio anio) {
		this.anio = anio;
	}

	public Me getMe() {
		return this.me;
	}

	public void setMe(Me me) {
		this.me = me;
	}

	public List<Planilla> getPlanillas() {
		return this.planillas;
	}

	public void setPlanillas(List<Planilla> planillas) {
		this.planillas = planillas;
	}

	public Planilla addPlanilla(Planilla planilla) {
		getPlanillas().add(planilla);
		planilla.setAperturaLectura(this);

		return planilla;
	}

	public Planilla removePlanilla(Planilla planilla) {
		getPlanillas().remove(planilla);
		planilla.setAperturaLectura(null);

		return planilla;
	}

	public List<ResponsableLectura> getResponsableLecturas() {
		return this.responsableLecturas;
	}

	public void setResponsableLecturas(List<ResponsableLectura> responsableLecturas) {
		this.responsableLecturas = responsableLecturas;
	}

	public ResponsableLectura addResponsableLectura(ResponsableLectura responsableLectura) {
		getResponsableLecturas().add(responsableLectura);
		responsableLectura.setAperturaLectura(this);

		return responsableLectura;
	}

	public ResponsableLectura removeResponsableLectura(ResponsableLectura responsableLectura) {
		getResponsableLecturas().remove(responsableLectura);
		responsableLectura.setAperturaLectura(null);

		return responsableLectura;
	}

}