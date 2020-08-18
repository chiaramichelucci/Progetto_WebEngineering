package data.proxy;

import data.DataItemProxy;
import data.impl.RispostaImpl;

public class RispostaProxy extends RispostaImpl implements DataItemProxy {

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
