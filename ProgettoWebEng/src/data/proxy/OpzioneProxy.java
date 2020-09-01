package data.proxy;

import java.util.logging.Level;
import java.util.logging.Logger;

import data.DataItemProxy;
import data.impl.OpzioneImpl;
import data.model.Domanda;
import data.DataLayer;

public class OpzioneProxy extends OpzioneImpl implements DataItemProxy {

	protected boolean modified;
    protected int id_domanda = 0;
    
    protected DataLayer dataLayer;
    
    public OpzioneProxy(DataLayer d) {
        super();
        //dependency injection
        this.dataLayer = d;
        this.modified = false;
        this.id_domanda = 0;
    }
    
    //funzione mancante
    
    public void setID(int id) {
    	super.setID(id);
        this.modified = true;
    }
    
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }
    
    public void setDomanda(Domanda domanda) {
        super.setDomanda(domanda);
        this.id_domanda = domanda.getID();
        this.modified = true;
    }
    
    @Override
    public void setIDomanda(int id) {
    	super.setIDomanda(id);
    	this.modified = true;
    }
    
    public int getIDomanda() {
    	return this.id_domanda;
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
