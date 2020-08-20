package data.proxy;

import java.util.logging.Level;
import java.util.logging.Logger;

import data.DataItemProxy;
import data.impl.OpzioneImpl;
import data.model.Domanda;
import data.DataLayer;

public class OpzioneProxy extends OpzioneImpl implements DataItemProxy {

	protected boolean modified;
    protected String codiceDomanda = "";
    
    protected DataLayer dataLayer;
    
    public OpzioneProxy(DataLayer d) {
        super();
        //dependency injection
        this.dataLayer = d;
        this.modified = false;
        this.codiceDomanda = "";
    }
    
    //funzione mancante
    
    public void setDomanda(String string) {
        super.setDomanda(string);
        this.modified = true;
    }
    
    public void setOpzione(String testo) {
    	super.setOpzione(testo);
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
