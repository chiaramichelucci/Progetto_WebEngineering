package data.proxy;

import data.DataItemProxy;
import data.impl.AmministratoreImpl;

public class AmministratoreProxy extends AmministratoreImpl implements DataItemProxy {

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
