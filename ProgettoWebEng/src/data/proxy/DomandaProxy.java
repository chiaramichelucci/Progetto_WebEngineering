package data.proxy;

import data.DataItemProxy;
import data.impl.DomandaImpl;

public class DomandaProxy extends DomandaImpl implements DataItemProxy {

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
