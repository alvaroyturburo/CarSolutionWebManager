package com.empresas.control;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class index {

	@Getter @Setter private String formularioActual;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		log.info("Administración");
	}
	
	@Command
	@NotifyChange("formularioActual")
	public void seleccionMenu(@BindingParam("direccion") String direccion) {
		if(direccion.equals("empresas")){
			setFormularioActual("Empresa/listaEmpresas.zul");
		}else if(direccion.equals("servicios")){
			setFormularioActual("");
		}

	}
	
	/*@Command
	public void salir(){
		Session session;
		session=Sessions.getCurrent();
		session.invalidate();
		Executions.getCurrent().sendRedirect("../login.zul");
	}*/
}
