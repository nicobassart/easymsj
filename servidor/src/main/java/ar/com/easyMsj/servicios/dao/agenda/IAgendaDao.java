package ar.com.easyMsj.servicios.dao.agenda;

import java.util.List;

import ar.com.easyMsj.entity.Agenda;

public interface IAgendaDao {
	public List<Agenda> getAgendas();
	public void update(Agenda agenda);
	public void add(Agenda agenda);
	public void remove(Agenda agenda);
	public void removeAll(List<Agenda> agendas);
	public Agenda find(String value);
}
