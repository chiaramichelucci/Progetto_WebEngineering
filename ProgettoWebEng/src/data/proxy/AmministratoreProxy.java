package data.proxy;

import data.DataItemProxy;
import data.DataLayer;
import data.impl.AmministratoreImpl;

public class AmministratoreProxy extends AmministratoreImpl implements DataItemProxy {
	
	protected DataLayer dataLayer;
	
	public AmministratoreProxy(DataLayer a) {
		super();
		this.dataLayer = a;
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
	
	public void setAmministratore(int id_amministratore) {
		
	}

}
