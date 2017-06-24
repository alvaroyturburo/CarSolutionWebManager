package com.empresas.modelo;

import java.io.IOException;
import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;

import com.empresas.util.FileUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the empresa database table.
 * 
 */
@Entity
@Table(name="empresa")
@NamedQueries({
@NamedQuery(name="Empresa.findAll", query="SELECT e FROM Empresa e"),
@NamedQuery(name="Empresa.buscarPorNombre", query="SELECT e FROM Empresa e where e.nombre like :nombre")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_empresa")
	@Getter @Setter private int idEmpresa;

	@Column(name="descripcion_empresa")
	@Getter @Setter private String descripcionEmpresa;

	@Getter @Setter private String direccion;

	@Getter @Setter private String estado;

	@Getter @Setter private String latitud;

	@Getter @Setter private String longitud;

	@Getter @Setter private String nombre;

	@Column(name="ruta_imagen")
	@Getter @Setter private String rutaImagen;

	@Getter @Setter private String telefono;
	
	@Column(name="path_foto")
	@Getter @Setter private String pathFoto;
	
	@Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(name="foto", columnDefinition="BLOB NULL")
    @Getter @Setter byte[] foto;

	//bi-directional many-to-one association to Parroquia
	@ManyToOne
	@JoinColumn(name="id_parroquia")
	@Getter @Setter private Parroquia parroquia;

	//bi-directional many-to-one association to TipoEmpresa
	@ManyToOne
	@JoinColumn(name="idtipo_empresa")
	@Getter @Setter private TipoEmpresa tipoEmpresa;

	//bi-directional many-to-one association to EmpresaHorario
	@OneToMany(mappedBy="empresa", cascade=CascadeType.ALL)
	@Getter @Setter private List<EmpresaHorario> empresaHorarios;

	//bi-directional many-to-one association to EmpresaServicio
	@OneToMany(mappedBy="empresa", cascade=CascadeType.ALL)
	@Getter @Setter private List<EmpresaServicio> empresaServicios;

	public EmpresaServicio addEmpresaServicio(EmpresaServicio empresaServicio) {
		getEmpresaServicios().add(empresaServicio);
		empresaServicio.setEmpresa(this);

		return empresaServicio;
	}

	public EmpresaServicio removeEmpresaServicio(EmpresaServicio empresaServicio) {
		getEmpresaServicios().remove(empresaServicio);
		empresaServicio.setEmpresa(null);

		return empresaServicio;
	}
	
	
	public AImage getFotoMostrar() {
		AImage retorno = null;
		if (getPathFoto() != null) {
			try {
				retorno = FileUtil.getImagenTamanoFijoBlob(getFoto(), 30, 30, getPathFoto());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				String pathAbsoluto = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
				String imagen = pathAbsoluto + "/imagenes/Usuario.jpg";
				retorno = FileUtil.getImagenTamanoFijo(new AImage(imagen), 30, 30);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return retorno; 
	}
	
	public AImage getFotoMostrarEdicion() {
		AImage retorno = null;
		if (getPathFoto() != null) {
			try {
				retorno = FileUtil.getImagenTamanoFijoBlob(getFoto(), 285, 285, getPathFoto());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				String pathAbsoluto = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
				String imagen = pathAbsoluto + "/imagenes/Usuario.jpg";
				retorno = FileUtil.getImagenTamanoFijo(new AImage(imagen), 285, 285);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return retorno; 
	}
	

}