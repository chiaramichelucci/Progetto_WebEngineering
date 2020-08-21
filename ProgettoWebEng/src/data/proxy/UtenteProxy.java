package data.proxy;

import data.DataItemProxy;
import data.DataLayer;
import data.impl.UtenteImpl;

public class UtenteProxy extends UtenteImpl implements DataItemProxy {

	public UtenteProxy(int id, String nome, String cognome, String email, String password, String tipo) {
		super(id, nome, cognome, email, password, tipo);
		// TODO Auto-generated constructor stub
	}

	
	protected DataLayer dataLayer;
	
	public UtenteProxy(DataLayer d) {
		super(id, nome, cognome, email, password, tipo);
		this.dataLayer = d;
	}
	
	@Override
	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setModified(boolean dirty) {
		// TODO Auto-generated method stub

	}

	public void setKey(int int1) {
		// TODO Auto-generated method stub
		
	}
	
	public void getUtente (int id_utente) {
		
	}

}
