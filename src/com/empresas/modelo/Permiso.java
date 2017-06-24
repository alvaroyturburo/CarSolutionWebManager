package com.empresas.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the permisos database table.
 * 
 */
@Entity
@Table(name="permisos")
@NamedQuery(name="Permiso.findAll", query="SELECT p FROM Permiso p")
public class Permiso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_permisos")
	private int idPermisos;

	private String crear;

	private String descripcion;

	private String editar;

	private String eliminar;

	private String estado;

	//bi-directional many-to-one association to Tipousuario
	@ManyToOne
	@JoinColumn(name="id_tipoUsuario")
	private Tipousuario tipousuario;

	public Permiso() {
	}

	public int getIdPermisos() {
		return this.idPermisos;
	}

	public void setIdPermisos(int idPermisos) {
		this.idPermisos = idPermisos;
	}

	public String getCrear() {
		return this.crear;
	}

	public void setCrear(String crear) {
		this.crear = crear;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEditar() {
		return this.editar;
	}

	public void setEditar(String editar) {
		this.editar = editar;
	}

	public String getEliminar() {
		return this.eliminar;
	}

	public void setEliminar(String eliminar) {
		this.eliminar = eliminar;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Tipousuario getTipousuario() {
		return this.tipousuario;
	}

	public void setTipousuario(Tipousuario tipousuario) {
		this.tipousuario = tipousuario;
	}

}