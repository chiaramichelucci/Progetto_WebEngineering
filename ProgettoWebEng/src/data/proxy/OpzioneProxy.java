package data.proxy;

import java.util.logging.Level;
import java.util.logging.Logger;

import data.DataItemProxy;
import data.impl.OpzioneImpl;
import data.model.Domanda;
import data.DataLayer;

public class OpzioneProxy extends OpzioneImpl implements DataItemProxy {

	protected boolean modified;
    protected int codiceDomanda = 0;
    
    protected DataLayer dataLayer;
    
    public OpzioneProxy(DataLayer d) {
        super();
        //dependency injection
        this.dataLayer = d;
        this.modified = false;
        this.codiceDomanda = 0;
    }
    
    //funzione mancante
    
    public void setID(int id) {
        super.setID(id);
        this.modified = true;
    }
    
    public void setTesto(String testo) {
    	super.setTesto(testo);
    	this.modified = true;
    }
    
	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean dirty) {
		this.modified = dirty;
	}

}
