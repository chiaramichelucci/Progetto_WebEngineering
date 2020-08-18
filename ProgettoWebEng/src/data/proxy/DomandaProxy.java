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
	protected String domanda_codice;
	protected int sondaggio_id;
	
	protected DataLayer dataLayer;
	
	public DomandaProxy(DataLayer d) {
		super();
		this.dataLayer = d;
		this.modified = false;
		this.domanda_codice = "";
		this.sondaggio_id = 0;
	}
	
	@Override
    public void setCodice(String codice) {
        super.setCodice(codice);
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
	public void setOpzioni(List<Opzione> opzioni) {
		 super.setOpzioni(opzioni);
		 this.modified = true;
	 } 
	 
	 @Override
	public void addOpzione(Opzione opzione) {
		 super.addOpzione(opzione);
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
