package com.empresas.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the provincia database table.
 * 
 */
@Entity
@Table(name="provincia")
@NamedQueries({
@NamedQuery(name="Provincia.findAll", query="SELECT p FROM Provincia p"),
@NamedQuery(name="Provincia.buscarPorPais", query="SELECT p FROM Provincia p where p.pai.nombrePais like :nombre")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class Provincia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_provincia")
	@Getter @Setter private int idProvincia;

	@Getter @Setter private String estado;

	@Column(name="nombre_provincia")
	@Getter @Setter private String nombreProvincia;

	//bi-directional many-to-one association to Canton
	@OneToMany(mappedBy="provincia", cascade=CascadeType.ALL)
	@Getter @Setter private List<Canton> cantons;

	//bi-directional many-to-one association to Pai
	@ManyToOne
	@JoinColumn(name="id_pais")
	@Getter @Setter private Pai pai;

	public Canton addCanton(Canton canton) {
		getCantons().add(canton);
		canton.setProvincia(this);

		return canton;
	}

	public Canton removeCanton(Canton canton) {
		getCantons().remove(canton);
		canton.setProvincia(null);

		return canton;
	}


}