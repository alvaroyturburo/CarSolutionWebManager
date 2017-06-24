package com.empresas.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class ProvinciaDAO extends ClaseDAO{

	@SuppressWarnings("unchecked")
	public List<Provincia> mostrarProvincias(){
		List<Provincia> retorno = new ArrayList<Provincia>();
		try {
			Query query = getEntityManager().createNamedQuery("Provincia.findAll");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			retorno = (List<Provincia>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Provincia> mostrarProvinciasxPais(String nombre){
		List<Provincia> retorno = new ArrayList<Provincia>();
		String patron = nombre;

		if (nombre == null || nombre.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron + "%";
		}
		try {
			Query query = getEntityManager().createNamedQuery("Provincia.buscarPorPais");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					query.setParameter("nombre", patron);
			retorno = (List<Provincia>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}
}
