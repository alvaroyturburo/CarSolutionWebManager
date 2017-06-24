package com.empresas.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private int idUsuario;

	private String estado;

	//bi-directional many-to-one association to Datousuario
	@OneToMany(mappedBy="usuarioBean")
	private List<Datousuario> datousuarios;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="id_persona")
	private Persona persona;

	//bi-directional many-to-one association to Tipousuario
	@ManyToOne
	@JoinColumn(name="id_tipoUsuario")
	private Tipousuario tipousuario;

	public Usuario() {
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Datousuario> getDatousuarios() {
		return this.datousuarios;
	}

	public void setDatousuarios(List<Datousuario> datousuarios) {
		this.datousuarios = datousuarios;
	}

	public Datousuario addDatousuario(Datousuario datousuario) {
		getDatousuarios().add(datousuario);
		datousuario.setUsuarioBean(this);

		return datousuario;
	}

	public Datousuario removeDatousuario(Datousuario datousuario) {
		getDatousuarios().remove(datousuario);
		datousuario.setUsuarioBean(null);

		return datousuario;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Tipousuario getTipousuario() {
		return this.tipousuario;
	}

	public void setTipousuario(Tipousuario tipousuario) {
		this.tipousuario = tipousuario;
	}

}