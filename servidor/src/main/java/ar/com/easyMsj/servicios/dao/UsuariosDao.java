package ar.com.easyMsj.servicios.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import ar.com.easyMsj.entity.PersonaEnt;
import ar.com.easyMsj.entity.RolEnt;
import ar.com.easyMsj.entity.UsuarioEnt;
import ar.com.easyMsj.exceptions.UsuarioExistenteException;
import ar.com.easyMsj.servicios.dao.persona.IPersonaDao;

@Service
public class UsuariosDao implements IUsuariosDao{
	
	@PersistenceContext
	EntityManager em;

    @Autowired
    IPersonaDao personaDao;
	
	@SuppressWarnings("unchecked")
	@Bean
	@Scope(value=WebApplicationContext.SCOPE_SESSION,
		   proxyMode=ScopedProxyMode.INTERFACES)
	public List<UsuarioEnt> getUsuarios() {
		List<UsuarioEnt> resultList = em.createQuery("from UsuarioEnt").getResultList();
		return resultList;
	}
	
	@Bean
	@Scope(value=WebApplicationContext.SCOPE_SESSION,
		   proxyMode=ScopedProxyMode.INTERFACES)
	public UsuarioEnt getUsuario(String userName) {
		UsuarioEnt usuario = null;
		Query query = em.createQuery("from UsuarioEnt as u where u.userName = ?1");
		query.setParameter(1, userName);
		try {
			usuario = (UsuarioEnt) query.getSingleResult();
		}
		catch (NoResultException e) {
			System.out.println("No hay resultados para " + userName);
		}
		
		return usuario;
	}
	
	@Transactional
	public UsuarioEnt crearUsuario(String tipoDoc, String numeroDocumento, String mail, String pclave, String apellido, String nombre) throws UsuarioExistenteException,  NoResultException{
//		ClaveEnt clave = null;
		UsuarioEnt usuario = null;
//		Query query = em.createQuery("from ClaveEnt as c where c.clave = ?1 and c.estado = ?2");
//		query.setParameter(1, pclave);
//		query.setParameter(2, "LIBRE");//Estados existentes: LIBRE, ASIGNADA, VENCIDA
//		try {
//			clave = (ClaveEnt) query.getSingleResult();
//			System.out.println("clave libre existente");
//			
//			if(getUsuario(numeroDocumento) != null){
//				throw new UsuarioExistenteException();
//			}
//			
//			PersonaEnt persona = new PersonaEnt();
//			persona.setTipoDoc(tipoDoc);
//			persona.setNumeroDoc(Integer.valueOf(numeroDocumento));
//			persona.setApellido(apellido);
//			persona.setNombre(nombre);
//			persona.setEmail(mail);
//			
//			em.persist(persona);//porque necesito que genere el ID, que será usado en crearQre
//			
//			persona.setQre(personaDao.crearQre(persona));
//			
//			usuario = new UsuarioEnt();
//			usuario.setUserName(numeroDocumento);
//			usuario.setMail(mail);
//			usuario.setPassword(pclave);
//			
//			List<RolEnt> roles = new ArrayList<RolEnt>();
//			roles.add( new RolEnt("PACIENTE", "Paciente"));//TODO: depende el tipo de clave
//			usuario.setRoles(roles);
//			usuario.setTipoUsuario("PACIENTE");
//			usuario.setPersona(persona);
//			
//			em.persist(usuario);			
//		}
//		catch (NoResultException e) {
//			System.out.println("CLAVE INEXISTENTE O YA UTILIZADA");
//			throw e;
//		}
		
		return usuario;
	}
	
	
}
