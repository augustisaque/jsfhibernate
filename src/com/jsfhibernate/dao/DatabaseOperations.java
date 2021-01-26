package com.jsfhibernate.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jsfhibernate.pojo.Usuario;
import com.jsfhibernate.util.HibernateUtil;

public class DatabaseOperations {

	private static Transaction transObj;
	private static Session sessionObj = HibernateUtil.getSessionFactory().openSession();


	public void addUsuarioInDb(Usuario usuarioObj) {		
		try {
			transObj = sessionObj.beginTransaction();
			sessionObj.merge(usuarioObj);
		
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("createdUsuarioId",  usuarioObj.getId());						
		} catch (Exception exceptionObj) {
			exceptionObj.printStackTrace();
		} finally {
			transObj.commit();
		}
	}

	public void deleteUsuarioInDb(Long delUsuarioId) {
		try {
			transObj = sessionObj.beginTransaction();
			Usuario usuId = (Usuario)sessionObj.load(Usuario.class, new Long(delUsuarioId));
			sessionObj.delete(usuId);

			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deletedUsuarioId",  delUsuarioId);	
		} catch (Exception exceptionObj) {
			exceptionObj.printStackTrace();
		} finally {
			transObj.commit();
		}
	}
	
	public List<Usuario> getUsuarioById(Long usuarioId) {	
		Usuario particularUsuDObj = new Usuario();
		List<Usuario> particularUsuarioList = new ArrayList<Usuario>();            
		try {
			transObj = sessionObj.beginTransaction();
			Query queryObj = sessionObj.createQuery("from Usuario where id= :usuario_id").setLong("usuario_id", usuarioId);			
			particularUsuDObj = (Usuario)queryObj.uniqueResult();
			particularUsuarioList = queryObj.list();			
		
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("findUsuarioById",  usuarioId);
		} catch(Exception exceptionObj) {
			exceptionObj.printStackTrace();
		} finally {
			transObj.commit();
		}
		return particularUsuarioList;
	}
	
	
	public List<Usuario> getUsuarioByCredentials(String email, String senha) {	
		Usuario particularUsuDObj = new Usuario();
		List<Usuario> particularUsuarioList = new ArrayList<Usuario>();            
		try {
			transObj = sessionObj.beginTransaction();
			Query queryObj = sessionObj.createQuery("from Usuario where email= :usuario_email and senha= :usuario_senha");
			queryObj.setParameter("usuario_email",email);
			queryObj.setParameter("usuario_senha", senha );			
			particularUsuDObj = (Usuario)queryObj.uniqueResult();
			particularUsuarioList = queryObj.list();
			
			if (particularUsuarioList.size() > 0 ) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("Logado.html");
			}
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("findUsuarioById",  email);
		} catch(Exception exceptionObj) {
			exceptionObj.printStackTrace();
		} finally {
			transObj.commit();
		}
		return particularUsuarioList;
	}

	public void updateUsuarioRecord(Usuario updateUsuarioObj) {
		try {
			transObj = sessionObj.beginTransaction();
			sessionObj.merge(updateUsuarioObj);		

			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("updatedUsuarioRecord",  "Success");
		} catch(Exception exceptionObj){
			exceptionObj.printStackTrace();
		} finally {
			transObj.commit();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> retrieveUsuario() {		
		Usuario usuariosObj;
		List<Usuario>allUsuarios = new ArrayList<Usuario>();
		try {
			transObj = sessionObj.beginTransaction();
			Query queryObj = sessionObj.createQuery("from Usuario");
			allUsuarios = queryObj.list();
			for(Usuario usu : allUsuarios) {
				usuariosObj = new Usuario(); 								
				usuariosObj.setEmail(usu.getEmail());
				usuariosObj.setSenha(usu.getSenha());								
				allUsuarios.add(usuariosObj);  
			}			

			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("findUsuarioById", "true");
		} catch(Exception exceptionObj) {
			exceptionObj.printStackTrace();
		} finally {
			transObj.commit();
		}
		return allUsuarios;
	}
}