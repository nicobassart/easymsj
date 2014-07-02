package ar.com.easyMsj.servicios.dao.persona;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.easyMsj.entity.PersonaEnt;
import ar.com.easyMsj.servicios.dao.IUsuariosDao;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class PersonaDao implements IPersonaDao,Serializable{
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	IUsuariosDao usuariosDAO;
	
	@Override
	public PersonaEnt consultarPersona(int idPersona) {
		PersonaEnt persona = null;
		Query query = em.createQuery("from PersonaEnt as u where u.id = ?1");
		query.setParameter(1, idPersona);
		try {
			persona = (PersonaEnt) query.getSingleResult();
		}
		catch (NoResultException e) {
			System.out.println("No hay resultados para " + idPersona);
		}
		
		return persona;
	}

	@Override
	public List<PersonaEnt> obtenerPersonas() {
		List<PersonaEnt> resultList = em.createQuery("from PersonaEnt").getResultList();
		return resultList;
	}
	
	@Override
	public List<PersonaEnt> obtenerParsonasDelClub(int id) {
//		List<PersonaEnt> resultList = em.createQuery("from PersonaEnt").getResultList();
//		
//		return resultList;
		
		List persona = null;
		Query query = em.createQuery("from PersonaEnt as u right join u.ficha_medica as d where d.paso2_15_presentarEn= ?1");
		query.setParameter(1, id);
		try {
			persona = (List) query.getResultList();
		}
		catch (NoResultException e) {
			System.out.println("No hay resultados para ");
		}
		
		return persona;

		
	}
	

	/**
	 * TODO: esto se modificara cuando la QRE ya se cargue automaticamente con el alta del usuario
	 * @return
	 */
	public Blob crearQre(PersonaEnt persona) {
	
	    Blob blob = null;
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    try {
		  if(persona.getQre()==null){
		      	    //TODO: ByteArrayOutputStream TO Array Byte
			  
			  		URL reconstructedURL = new URL(FacesContext.getCurrentInstance().getExternalContext().getRequestScheme(),
					  FacesContext.getCurrentInstance().getExternalContext().getRequestServerName(),
					  FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort(),
                      "");
			  
	        	    BitMatrix bitMatrix = new QRCodeWriter().encode(reconstructedURL + "/qre.do?id=" + persona.getId(), BarcodeFormat.QR_CODE, 400, 400);
	        	    BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
	        	    ImageIO.write(bufferedImage, "png", baos);
	        	    try {
	        	    	blob = new SerialBlob(baos.toByteArray());
	        	    } catch (SerialException e) {
	        	    	System.out.println(e);
	        	    } catch (SQLException e) {
	        	    	System.out.println(e);
	        	    }
		    }
		   
	    } catch (Exception e) {
	    	System.out.println(e);
	    }

	    return blob;
	}

}
