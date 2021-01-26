package com.jsfhibernate.pojo;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jsfhibernate.dao.DatabaseOperations;

@ManagedBean
@SessionScoped
public class Usuario implements java.io.Serializable {

	@Id
	@Column(name="id_fornecedor")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="email")
	private String email;
	
	@Column(name="senha")
	private String senha;
	private List<Usuario> usuarioList;	
	public static DatabaseOperations dbObj;
	private static final long serialVersionUID = 1L;

	public Usuario() { }

	public Usuario(Long id) {
		this.id = id;
	}

	public  Usuario(Long id, String email, String senha) {
		this.id = id;
		this.email = email;
		this.senha = senha;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}

	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}

	public void saveUsuarioRecord() {		
		dbObj = new DatabaseOperations();
		dbObj.addUsuarioInDb(this);
	}

	public void deleteUsuarioRecord() {
		dbObj = new DatabaseOperations();
		dbObj.deleteUsuarioInDb(id);
	}

	public List<Usuario> getUsuarioDetailsById() {
		dbObj = new DatabaseOperations();		
		usuarioList = dbObj.getUsuarioById(id);
		for(Usuario selectedUsu : usuarioList) {
			email = selectedUsu.getEmail();
			senha = selectedUsu.getSenha();
		}
		return usuarioList;
	}
	
	public List<Usuario> validateCredentials() {
		dbObj = new DatabaseOperations();		
		usuarioList = dbObj.getUsuarioByCredentials(email, senha);
		
		return usuarioList;
	}

	public void updateUsuarioDetails() {
		dbObj = new DatabaseOperations();		
		dbObj.updateUsuarioRecord(this);
	}

	public List<Usuario> getAllUsuarioRecords() {
		dbObj = new DatabaseOperations();		
		List<Usuario> usuariosList = dbObj.retrieveUsuario();
		for(Usuario stud : usuariosList) {
			if(stud.getId() == id) {
				email = stud.getEmail();
				senha = stud.getSenha();
			}
		}
		return usuariosList;
	}
}