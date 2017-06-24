package com.empresas.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class ParroquiaDAO extends ClaseDAO{
	
	@SuppressWarnings("unchecked")
	public List<Parroquia> mostrarParroquias(){
		List<Parroquia> retorno = new ArrayList<Parroquia>();
		try {
			Query query = getEntityManager().createNamedQuery("Parroquia.findAll");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			retorno = (List<Parroquia>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Parroquia> mostrarParroquiaxCantones(String nombre){
		List<Parroquia> retorno = new ArrayList<Parroquia>();
		String patron = nombre;

		if (nombre == null || nombre.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron + "%";
		}
		try {
			Query query = getEntityManager().createNamedQuery("Parroquia.buscarPorCanton");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					query.setParameter("nombre", patron);
			retorno = (List<Parroquia>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}

}
