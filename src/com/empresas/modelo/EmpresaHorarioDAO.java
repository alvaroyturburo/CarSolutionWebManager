package com.empresas.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class EmpresaHorarioDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<EmpresaHorario> listaHorariosAgregados(int id){
		List<EmpresaHorario> retorno = new ArrayList<EmpresaHorario>();
		try {
			Query query = getEntityManager().createNamedQuery("EmpresaHorario.findIdEmpresa");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					query.setParameter("id", id);
			retorno = (List<EmpresaHorario>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}

}
