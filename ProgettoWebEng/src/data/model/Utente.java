package data.model;

import data.DataItem;

public interface Utente extends DataItem<Integer>{
	
	void setID(int id);
	int getID();
	Integer getKey();
	void setNome(String nome);
	void setCognome(String cognome);
	void setEmail(String email);
	void setPassword(String password);
	void setTipo(String tipo);
	String getNome();
	String getCognome();
	String getEmail();
	String getPassword();
	String getTipo();

}
