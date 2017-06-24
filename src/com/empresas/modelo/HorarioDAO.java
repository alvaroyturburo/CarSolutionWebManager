package com.empresas.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class HorarioDAO extends ClaseDAO{

	@SuppressWarnings("unchecked")
	public List<Horario> mostrarHorarios(){
		List<Horario> retorno = new ArrayList<Horario>();
		try {
			Query query = getEntityManager().createNamedQuery("Horario.findAll");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			retorno = (List<Horario>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}
}
