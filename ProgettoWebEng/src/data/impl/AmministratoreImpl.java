package data.impl;

import data.DataItemImpl;
import data.model.Amministratore;

public class AmministratoreImpl extends DataItemImpl<Integer> implements Amministratore{
	 
	private int id;
	private String email;
	private String password;
	
	public AmministratoreImpl() {
		this.email = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Integer getKey() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}