package data.impl;

import data.DataItemImpl;
import data.model.Utente;

public class UtenteImpl extends DataItemImpl<Integer> implements Utente {
	protected static int id;
	protected static String nome;
	protected static String cognome;
	protected static String email;
	protected static String password;
	protected static String tipo;
	public UtenteImpl(int id, String nome, String cognome, String email, String password, String tipo) {;
	setId(id);
	setNome(nome);
	setCognome(cognome);
	setEmail(email);
	setPassword(password);	
	setTipo(tipo);	
	}
	
	public int getId() {
	return id;
	}
	
	public void setId(int id) {
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
