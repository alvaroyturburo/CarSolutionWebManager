package com.empresas.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class TipoEmpresaDAO extends ClaseDAO{

	@SuppressWarnings("unchecked")
	public List<TipoEmpresa> mostrarTipoEmpresa(){
		List<TipoEmpresa> retorno = new ArrayList<TipoEmpresa>();
		try {
			Query query = getEntityManager().createNamedQuery("TipoEmpresa.findAll");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			retorno = (List<TipoEmpresa>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}
}
