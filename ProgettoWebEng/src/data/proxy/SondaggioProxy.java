package data.proxy;

import data.DataItemProxy;
import data.DataLayer;
import data.impl.DomandaImpl;
import data.impl.SondaggioImpl;

public class SondaggioProxy extends SondaggioImpl implements DataItemProxy {

	public SondaggioProxy(int id, String titolo, boolean disponibile, String modalita, int n_domande, DomandaImpl[] d) {
		super(id, titolo, disponibile, modalita, n_domande, d);
		// TODO Auto-generated constructor stub
	}

	protected DataLayer dataLayer;
	
	public SondaggioProxy(DataLayer a) {
		super(id, titolo, disponibile, modalita, n_domande, domande);
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

	public void setNDomande(int int1) {
		// TODO Auto-generated method stub
		
	}

	public void setURL(String string) {
		// TODO Auto-generated method stub
		
	}
	
	public void getSondaggio (int id_sondaggio) {
		
	}

}
