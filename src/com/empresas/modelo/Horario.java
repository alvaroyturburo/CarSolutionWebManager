package com.empresas.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;


/**
 * The persistent class for the horario database table.
 * 
 */
@Entity
@Table(name="horario")
@NamedQueries({
@NamedQuery(name="Horario.findAll", query="SELECT h FROM Horario h")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class Horario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_horario")
	@Getter @Setter private int idHorario;

	@Getter @Setter private String estado;

	@Getter @Setter private int habilitar;

	@Column(name="hora_fin")
	@Getter @Setter private Time horaFin;

	@Column(name="hora_inicio")
	@Getter @Setter private Time horaInicio;
	
	public String getDia(){
		if(habilitar==1){
			return "Lunes";
		}else{
			if(habilitar==2){
				return "Martes";
			}else{
				if(habilitar==3){
					return "Miercoles";
				}else{
					if(habilitar==4){
						return "Jueves";
					}else{
						if(habilitar==5){
							return "Viernes";
						}else{
							if(habilitar==6){
								return "Sabado";
							}else{
								return "Domingo";
							}
						}
					}
				}
			}
		}
	}
	


}