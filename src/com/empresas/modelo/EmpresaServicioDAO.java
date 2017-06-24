package com.empresas.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class EmpresaServicioDAO extends ClaseDAO{
	
	@SuppressWarnings("unchecked")
	public List<EmpresaServicio> listaServicioAgregados(int id){
		List<EmpresaServicio> retorno = new ArrayList<EmpresaServicio>();
		try {
			Query query = getEntityManager().createNamedQuery("EmpresaServicio.findId");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					query.setParameter("id", id);
			retorno = (List<EmpresaServicio>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}
	
	@SuppressWarnings("unchecked")
	public EmpresaServicio ServicioAgregados( int idservicio, int idempresa){
		EmpresaServicio retorno = new EmpresaServicio();
		try {
			Query query = getEntityManager().createNamedQuery("EmpresaServicio.findIdService");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					query.setParameter("id", idservicio);
					query.setParameter("idE", idempresa);
			retorno =  (EmpresaServicio) query.getSingleResult();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}

}
