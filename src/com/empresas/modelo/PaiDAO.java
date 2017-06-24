package com.empresas.modelo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import lombok.extern.java.Log;

@Log
public class PaiDAO extends ClaseDAO{

	@SuppressWarnings("unchecked")
	public List<Pai> mostrarPaises(){
		List<Pai> retorno = new ArrayList<Pai>();
		try {
			Query query = getEntityManager().createNamedQuery("Pai.findAll");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			retorno = (List<Pai>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}
}
