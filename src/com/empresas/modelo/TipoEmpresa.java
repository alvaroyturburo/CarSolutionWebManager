package com.empresas.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the tipo_empresa database table.
 * 
 */
@Entity
@Table(name="tipo_empresa")
@NamedQueries({
@NamedQuery(name="TipoEmpresa.findAll", query="SELECT t FROM TipoEmpresa t")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class TipoEmpresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtipo_empresa")
	@Getter @Setter private int idtipoEmpresa;

	@Column(name="descripcion_tipo_empresa")
	@Getter @Setter private String descripcionTipoEmpresa;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to Empresa
	@OneToMany(mappedBy="tipoEmpresa", cascade=CascadeType.ALL)
	@Getter @Setter private List<Empresa> empresas;

	public Empresa addEmpresa(Empresa empresa) {
		getEmpresas().add(empresa);
		empresa.setTipoEmpresa(this);

		return empresa;
	}

	public Empresa removeEmpresa(Empresa empresa) {
		getEmpresas().remove(empresa);
		empresa.setTipoEmpresa(null);

		return empresa;
	}

}