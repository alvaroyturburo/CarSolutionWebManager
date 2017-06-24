package com.empresas.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.empresas.modelo.Empresa;
import com.empresas.modelo.EmpresaDAO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;


@Log
public class ListaEmpresas {
	
	@Getter @Setter private String textoBuscar;
	@Getter @Setter private Empresa empresaSeleccionado;
	@Getter @Setter private List<Empresa> empresas;
	private EmpresaDAO empresaDao = new EmpresaDAO();

		
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		log.info("Administración de Empresas");
	}
	
	@GlobalCommand("ListaEmpresas.buscar")
	@Command
	@NotifyChange({"empresas", "empresaSeleccionado"})
	public void buscar(){
		if (empresas != null) {
			empresas = null; 
		}
		empresas = empresaDao.buscarUsuarioPorNombre(textoBuscar);
		empresaSeleccionado = null; 
		
	}
	
	@Command
	@NotifyChange({""})
	public void nuevoEmpresa(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Empresa", new Empresa());
		Window ventanaCargar = (Window) Executions.createComponents("Empresa/edicionEmpresas.zul", null, params);
		ventanaCargar.doModal();
	}
	
	@Command
	@NotifyChange({""})
	public void editarEmpresa(){
		if(getEmpresaSeleccionado() == null){
			Messagebox.show("Debe seleccionar una Empresa","Administración",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Empresa", empresaSeleccionado);
		Window ventanaCargar = (Window) Executions.createComponents("Empresa/edicionEmpresas.zul", null, params);
		ventanaCargar.doModal();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	@NotifyChange({""})
	public void eliminarEmpresa(){
		if(getEmpresaSeleccionado() == null){
			Messagebox.show("Debe seleccionar una Empresa","Administración",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
		Messagebox.show("Desea eliminar el registro seleccionado? Recuerde que esta acción no puede ser revertida y"
				+ "puede ocasionar un mal funcionamiento de la aplicación", "Confirmación de Eliminación", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					
					try {
						empresaDao.getEntityManager().getTransaction().begin();
						empresaSeleccionado.setEstado("I");
						empresaSeleccionado = empresaDao.getEntityManager().merge(empresaSeleccionado);
						empresaDao.getEntityManager().getTransaction().commit();	
						Clients.showNotification("Transaccion ejecutada con exito");

						BindUtils.postGlobalCommand(null, null, "ListaEmpresas.buscar", null);
					} catch (Exception e) {
						e.printStackTrace();
						empresaDao.getEntityManager().getTransaction().rollback();
					}	
					
				}
			}
		});
	}
	
}
