package ar.com.easyMsj.actions.agenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import ar.com.easyMsj.actions.Action;
import ar.com.easyMsj.bean.persona.PersonaBean;
import ar.com.easyMsj.entity.Agenda;


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
