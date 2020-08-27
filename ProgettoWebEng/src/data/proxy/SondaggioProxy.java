package data.proxy;

import data.DataItemProxy;
import data.DataLayer;
import data.impl.DomandaImpl;
import data.impl.SondaggioImpl;

public class SondaggioProxy extends SondaggioImpl implements DataItemProxy {
	
	protected boolean modified;

	protected DataLayer dataLayer;
	
	public SondaggioProxy(DataLayer a) {
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

	public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

	public void setNDomande(int int1) {
		// TODO Auto-generated method stub
		
	}

	public void setURL(String string) {
		// TODO Auto-generated method stub
		
	}
	
	public void getSondaggio (int id_sondaggio) {
		
	}

}
