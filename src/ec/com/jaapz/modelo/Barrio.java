package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the barrio database table.
 * 
 */
@Entity
@Table(name="barrio")
@NamedQueries({
	@NamedQuery(name="Barrio.findAll", query="SELECT b FROM Barrio b"),
	@NamedQuery(name="Barrio.findAllActivo", query="SELECT b FROM Barrio b where b.estado = 'A'"),
	@NamedQuery(name="Barrio.findBarrios", query="SELECT b FROM Barrio b where lower(b.nombre) like lower(:patron) "
			+ "and b.estado = 'A' order by b.idBarrio asc")
})
public class Barrio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_barrio")
	private Integer idBarrio;

	private String descripcion;

	private String estado;

	private String nombre;

	//bi-directional many-to-one association to CuentaCliente
	@OneToMany(mappedBy="barrio")
	private List<CuentaCliente> cuentaClientes;

	//bi-directional many-to-one association to Inspeccion
	@OneToMany(mappedBy="barrio")
	private List<Inspeccion> inspeccions;

	//bi-directional many-to-one association to ResponsableLectura
	@OneToMany(mappedBy="barrio")
	private List<ResponsableLectura> responsableLecturas;

	public Barrio() {
	}

	public Integer getIdBarrio() {
		return this.idBarrio;
	}

	public void setIdBarrio(Integer idBarrio) {
		this.idBarrio = idBarrio;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<CuentaCliente> getCuentaClientes() {
		return this.cuentaClientes;
	}

	public void setCuentaClientes(List<CuentaCliente> cuentaClientes) {
		this.cuentaClientes = cuentaClientes;
	}

	public CuentaCliente addCuentaCliente(CuentaCliente cuentaCliente) {
		getCuentaClientes().add(cuentaCliente);
		cuentaCliente.setBarrio(this);

		return cuentaCliente;
	}

	public CuentaCliente removeCuentaCliente(CuentaCliente cuentaCliente) {
		getCuentaClientes().remove(cuentaCliente);
		cuentaCliente.setBarrio(null);

		return cuentaCliente;
	}

	public List<Inspeccion> getInspeccions() {
		return this.inspeccions;
	}

	public void setInspeccions(List<Inspeccion> inspeccions) {
		this.inspeccions = inspeccions;
	}

	public Inspeccion addInspeccion(Inspeccion inspeccion) {
		getInspeccions().add(inspeccion);
		inspeccion.setBarrio(this);

		return inspeccion;
	}

	public Inspeccion removeInspeccion(Inspeccion inspeccion) {
		getInspeccions().remove(inspeccion);
		inspeccion.setBarrio(null);

		return inspeccion;
	}

	public List<ResponsableLectura> getResponsableLecturas() {
		return this.responsableLecturas;
	}

	public void setResponsableLecturas(List<ResponsableLectura> responsableLecturas) {
		this.responsableLecturas = responsableLecturas;
	}

	public ResponsableLectura addResponsableLectura(ResponsableLectura responsableLectura) {
		getResponsableLecturas().add(responsableLectura);
		responsableLectura.setBarrio(this);

		return responsableLectura;
	}

	public ResponsableLectura removeResponsableLectura(ResponsableLectura responsableLectura) {
		getResponsableLecturas().remove(responsableLectura);
		responsableLectura.setBarrio(null);

		return responsableLectura;
	}

	@Override
	public String toString() {
		return this.nombre;
	}

}