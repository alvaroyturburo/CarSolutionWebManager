package com.empresas.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class EmpresaDAO extends ClaseDAO{
	
	@SuppressWarnings("unchecked")
	public List<Empresa> buscarUsuarioPorNombre(String nombre){
		List<Empresa> retorno = new ArrayList<Empresa>();
		String patron = nombre;

		if (nombre == null || nombre.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron + "%";
		}
		try {
			Query query = getEntityManager().createNamedQuery("Empresa.buscarPorNombre");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					query.setParameter("nombre", patron);
			retorno = (List<Empresa>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}
}
