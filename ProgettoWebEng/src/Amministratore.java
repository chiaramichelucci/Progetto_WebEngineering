

public class Amministratore {
	 
	private int id;
	private String email;
	private String password;
	
	public Amministratore(int id, String email, String password) {
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
	

}