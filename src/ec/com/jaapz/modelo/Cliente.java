package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@Table(name="cliente")
@NamedQueries({
	@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c order by c.idCliente"),
	@NamedQuery(name="Cliente.bucarPatron", query="SELECT c FROM Cliente c where (lower(c.apellidos) like lower(:patron)  or lower(c.nombres) like lower(:patron))"
			+ "  and c.estado <> 'E' order by c.idCliente")
})
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cliente")
	private Integer idCliente;

	private String apellidos;

	private String cedula;

	private String direccion;

	private String email;

	private String estado;

	private byte[] foto;

	private String genero;

	private String nombres;

	private String telefono;

	@Column(name="usuario_crea")
	private Integer usuarioCrea;

	//bi-directional many-to-one association to CuentaCliente
	@OneToMany(mappedBy="cliente")
	private List<CuentaCliente> cuentaClientes;

	//bi-directional many-to-one association to Inspeccion
	@OneToMany(mappedBy="cliente")
	private List<Inspeccion> inspeccions;

	public Cliente() {
	}

	public Integer getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
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

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Integer getUsuarioCrea() {
		return this.usuarioCrea;
	}

	public void setUsuarioCrea(Integer usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}

	public List<CuentaCliente> getCuentaClientes() {
		return this.cuentaClientes;
	}

	public void setCuentaClientes(List<CuentaCliente> cuentaClientes) {
		this.cuentaClientes = cuentaClientes;
	}

	public CuentaCliente addCuentaCliente(CuentaCliente cuentaCliente) {
		getCuentaClientes().add(cuentaCliente);
		cuentaCliente.setCliente(this);

		return cuentaCliente;
	}

	public CuentaCliente removeCuentaCliente(CuentaCliente cuentaCliente) {
		getCuentaClientes().remove(cuentaCliente);
		cuentaCliente.setCliente(null);

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
		inspeccion.setCliente(this);

		return inspeccion;
	}

	public Inspeccion removeInspeccion(Inspeccion inspeccion) {
		getInspeccions().remove(inspeccion);
		inspeccion.setCliente(null);

		return inspeccion;
	}

}