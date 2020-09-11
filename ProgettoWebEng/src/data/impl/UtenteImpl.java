package data.impl;

import data.DataItemImpl;
import data.model.Utente;

public class UtenteImpl extends DataItemImpl<Integer> implements Utente {
	
	private int id;
	private String nome;
	private String cognome;
	private String email;
	private String password;
	private String tipo;
	
	public UtenteImpl() {;
		this.cognome= "";
		this.nome= "";
		this.email= "";
		this.password= "";
	}
	
	public int getID() {
	return id;
	}
	
	public void setID(int id) {
		this.id=id;
	}
	
	public String getNome() {
		return nome;
	}
		
	public void setNome(String nome) {
		this.cognome=nome;
	}
	
	public String getCognome() {
		return cognome;
	}
		
	public void setCognome(String cognome) {
		this.cognome=cognome;
	}
	
	public String getEmail() {
		return email;
	}
		
	public void setEmail(String email) {
		this.email=email;
	}	
	
	public String getPassword() {
		return password;
	}
		
	public void setPassword(String password) {
		this.password=password;
	}	
	
	public String getTipo() {
		return tipo;
	}
		
	public void setTipo(String tipo) {
		this.tipo=tipo;
	}

	@Override
	public Integer getKey() {
		// TODO Auto-generated method stub
		return 0;
	}	
}
