package ar.com.instMessenger.bean.agenda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import ar.com.instMessenger.bean.Bean;
import ar.com.instMessenger.bean.persona.PersonaBean;
import ar.com.instMessenger.entity.Agenda;
import ar.com.instMessenger.entity.Persona;
import ar.com.instMessenger.servicios.dao.agenda.IAgendaDao;

@Named
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class AgendaBean extends Bean {
	private static final long serialVersionUID = -8725702349262618823L;

	@Autowired
	IAgendaDao agendaDAO;

	@Autowired
	PersonaBean personaBean;
	
	private List<Agenda> agendas = new ArrayList<Agenda>();

	private Agenda[] selectedAgendas;

	private Agenda selectedAgenda;

	private Agenda nuevaAgenda = new Agenda();

	private boolean skip;

	@PostConstruct
	public void postConstructor() {
		agendas = agendaDAO.getAgendas();
		nuevaAgenda.setPersonas(new ArrayList<Persona>());
	}

	public void agregarAgenda(ActionEvent event) {
		FacesMessage msg;
		if (agendas.contains(nuevaAgenda)) {
			msg = new FacesMessage("Nombre ya existente: ",nuevaAgenda.getNombre());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		agendaDAO.add(nuevaAgenda);
		agendas = agendaDAO.getAgendas();
		nuevaAgenda = new Agenda();
		nuevaAgenda.setPersonas(new ArrayList<Persona>());
		selectedAgenda = null;
	}
	
	public void agregarSeleccionadosEnAgenda(ActionEvent event) {
		FacesMessage msg;
		if (agendas.contains(nuevaAgenda)) {
			msg = new FacesMessage("Nombre ya existente: ",nuevaAgenda.getNombre());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		nuevaAgenda.setPersonas(personaBean.getPersonas());
		agendaDAO.add(nuevaAgenda);
		agendas = agendaDAO.getAgendas();
		nuevaAgenda = new Agenda();
		nuevaAgenda.setPersonas(new ArrayList<Persona>());
		selectedAgenda = null;
	}

	public void updateAgenda(ActionEvent event) {
		agendaDAO.update(getSelectedAgenda());
		agendas = agendaDAO.getAgendas();
	}

	public void agregarPersona(ActionEvent event) {
		Persona persona = new Persona();
		persona.setEstado("ACTIVO");
		if (selectedAgenda != null)// Fue por el detalle
			selectedAgenda.getPersonas().add(persona);
		else
			// Fue a crear una nueva agenda
			nuevaAgenda.getPersonas().add(persona);
	}

	public void eliminarAgendas() {
		List<Agenda> agendasAeliminar = new ArrayList<Agenda>(
				Arrays.asList(getSelectedAgendas()));
		agendaDAO.removeAll(agendasAeliminar);
		this.agendas.removeAll(agendasAeliminar);

		FacesMessage msg;
		if (agendasAeliminar.size() == 0) {
			msg = new FacesMessage("Debe seleccionar al menos una Agenda","");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		if (agendasAeliminar.size() > 1) {
			msg = new FacesMessage("Agendas eliminadas","");
		} else {
			msg = new FacesMessage("Agenda eliminada", agendasAeliminar.get(0)
					.getNombre());
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void eliminarPersona(Persona persona) {
		if (selectedAgenda != null)// Fue por el detalle
			selectedAgenda.getPersonas().remove(persona);
		else
			// Fue a crear una nueva agenda
			nuevaAgenda.getPersonas().remove(persona);
	}

	public void onEdit(RowEditEvent event) {

		Agenda agenda = (Agenda) event.getObject();
		agendaDAO.update(agenda);

		FacesMessage msg = new FacesMessage("Parametro Editado",
				agenda.getNombre());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(CloseEvent event) {
	
		nuevaAgenda = new Agenda();
		nuevaAgenda.setPersonas(new ArrayList<Persona>());
		selectedAgenda = null;

	}

	public void onEditPersona(RowEditEvent event) {

		Persona persona = (Persona) event.getObject();

		FacesMessage msg = new FacesMessage("Parametro Editado",
				persona.getApellido() + ", " + persona.getNombre());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancelPersona(RowEditEvent event) {
		Persona persona = (Persona) event.getObject();
		FacesMessage msg = new FacesMessage("Parametro Cancelado",
				persona.getApellido() + ", " + persona.getNombre());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onFlowProcess(FlowEvent event) {
		// logger.info("Current wizard step:" + event.getOldStep());
		// logger.info("Next step:" + event.getNewStep());

		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public IAgendaDao getAgendaDAO() {
		return agendaDAO;
	}

	public void setAgendaDAO(IAgendaDao agendaDAO) {
		this.agendaDAO = agendaDAO;
	}

	public List<Agenda> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}

	public Agenda[] getSelectedAgendas() {
		return selectedAgendas;
	}

	public void setSelectedAgendas(Agenda[] selectedAgendas) {
		this.selectedAgendas = selectedAgendas;
	}

	public Agenda getNuevaAgenda() {
		return nuevaAgenda;
	}

	public void setNuevaAgenda(Agenda nuevaAgenda) {
		this.nuevaAgenda = nuevaAgenda;
	}

	public Agenda getSelectedAgenda() {
		return selectedAgenda;
	}

	public void setSelectedAgenda(Agenda selectedAgenda) {
		this.selectedAgenda = selectedAgenda;
	}

}
