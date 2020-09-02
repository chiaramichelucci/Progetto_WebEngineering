package data.proxy;

import java.util.logging.Level;
import java.util.logging.Logger;

import data.DataItemProxy;
import data.impl.RispostaImpl;
import data.model.Domanda;
import data.model.Utente;
import data.DataException;
import data.DataLayer;
import data.dao.DomandaDAO;

public class RispostaProxy extends RispostaImpl implements DataItemProxy {

	protected boolean modified;
    protected int codiceDomanda = 0;
    protected int idUtente = 0;

    protected DataLayer dataLayer;
	
	public RispostaProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
        this.codiceDomanda = 0;
        this.idUtente = 0;
    }
	
	@Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }
	
	@Override
    public Domanda getDomanda() {
        if (super.getDomanda() == null && codiceDomanda == 0) {
            try {
                super.setDomanda(((DomandaDAO) dataLayer.getDAO(Domanda.class)).getDomanda(codiceDomanda));
            } catch (DataException ex) {
                Logger.getLogger(RispostaProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getDomanda();
    }
	
	public void setDomanda(Domanda codice) {
		super.setDomanda(codice);
    	this.modified = true;
	}
	
	public void setDomandaId(int codice) {
		this.codiceDomanda = codice;
    	this.modified = true;
	}
	
	public void setUtente(Utente id) {
		super.setUtente(id);
    	this.modified = true;
	}
	
	public void setUtenteKey(int id) {
		this.idUtente = id;
    	this.modified = true;
	}


	public void setRisposta(String testo) {
		super.setRisposta(testo);
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
