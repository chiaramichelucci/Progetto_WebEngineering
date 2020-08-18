package data.proxy;

import data.DataItemProxy;
import data.impl.SondaggioImpl;

public class SondaggioProxy extends SondaggioImpl implements DataItemProxy {

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
