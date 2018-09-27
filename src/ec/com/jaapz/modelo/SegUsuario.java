package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the seg_usuario database table.
 * 
 */
@Entity
@Table(name="seg_usuario")
@NamedQueries({
	@NamedQuery(name="SegUsuario.findAll", query="SELECT u FROM SegUsuario u"),
	@NamedQuery(name="SegUsuario.buscarPatron", 
	            query="SELECT u FROM SegUsuario u "
	            		+ "WHERE u.usuario = (:usuario) AND u.clave = (:clave) and u.estado = 'A'"),
	@NamedQuery(name="SegUsuario.validarUsuario", 
    query="SELECT u FROM SegUsuario u "
    		+ "WHERE u.usuario = (:usuario) AND u.idUsuario <> (:idUsuario) and u.estado = 'A'"),
	@NamedQuery(name="SegUsuario.buscarInspeccion", query="SELECT u FROM SegUsuario u where u.segPerfil.idPerfil = 3")
})
public class SegUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Integer idUsuario;

	private String apellidos;

	private String cargo;

	private String cedula;

	private String clave;

	private String direccion;
	
	private String nuevo;

	private String estado;

	private byte[] foto;

	private String nombres;

	private String telefono;

	private String usuario;

	//bi-directional many-to-one association to ResponsableLectura
	@OneToMany(mappedBy="segUsuario")
	private List<ResponsableLectura> responsableLecturas;

	//bi-directional many-to-one association to SegPerfil
	@ManyToOne
	@JoinColumn(name="id_perfil")
	private SegPerfil segPerfil;

	public SegUsuario() {
	}

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<ResponsableLectura> getResponsableLecturas() {
		return this.responsableLecturas;
	}

	public void setResponsableLecturas(List<ResponsableLectura> responsableLecturas) {
		this.responsableLecturas = responsableLecturas;
	}

	public ResponsableLectura addResponsableLectura(ResponsableLectura responsableLectura) {
		getResponsableLecturas().add(responsableLectura);
		responsableLectura.setSegUsuario(this);

		return responsableLectura;
	}

	public ResponsableLectura removeResponsableLectura(ResponsableLectura responsableLectura) {
		getResponsableLecturas().remove(responsableLectura);
		responsableLectura.setSegUsuario(null);

		return responsableLectura;
	}

	public SegPerfil getSegPerfil() {
		return this.segPerfil;
	}

	public void setSegPerfil(SegPerfil segPerfil) {
		this.segPerfil = segPerfil;
	}

	public String getNuevo() {
		return nuevo;
	}

	public void setNuevo(String nuevo) {
		this.nuevo = nuevo;
	}

}