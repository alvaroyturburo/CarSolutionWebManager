package com.empresas.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the empresa_servicio database table.
 * 
 */
@Entity
@Table(name="empresa_servicio")
@NamedQueries({
@NamedQuery(name="EmpresaServicio.findAll", query="SELECT e FROM EmpresaServicio e"),
@NamedQuery(name="EmpresaServicio.findId", query="SELECT e FROM EmpresaServicio e where e.empresa.idEmpresa = :id"),
@NamedQuery(name="EmpresaServicio.findIdService", query="SELECT e FROM EmpresaServicio e where e.servicio.idServicios = :id and e.empresa.idEmpresa = :idE")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class EmpresaServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idempresa_servicio")
	@Getter @Setter private int idempresaServicio;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to Empresa
	@ManyToOne
	@JoinColumn(name="id_empresa")
	@Getter @Setter private Empresa empresa;

	//bi-directional many-to-one association to Servicio
	@ManyToOne
	@JoinColumn(name="id_servicios")
	@Getter @Setter private Servicio servicio;

}