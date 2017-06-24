package com.empresas.control;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.event.MapMouseEvent;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

import com.empresas.modelo.Canton;
import com.empresas.modelo.CantonDAO;
import com.empresas.modelo.Empresa;
import com.empresas.modelo.EmpresaDAO;
import com.empresas.modelo.EmpresaHorario;
import com.empresas.modelo.EmpresaHorarioDAO;
import com.empresas.modelo.EmpresaServicio;
import com.empresas.modelo.EmpresaServicioDAO;
import com.empresas.modelo.Horario;
import com.empresas.modelo.HorarioDAO;
import com.empresas.modelo.Pai;
import com.empresas.modelo.PaiDAO;
import com.empresas.modelo.Parroquia;
import com.empresas.modelo.ParroquiaDAO;
import com.empresas.modelo.Provincia;
import com.empresas.modelo.ProvinciaDAO;
import com.empresas.modelo.Servicio;
import com.empresas.modelo.ServicioDAO;
import com.empresas.modelo.ServicioDisponible;
import com.empresas.modelo.TipoEmpresa;
import com.empresas.modelo.TipoEmpresaDAO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;


@Log
public class EdicionEmpresa {
	
	@Wire private Gmaps gmaps;
	@Wire private Textbox doblelongitude,doblelatitude;
	Gmarker gmarker;
	
	@Wire private Window winEmpresa;	
	@Wire private Button btnUpload, buttonAceptar, btnHorarioEditar, btnHorarioGuardar;
	@Wire private Image imgFoto;
	@Wire private Tab tabAtencion, tabServicios;
	@Wire private Tabpanel tabpanelAtencion, tabpanelServicios;
	@Wire private Label lblNota;
	@Wire private Combobox cmbProvincias, cmbCantones,cmbParroquias;
	private int id_validador;
	@Wire private Checkbox ckL, ckM, ckMI, ckJ, ckV, ckS, ckD;
	@Wire private Timebox tbiL, tbiM, tbiMI, tbiJ, tbiV, tbiS, tbiD, tbfL, tbfM, tbfMI, tbfJ, tbfV, tbfS, tbfD;
	@Wire private Grid gridHorarios;
	
	@Getter @Setter private Empresa empresa;
	@Getter @Setter private Pai paisSeleccionado;
	@Getter @Setter private Provincia provinciaSeleccionado;
	@Getter @Setter private Canton cantonSeleccionado;
	@Getter @Setter private Parroquia parroquiaSeleccionado;
	@Getter @Setter private TipoEmpresa tipoEmpresaSeleccionado;
	//@Getter @Setter private TipoEmpresa tipoEmpresaSeleccionado;
	
	@Getter @Setter private List<ServicioDisponible> compDisponibles;
	@Getter @Setter private List<ServicioDisponible> compRegistrados;
	@Getter @Setter private List<ServicioDisponible> compRegistradosVeri;
	
	@Getter @Setter private List<Pai> paises;
	@Getter @Setter private List<Provincia> provincias;
	@Getter @Setter private List<Canton> cantones;
	@Getter @Setter private List<Parroquia> parroquias;
	@Getter @Setter private List<TipoEmpresa> tiposempresas;
	@Getter @Setter private List<Servicio> servicios;
	@Getter @Setter private List<EmpresaServicio> empresaServicios;
	@Getter @Setter private List<EmpresaHorario> empresaHorarios;
	@Getter @Setter private List<Horario> horarios;
	
	
	
	

	//private EmpresaDAO empresaDao = new EmpresaDAO();
	private PaiDAO paiDao = new PaiDAO();
	private ProvinciaDAO provinciaDao = new ProvinciaDAO();
	private CantonDAO cantonDao = new CantonDAO();
	private ParroquiaDAO parroquiaDao = new ParroquiaDAO();
	private EmpresaDAO empresaDao = new EmpresaDAO();
	private TipoEmpresaDAO tipoEmpresaDao = new TipoEmpresaDAO();
	private ServicioDAO servicioDao = new ServicioDAO();
	private HorarioDAO horarioDao = new HorarioDAO();
	private EmpresaServicioDAO empresaServicioDao = new EmpresaServicioDAO();
	private EmpresaHorarioDAO empresaHorarioDao = new EmpresaHorarioDAO();
	EmpresaServicio empSer = new EmpresaServicio();
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		/*Session session;
		session=Sessions.getCurrent();
		usuario_login = (Usuario) session.getAttribute("_USUARIO_");*/
		empresa = (Empresa) Executions.getCurrent().getArg().get("Empresa");
		id_validador = empresa.getIdEmpresa();
		tiposempresas = tipoEmpresaDao.mostrarTipoEmpresa();
		horarios = horarioDao.mostrarHorarios();
		gmarker = new Gmarker();
		cargarPaises();
		if(id_validador==0){
			tabAtencion.setVisible(false);
			tabServicios.setVisible(false);
			tabpanelAtencion.setVisible(false);
			tabpanelServicios.setVisible(false);
			lblNota.setVisible(true);
		}else{
			servicios = servicioDao.mostrarServicios();
			Double Lat =Double.valueOf(empresa.getLatitud());
			Double Lng = Double.valueOf(empresa.getLongitud());
			gmarker.setLat(Lat);
			gmarker.setLng(Lng);
			gmarker.setParent(gmaps);
			tabAtencion.setVisible(true);
			tabServicios.setVisible(true);
			tabpanelAtencion.setVisible(true);
			tabpanelServicios.setVisible(true);
			lblNota.setVisible(false);
			listaCheckServicio();
			listaCheckHorario();
			btnHorarioEditar.setDisabled(false);
			btnHorarioGuardar.setDisabled(true);
		}
		log.info("Edicion de empresa");
	}
	@GlobalCommand("EdicionEmpresa.actualizarListasHorario")
	public void listaCheckHorario(){
		empresaHorarios = empresaHorarioDao.listaHorariosAgregados(id_validador);
		if(empresaHorarios.size()>0){
			for(EmpresaHorario objeto : empresaHorarios){
				DateFormat hora = new SimpleDateFormat("HH:mm:ss");
				if(objeto.getDia().equals("Lunes")){
					ckL.setChecked(true);
					try {
						Date dateI = hora.parse(objeto.getHoraInicio());
						tbiL.setValue(dateI);
						Date dateF = hora.parse(objeto.getHoraFin());
						tbfL.setValue(dateF);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(objeto.getDia().equals("Martes")){
					ckM.setChecked(true);
					try {
						Date dateI = hora.parse(objeto.getHoraInicio());
						tbiM.setValue(dateI);
						Date dateF = hora.parse(objeto.getHoraFin());
						tbfM.setValue(dateF);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(objeto.getDia().equals("Miercoles")){
					ckMI.setChecked(true);
					try {
						Date dateI = hora.parse(objeto.getHoraInicio());
						tbiMI.setValue(dateI);
						Date dateF = hora.parse(objeto.getHoraFin());
						tbfMI.setValue(dateF);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(objeto.getDia().equals("Jueves")){
					ckJ.setChecked(true);
					try {
						Date dateI = hora.parse(objeto.getHoraInicio());
						tbiJ.setValue(dateI);
						Date dateF = hora.parse(objeto.getHoraFin());
						tbfJ.setValue(dateF);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(objeto.getDia().equals("Viernes")){
					ckV.setChecked(true);
					try {
						Date dateI = hora.parse(objeto.getHoraInicio());
						tbiV.setValue(dateI);
						Date dateF = hora.parse(objeto.getHoraFin());
						tbfV.setValue(dateF);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(objeto.getDia().equals("Sabado")){
					ckS.setChecked(true);
					try {
						Date dateI = hora.parse(objeto.getHoraInicio());
						tbiS.setValue(dateI);
						Date dateF = hora.parse(objeto.getHoraFin());
						tbfS.setValue(dateF);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(objeto.getDia().equals("Domingo")){
					ckD.setChecked(true);
					try {
						Date dateI = hora.parse(objeto.getHoraInicio());
						tbiD.setValue(dateI);
						Date dateF = hora.parse(objeto.getHoraFin());
						tbfD.setValue(dateF);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		}else{
			ckL.setChecked(false);
			ckM.setChecked(false);
			ckMI.setChecked(false);
			ckJ.setChecked(false);
			ckV.setChecked(false);
			ckS.setChecked(false);
			ckD.setChecked(false);
		}
	}
	
	public Constraint getValidaHoraL() {
		return new Constraint() {
			
			@Override
			public void validate(Component comp, Object value){
				Date valor = (Date) value;
								
				if (valor.before(tbiL.getValue())) {
					Clients.showNotification("La hora no debe ser anterior menor a la inicial", "error", tbfL, "end_center",2000,true);
					return;
				}
			}
		};		
	}
	public Constraint getValidaHoraM() {
		return new Constraint() {
			
			@Override
			public void validate(Component comp, Object value){
				Date valor = (Date) value;
								
				if (valor.before(tbiM.getValue())) {
					Clients.showNotification("La hora no debe ser anterior menor a la inicial", "error", tbfM, "end_center",2000,true);
					return;
				}
			}
		};		
	}
	public Constraint getValidaHoraMI() {
		return new Constraint() {
			
			@Override
			public void validate(Component comp, Object value){
				Date valor = (Date) value;
								
				if (valor.before(tbiMI.getValue())) {
					Clients.showNotification("La hora no debe ser anterior menor a la inicial", "error", tbfMI, "end_center",2000,true);
					return;
				}
			}
		};		
	}
	public Constraint getValidaHoraJ() {
		return new Constraint() {
			
			@Override
			public void validate(Component comp, Object value){
				Date valor = (Date) value;
								
				if (valor.before(tbiJ.getValue())) {
					Clients.showNotification("La hora no debe ser anterior menor a la inicial", "error", tbfJ, "end_center",2000,true);
					return;
				}
			}
		};		
	}
	public Constraint getValidaHoraV() {
		return new Constraint() {
			
			@Override
			public void validate(Component comp, Object value){
				Date valor = (Date) value;
								
				if (valor.before(tbiV.getValue())) {
					Clients.showNotification("La hora no debe ser anterior menor a la inicial", "error", tbfV, "end_center",2000,true);
					return;
				}
			}
		};		
	}
	public Constraint getValidaHoraS() {
		return new Constraint() {
			
			@Override
			public void validate(Component comp, Object value){
				Date valor = (Date) value;
								
				if (valor.before(tbiS.getValue())) {
					Clients.showNotification("La hora no debe ser anterior menor a la inicial", "error", tbfS, "end_center",2000,true);
					return;
				}
			}
		};		
	}
	public Constraint getValidaHoraD() {
		return new Constraint() {
			
			@Override
			public void validate(Component comp, Object value){
				Date valor = (Date) value;
								
				if (valor.before(tbiD.getValue())) {
					Clients.showNotification("La hora no debe ser anterior menor a la inicial", "error", tbfD, "end_center",2000,true);
					return;
				}
			}
		};		
	}
	
	@Command
	public void editarHorarios(){
		btnHorarioEditar.setDisabled(true);
		btnHorarioGuardar.setDisabled(false);
	}
	
	@Command
	public void guardarHorarios(){
		
		btnHorarioEditar.setDisabled(false);
		btnHorarioGuardar.setDisabled(true);
	}
	
	
	
	
	@GlobalCommand("EdicionEmpresa.actualizarListasServicio")
	public void listaCheckServicio(){
		empresaServicios = empresaServicioDao.listaServicioAgregados(id_validador);
		if(empresaServicios.size()>0){
			System.out.println("Prueba");
			Collection<String> listOne = new ArrayList<>();
			Collection<String> listTwo = new ArrayList<>();
			compDisponibles = new ArrayList<ServicioDisponible>();
			compRegistrados = new ArrayList<ServicioDisponible>();
			compRegistradosVeri = new ArrayList<ServicioDisponible>();
			for(Servicio servicioV : servicios){
				ServicioDisponible objeto = new ServicioDisponible();
				objeto.setServicio(servicioV);
				objeto.setSeleccionado(false);
				listOne.add(servicioV.getDescripcionServicio());
				compRegistradosVeri.add(objeto);
			}
			for(Servicio servicioV : servicios){
				for(EmpresaServicio emprServi : empresaServicios){
					if(emprServi.getServicio().getIdServicios()== servicioV.getIdServicios()){
					ServicioDisponible objeto = new ServicioDisponible();
					objeto.setServicio(servicioV);
					objeto.setSeleccionado(false);
					listTwo.add(servicioV.getDescripcionServicio());
					compRegistrados.add(objeto);
					}
				}
			}
							
			Collection<String> similar = new HashSet<String>( listOne );
			Collection<String> different = new HashSet<String>();
			different.addAll( listOne );
			different.addAll( listTwo );
			
			similar.retainAll( listTwo );
			different.removeAll( similar );
			//compDisponibles=(List<ServicioDisponible>) different;
			//System.out.printf("One:%s%nTwo:%s%nSimilar:%s%nDifferent:%s%n", listOne, listTwo, similar, different);
			for(String emprServi : different){
				ServicioDisponible objeto = new ServicioDisponible();
				objeto.setServicio(servicioDao.mostrarServiciosName(emprServi));
				objeto.setSeleccionado(false);
				compDisponibles.add(objeto);
			}
			
		}else{
			compDisponibles = new ArrayList<ServicioDisponible>();
			compRegistrados = new ArrayList<ServicioDisponible>();
			for(Servicio servicioV : servicios){
				ServicioDisponible objeto = new ServicioDisponible();
				objeto.setServicio(servicioV);
				objeto.setSeleccionado(false);
				compDisponibles.add(objeto);
			}
		}
	}
	
	
	@Command
	@NotifyChange({"*"})
	public void agregar(){
				
		if(compDisponibles == null){
			Messagebox.show("No es posible realizar esta acción!!","Administración",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
		if(compDisponibles.isEmpty()){
			Messagebox.show("No es posible realizar esta acción!!","Administración",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
		try {
			empresaServicioDao.getEntityManager().getTransaction().begin();
			for(ServicioDisponible serviDis : compDisponibles){
				if(serviDis.isSeleccionado()){
					EmpresaServicio empSer = new EmpresaServicio();
					empSer.setEmpresa(empresa);
					empSer.setServicio(serviDis.getServicio());
					empSer.setEstado("A");
					empresaServicioDao.getEntityManager().persist(empSer);
				}
			}
			empresaServicioDao.getEntityManager().getTransaction().commit();
			Clients.showNotification("Transaccion ejecutada con exito");
			
			BindUtils.postGlobalCommand(null, null, "EdicionEmpresa.actualizarListasServicio", null);
			
		} catch (Exception e) {
			e.printStackTrace();
			empresaServicioDao.getEntityManager().getTransaction().rollback();
		}
	}
	@Command
	@NotifyChange({"*"})
	public void quitar(){
		
		if(compRegistrados == null){
			Messagebox.show("No es posible realizar esta acción!!","Administración",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
		if(compRegistrados.isEmpty()){
			Messagebox.show("No es posible realizar esta acción!!","Administración",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
		try {
			
			for(ServicioDisponible serviDis : compRegistrados){
				if(serviDis.isSeleccionado()){
					
					empSer = empresaServicioDao.ServicioAgregados(serviDis.getServicio().getIdServicios(), empresa.getIdEmpresa());
					
					System.out.println(empSer.getIdempresaServicio());
					empresaServicioDao.getEntityManager().getTransaction().begin();
					
							empSer.setEstado("E");
							
							empresaServicioDao.getEntityManager().merge(empSer);
						
					empresaServicioDao.getEntityManager().getTransaction().commit();
				}
			}
			
			
			BindUtils.postGlobalCommand(null, null, "EdicionEmpresa.actualizarListasServicio", null);
			Clients.showNotification("Transaccion ejecutada con exito");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			empresaServicioDao.getEntityManager().getTransaction().rollback();
		}
	}
	
	
	public void guardarEmpresa(){
		empresa.setEstado("A");
		//empresa.setParroquia(parroquiaSeleccionado);
		try {
			empresaDao.getEntityManager().getTransaction().begin();

			if(empresa.getIdEmpresa() == 0){
				empresaDao.getEntityManager().persist(empresa);
			}else{
				empresa = (Empresa) empresaDao.getEntityManager().merge(empresa);
			}
			
			empresaDao.getEntityManager().getTransaction().commit();
			Clients.showNotification("Empresa registrado!");
			BindUtils.postGlobalCommand(null, null, "ListaEmpresas.buscar", null);
			salir();
		} catch (Exception e) {
			e.printStackTrace();
			empresaDao.getEntityManager().getTransaction().rollback();
			Clients.showNotification("Error en la ejecución", "error", buttonAceptar, "end_center",3);
		}
	}
		
	@Command
	public void guardar(){
		guardarEmpresa();
	}
	
	@Command
	 public void getCoordenadas(@ContextParam(ContextType.TRIGGER_EVENT) MapMouseEvent event){
	  //Messagebox.show("Has hecho clic en las coordenadas: Lat: "+event.getLat()+" Lng: "+event.getLng());
	  gmarker.setLat(event.getLat());
	  gmarker.setLng(event.getLng());
	  gmarker.setParent(gmaps);
	  empresa.setLatitud(""+event.getLat());
	  empresa.setLongitud(""+event.getLng());
	  doblelatitude.setText(empresa.getLatitud());
	  doblelongitude.setText(empresa.getLongitud());
	 }
	
	@Command
	public void salir(){
		winEmpresa.detach();
	}
	
	@Command
	@NotifyChange({"imgFoto", "empresa.fotoMostrarEdicion"})
	public void subir(@ContextParam(ContextType.BIND_CONTEXT) BindContext contexto) {
		Media media = null;
		UploadEvent eventoCarga = (UploadEvent) contexto.getTriggerEvent();
		media = eventoCarga.getMedia();
		if(media.getFormat().equals("bmp") || media.getFormat().equals("jpeg") ||
			media.getFormat().equals("jpg") || media.getFormat().equals("gif")){
			empresa.setPathFoto(media.getFormat());
			empresa.setRutaImagen(media.getFormat());
			empresa.setFoto(media.getByteData());
			imgFoto.setContent(empresa.getFotoMostrarEdicion());
		}else{
			Clients.showNotification("Error en el formato del archivo", "error", btnUpload, "end_center",2000,true);
			return;
		}
	}
	
	
	@Command
	@NotifyChange({"*"})
	public void cargarPaises(){
		paises = paiDao.mostrarPaises();
		cmbProvincias.setText("");
		cmbCantones.setText("");
		cmbParroquias.setText("");
		if (provincias != null) {
			provincias = null; 
			
		}
		if (cantones != null) {
			cantones = null; 
			
		}
		if (parroquias != null) {
			parroquias = null; 
			
		}
		//paisSeleccionado = null;
		provinciaSeleccionado = null; 
		cantonSeleccionado = null;
		parroquiaSeleccionado = null;
	}
	
	
	@Command
	@NotifyChange({"*"})
	public void cargarProvincias(){
		cmbCantones.setText("");
		cmbParroquias.setText("");
		//System.out.println("Cargar el combo"+paisSeleccionado.getNombrePais());
		provincias = provinciaDao.mostrarProvinciasxPais(paisSeleccionado.getNombrePais());
		
		if (cantones != null) {
			cantones = null; 
			//cantonSeleccionado = null;
		}
		if (parroquias != null) {
			parroquias = null; 
			//parroquiaSeleccionado = null;
		}
		//provinciaSeleccionado = null; 
		cantonSeleccionado = null;
		parroquiaSeleccionado = null;
	}
	
	@Command
	@NotifyChange({"*"})
	public void cargarCantones(){
		cmbParroquias.setText("");
		cantones = cantonDao.mostrarCantonxProvincias(provinciaSeleccionado.getNombreProvincia());
		if (parroquias != null) {
			parroquias = null; 
			//parroquiaSeleccionado = null;
		}
		//cantonSeleccionado = null;
		parroquiaSeleccionado = null;
	}
	
	@Command
	@NotifyChange({"*"})
	public void cargarParroquias(){
		parroquias = parroquiaDao.mostrarParroquiaxCantones(cantonSeleccionado.getNombreCanton());
		//parroquiaSeleccionado = null;
		
	}
	
	
	
	

}
