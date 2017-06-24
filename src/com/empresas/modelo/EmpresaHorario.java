package com.empresas.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the empresa_horario database table.
 * 
 */
@Entity
@Table(name="empresa_horario")
@NamedQueries({
@NamedQuery(name="EmpresaHorario.findAll", query="SELECT e FROM EmpresaHorario e"),
@NamedQuery(name="EmpresaHorario.findIdEmpresa", query="SELECT e FROM EmpresaHorario e WHERE e.empresa.idEmpresa = :id")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class EmpresaHorario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idempresa_horario")
	@Getter @Setter private int idempresaHorario;

	@Getter @Setter private String dia;

	@Getter @Setter private String estado;

	@Column(name="hora_fin")
	@Getter @Setter private String horaFin;

	@Column(name="hora_inicio")
	@Getter @Setter private String horaInicio;

	//bi-directional many-to-one association to Empresa
	@ManyToOne
	@JoinColumn(name="id_empresa")
	@Getter @Setter private Empresa empresa;
}