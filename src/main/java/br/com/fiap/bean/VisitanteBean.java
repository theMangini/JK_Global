package br.com.fiap.bean;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.model.file.UploadedFile;

import br.com.fiap.dao.VisitanteDao;
import br.com.fiap.model.Visitante;

@Named
@RequestScoped
public class VisitanteBean {

	private Visitante visitante = new Visitante();
	private List<Visitante> list;
	private UploadedFile image;
	
	public VisitanteBean() {
		list = this.list();
	}
	
	public String save() throws IOException{		
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		
		String path = servletContext.getRealPath("/"); 
		
		FileOutputStream out = new FileOutputStream(path + "\\images\\" + image.getFileName());
		out.write(image.getContent());
		out.close();
		
		visitante.setImagePath("\\images\\" + image.getFileName());
		
		new VisitanteDao().create(visitante);
		
		mostrarMensagem();

		
		return "home?faces-redirect=true";
	}

	private void mostrarMensagem() {
		FacesContext
			.getCurrentInstance()
			.getExternalContext()
			.getFlash()
			.setKeepMessages(true);
		
		FacesContext
			.getCurrentInstance()
			.addMessage(null, new FacesMessage("Visitante cadastrado com sucesso"));
	}
	
	public List<Visitante> list(){
		return new VisitanteDao().listAll();
	}
	
	public String remove(Visitante Visitante) {
		new VisitanteDao().delete(Visitante);
		
		FacesContext
			.getCurrentInstance()
			.addMessage(null, new FacesMessage("Visitante apagado com sucesso"));
		
		return "home?faces-redirect=true";

	}
	
	public List<Visitante> getList() {
		return list;
	}

	public void setList(List<Visitante> list) {
		this.list = list;
	}

	public Visitante getVisitante() {
		return visitante;
	}

	public void setVisitante(Visitante Visitante) {
		this.visitante = Visitante;
	}

	public UploadedFile getImage() {
		return image;
	}

	public void setImage(UploadedFile image) {
		this.image = image;
	}

}
