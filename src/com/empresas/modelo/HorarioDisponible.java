package com.empresas.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class HorarioDisponible {
	@Getter @Setter private Horario horario;
	@Getter @Setter private boolean seleccionado;
}
