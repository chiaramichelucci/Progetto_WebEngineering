package data.proxy;

import data.DataItemProxy;
import data.impl.AmministratoreImpl;

public class AmministratoreProxy extends AmministratoreImpl implements DataItemProxy {

	public AmministratoreProxy(int id, String email, String password) {
		super(id, email, password);
		// TODO Auto-generated constructor stub
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

}
