package data.impl;

import data.model.Amministratore;

public class AmministratoreImpl implements Amministratore{
	 
	public static int id;
	protected static String email;
	protected static String password;
	
	public AmministratoreImpl(int id, String email, String password) {
		this.setId(id);
		this.setEmail(email);
		this.setPassword(password);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public static String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int getKey() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}