package com.empresas.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the pais database table.
 * 
 */
@Entity
@Table(name="pais")
@NamedQueries({
@NamedQuery(name="Pai.findAll", query="SELECT p FROM Pai p")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class Pai implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pais")
	@Getter @Setter private int idPais;

	@Getter @Setter private String estado;

	@Column(name="nombre_pais")
	@Getter @Setter private String nombrePais;

	//bi-directional many-to-one association to Provincia
	@OneToMany(mappedBy="pai", cascade=CascadeType.ALL)
	@Getter @Setter private List<Provincia> provincias;

	public Provincia addProvincia(Provincia provincia) {
		getProvincias().add(provincia);
		provincia.setPai(this);

		return provincia;
	}

	public Provincia removeProvincia(Provincia provincia) {
		getProvincias().remove(provincia);
		provincia.setPai(null);

		return provincia;
	}

}