package co.com.bnpparibas.cardif.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({ @NamedQuery(name = "Persona.login", query = "SELECT u FROM Usuario u JOIN FETCH u.caja WHERE u.usuario = :usuario AND u.contrasenia = :contrasenia") })
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable, BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String login = "Persona.login";

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombres", nullable = false, length = 80)
	private String nombres;

	@Column(name = "apellidos", nullable = false, length = 80)
	private String apellidos;

	@Column(name = "usuario", nullable = false, length = 40, unique = true)
	private String usuario;

	@Column(name = "contrasenia", nullable = false, length = 40)
	private String contrasenia;

	@Column(name = "telefono_uno", nullable = false, length = 20)
	private String telefonoUno;
	
	@Column(name = "telefono_dos", length = 20)
	private String telefonoDos;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "caja", nullable=false)
	private Caja caja;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_actualizacion", nullable = false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", nullable = false)
	private Date fechaCreacion;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTelefonoUno() {
		return telefonoUno;
	}

	public void setTelefonoUno(String telefonoUno) {
		this.telefonoUno = telefonoUno;
	}

	public String getTelefonoDos() {
		return telefonoDos;
	}

	public void setTelefonoDos(String telefonoDos) {
		this.telefonoDos = telefonoDos;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return this.contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	@Override
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaActualizacion;
	}

	@Override
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	@Override
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	@Override
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}
}
