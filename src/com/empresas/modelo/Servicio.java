package com.empresas.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the servicios database table.
 * 
 */
@Entity
@Table(name="servicios")
@NamedQueries({
@NamedQuery(name="Servicio.findAll", query="SELECT s FROM Servicio s"),
@NamedQuery(name="Servicio.findName", query="SELECT s FROM Servicio s Where s.descripcionServicio = :nombre")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class Servicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_servicios")
	@Getter @Setter private int idServicios;

	@Column(name="descripcion_servicio")
	@Getter @Setter private String descripcionServicio;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to EmpresaServicio
	@OneToMany(mappedBy="servicio", cascade=CascadeType.ALL)
	@Getter @Setter private List<EmpresaServicio> empresaServicios;

	public EmpresaServicio addEmpresaServicio(EmpresaServicio empresaServicio) {
		getEmpresaServicios().add(empresaServicio);
		empresaServicio.setServicio(this);

		return empresaServicio;
	}

	public EmpresaServicio removeEmpresaServicio(EmpresaServicio empresaServicio) {
		getEmpresaServicios().remove(empresaServicio);
		empresaServicio.setServicio(null);

		return empresaServicio;
	}

}