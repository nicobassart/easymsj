package ar.com.instMessenger.servicios.dao.persona;

import java.util.List;

import ar.com.instMessenger.entity.Persona;

public interface IPersonaDao {
	public List<Persona> getPersonas();
	public void update(Persona persona);
	public void add(Persona persona);
	public void remove(Persona persona);
	public Persona obtenerUnaPersona(int id);
}
