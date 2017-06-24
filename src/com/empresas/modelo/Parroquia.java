package com.empresas.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the parroquia database table.
 * 
 */
@Entity
@Table(name="parroquia")
@NamedQueries({
@NamedQuery(name="Parroquia.findAll", query="SELECT p FROM Parroquia p"),
@NamedQuery(name="Parroquia.buscarPorCanton", query="SELECT p FROM Parroquia p where p.canton.nombreCanton like :nombre")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class Parroquia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_parroquia")
	@Getter @Setter private int idParroquia;

	@Getter @Setter private String estado;

	@Column(name="nombre_parroquia")
	@Getter @Setter private String nombreParroquia;

	//bi-directional many-to-one association to Empresa
	@OneToMany(mappedBy="parroquia", cascade=CascadeType.ALL)
	@Getter @Setter private List<Empresa> empresas;

	//bi-directional many-to-one association to Canton
	@ManyToOne
	@JoinColumn(name="id_canton")
	@Getter @Setter private Canton canton;

	public Empresa addEmpresa(Empresa empresa) {
		getEmpresas().add(empresa);
		empresa.setParroquia(this);

		return empresa;
	}

	public Empresa removeEmpresa(Empresa empresa) {
		getEmpresas().remove(empresa);
		empresa.setParroquia(null);

		return empresa;
	}

	
}