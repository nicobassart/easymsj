package ar.com.easyMsj.servicios.dao.agenda;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.easyMsj.entity.Agenda;




@Service
@PersistenceContext(unitName="dataBasePpal")
public class AgendaDao implements IAgendaDao,Serializable{
	
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	EntityManager em;


	public List<Agenda> getAgendas() {
		Query newQuery =em.createQuery("from Agenda as agenda");
		//newQuery.setParameter(1, "ACTIVO");
		List<Agenda> resultList = newQuery.getResultList();
		return resultList;
	}
	
	public Agenda find(String value) {
		Query newQuery =em.createQuery("from Agenda as agenda where nombre = ?");
		newQuery.setParameter(1, value);
		List<Agenda> resultList = newQuery.getResultList();
		return resultList.get(0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Agenda agendaActualizada) {
	    
		em.merge(agendaActualizada);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(Agenda agenda) {
		em.merge(agenda);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(Agenda agenda) {
		em.remove(em.contains(agenda) ? agenda : em.merge(agenda));
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeAll(List<Agenda> agendas) {
		for (Agenda agenda : agendas) {
			em.remove(em.contains(agenda) ? agenda : em.merge(agenda));	
		}
	}
	
}
