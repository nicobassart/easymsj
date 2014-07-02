package ar.com.easyMsj.servicios.dao.persona;

import java.sql.Blob;
import java.util.List;

import ar.com.easyMsj.entity.PersonaEnt;

public interface IPersonaDao {
	public PersonaEnt consultarPersona(int id);
	public List<PersonaEnt> obtenerPersonas();
	public Blob crearQre(PersonaEnt persona);
	public List<PersonaEnt>  obtenerParsonasDelClub(int id);
}
