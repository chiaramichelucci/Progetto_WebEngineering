package data.proxy;

import data.DataItemProxy;
import data.DataLayer;
import data.impl.UtenteImpl;

public class UtenteProxy extends UtenteImpl implements DataItemProxy {
	
	protected DataLayer dataLayer;
	
	public UtenteProxy(DataLayer d) {
		super();
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
