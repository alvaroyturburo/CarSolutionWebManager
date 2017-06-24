package com.empresas.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ServicioDisponible {
	@Getter @Setter private Servicio servicio;
	@Getter @Setter private boolean seleccionado;
}
