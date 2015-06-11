package ar.com.instMessenger.servicios.dao.persona;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import ar.com.instMessenger.entity.Persona;


@Service
@PersistenceContext(unitName="dataBasePpal")
public class PersonaDao implements IPersonaDao{
	
	@PersistenceContext
	EntityManager em;


	@Bean
	@Scope(value=WebApplicationContext.SCOPE_SESSION, proxyMode=ScopedProxyMode.INTERFACES)
	@SuppressWarnings("unchecked")
	public List<Persona> getPersonas() {
		Query newQuery =em.createQuery("from Persona as per where per.estado = ?");
		newQuery.setParameter(1, "ACTIVO");
		List<Persona> resultList = newQuery.getResultList();
		return resultList;
	}
	

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Persona personaActualizada) {
		Persona persona = em.find(Persona.class, personaActualizada.getId());
		persona.setApellido(personaActualizada.getApellido());
		persona.setNombre(personaActualizada.getNombre());
		persona.setMail(personaActualizada.getMail());
		persona.setCelular(personaActualizada.getCelular());
		em.merge(persona);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(Persona persona) {
		em.merge(persona);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(Persona persona) {
		em.remove(em.contains(persona) ? persona : em.merge(persona));
	}
	

	@Bean
	@Scope(value=WebApplicationContext.SCOPE_REQUEST, proxyMode=ScopedProxyMode.INTERFACES)
	public Persona obtenerUnaPersona(int idPersona) {
		return em.find(Persona.class, idPersona);
	}
}
