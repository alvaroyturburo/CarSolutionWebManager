package com.empresas.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the canton database table.
 * 
 */
@Entity
@Table(name="canton")
@NamedQueries({
@NamedQuery(name="Canton.findAll", query="SELECT c FROM Canton c"),
@NamedQuery(name="Canton.buscarPorProvincia", query="SELECT c FROM Canton c where c.provincia.nombreProvincia like :nombre")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class Canton implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_canton")
	@Getter @Setter private int idCanton;

	@Getter @Setter private String estado;

	@Column(name="nombre_canton")
	@Getter @Setter private String nombreCanton;

	//bi-directional many-to-one association to Provincia
	@ManyToOne
	@JoinColumn(name="id_provincia")
	@Getter @Setter private Provincia provincia;

	//bi-directional many-to-one association to Parroquia
	@OneToMany(mappedBy="canton", cascade=CascadeType.ALL)
	@Getter @Setter private List<Parroquia> parroquias;

	public Parroquia addParroquia(Parroquia parroquia) {
		getParroquias().add(parroquia);
		parroquia.setCanton(this);

		return parroquia;
	}

	public Parroquia removeParroquia(Parroquia parroquia) {
		getParroquias().remove(parroquia);
		parroquia.setCanton(null);

		return parroquia;
	}

}