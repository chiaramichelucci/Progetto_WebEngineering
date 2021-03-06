package data.proxy;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import data.DataItemProxy;
import data.DataLayer;
import data.impl.DomandaImpl;
import data.model.Opzione;
import data.model.Sondaggio;
import data.dao.SondaggioDAO;
//import data.model.Sondaggio;
//import data.proxy.SondaggioProxy;
import data.DataException;

public class DomandaProxy extends DomandaImpl implements DataItemProxy {

	protected boolean modified;
	protected int sondaggio_id;
	
	protected DataLayer dataLayer;
	
	public DomandaProxy(DataLayer d) {
		super();
		this.dataLayer = d;
		this.modified = false;
		this.sondaggio_id = 0;
	}
	
	public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }
	
	@Override
    public void setID(int id) {
        super.setID(id);
        this.modified = true;
    }
	
	public Sondaggio getSondaggio() {
        if (super.getSondaggio() == null && sondaggio_id > 0) {
            try {
                super.setSondaggio(((SondaggioDAO) dataLayer.getDAO(Sondaggio.class)).getSondaggio(sondaggio_id));
            } catch (DataException ex) {
                Logger.getLogger(SondaggioProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getSondaggio();
	}
	
	public void setSondaggio(Sondaggio sondaggio) {
		super.setSondaggio(sondaggio);
        this.sondaggio_id = sondaggio.getID();
        this.modified = true;
	}
	
	 @Override
	 public void setTesto(String testo) {
	     super.setTesto(testo);
	     this.modified = true;
	 }
	 
	 @Override
	 public void setNota(String nota) {
	     super.setNota(nota);
	     this.modified = true;
	 }
	 
	 @Override
	public void setObbligatoria(boolean obbligatoria) {
		 super.setObbligatoria(obbligatoria);
		 this.modified = true;
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

}
