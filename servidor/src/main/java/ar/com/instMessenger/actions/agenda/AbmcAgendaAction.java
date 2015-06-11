package ar.com.instMessenger.actions.agenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import ar.com.instMessenger.actions.Action;
import ar.com.instMessenger.bean.persona.PersonaBean;
import ar.com.instMessenger.entity.Agenda;

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class AbmcAgendaAction extends Action implements IAbmcAgendaAction {

	private static final long serialVersionUID = -2261476298684088030L;

	@Autowired
	private PersonaBean personaBean;

	@Autowired
	private ApplicationContext appContext;

	public String agregarAgenda(Agenda agenda) {

		return "nuevaAgendaOK";
	}

}
