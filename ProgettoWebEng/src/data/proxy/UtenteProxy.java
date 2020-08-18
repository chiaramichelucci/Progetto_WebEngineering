package data.proxy;

import data.DataItemProxy;
import data.impl.UtenteImpl;

public class UtenteProxy extends UtenteImpl implements DataItemProxy {

	@Override
	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setModified(boolean dirty) {
		// TODO Auto-generated method stub

	}

}
