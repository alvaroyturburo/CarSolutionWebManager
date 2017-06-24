package com.empresas.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class ServicioDAO extends ClaseDAO{

	@SuppressWarnings("unchecked")
	public List<Servicio> mostrarServicios(){
		List<Servicio> retorno = new ArrayList<Servicio>();
		try {
			Query query = getEntityManager().createNamedQuery("Servicio.findAll");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			retorno = (List<Servicio>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Servicio mostrarServiciosName(String name){
		Servicio retorno = new Servicio();
		try {
			Query query = getEntityManager().createNamedQuery("Servicio.findName");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					query.setParameter("nombre", name);
			retorno = (Servicio) query.getSingleResult();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}
}
