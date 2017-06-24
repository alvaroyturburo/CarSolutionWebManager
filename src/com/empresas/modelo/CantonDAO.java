package com.empresas.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class CantonDAO extends ClaseDAO{

	@SuppressWarnings("unchecked")
	public List<Canton> mostrarCantones(){
		List<Canton> retorno = new ArrayList<Canton>();
		try {
			Query query = getEntityManager().createNamedQuery("Canton.findAll");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			retorno = (List<Canton>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Canton> mostrarCantonxProvincias(String nombre){
		List<Canton> retorno = new ArrayList<Canton>();
		String patron = nombre;

		if (nombre == null || nombre.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron + "%";
		}
		try {
			Query query = getEntityManager().createNamedQuery("Canton.buscarPorProvincia");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					query.setParameter("nombre", patron);
			retorno = (List<Canton>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}
}
