package com.empresas.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the datousuario database table.
 * 
 */
@Entity
@NamedQuery(name="Datousuario.findAll", query="SELECT d FROM Datousuario d")
public class Datousuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_datoUsuario;

	private String contrasena;

	private String estado;

	private String usuario;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuarioBean;

	public Datousuario() {
	}

	public int getId_datoUsuario() {
		return this.id_datoUsuario;
	}

	public void setId_datoUsuario(int id_datoUsuario) {
		this.id_datoUsuario = id_datoUsuario;
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioBean() {
		return this.usuarioBean;
	}

	public void setUsuarioBean(Usuario usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

}