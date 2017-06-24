package com.empresas.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipousuario database table.
 * 
 */
@Entity
@NamedQuery(name="Tipousuario.findAll", query="SELECT t FROM Tipousuario t")
public class Tipousuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_tipoUsuario;

	private String descripcion;

	private String estado;

	//bi-directional many-to-one association to Permiso
	@OneToMany(mappedBy="tipousuario")
	private List<Permiso> permisos;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="tipousuario")
	private List<Usuario> usuarios;

	public Tipousuario() {
	}

	public int getId_tipoUsuario() {
		return this.id_tipoUsuario;
	}

	public void setId_tipoUsuario(int id_tipoUsuario) {
		this.id_tipoUsuario = id_tipoUsuario;
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

	public List<Permiso> getPermisos() {
		return this.permisos;
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}

	public Permiso addPermiso(Permiso permiso) {
		getPermisos().add(permiso);
		permiso.setTipousuario(this);

		return permiso;
	}

	public Permiso removePermiso(Permiso permiso) {
		getPermisos().remove(permiso);
		permiso.setTipousuario(null);

		return permiso;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setTipousuario(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setTipousuario(null);

		return usuario;
	}

}