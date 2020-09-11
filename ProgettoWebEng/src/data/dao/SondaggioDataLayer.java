package data.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import data.DataLayer;
import data.dao.DomandaDAO;
import data.dao.DomandaDAO_MySQL;
import data.dao.OpzioneDAO;
import data.dao.OpzioneDAO_MySQL;
import data.dao.SondaggioDAO;
import data.dao.SondaggioDAO_MySQL;
import data.model.Amministratore;
import data.model.Domanda;
import data.model.Opzione;
import data.model.Risposta;
import data.model.Sondaggio;
import data.model.Utente;
import data.DataException;

public class SondaggioDataLayer extends DataLayer {

	public SondaggioDataLayer(DataSource datasource) throws SQLException {
        super(datasource);
    }

    @Override
    public void init() throws DataException {
    	registerDAO(Sondaggio.class, new SondaggioDAO_MySQL(this));
        registerDAO(Domanda.class, new DomandaDAO_MySQL(this));
        registerDAO(Opzione.class, new OpzioneDAO_MySQL(this));
        registerDAO(Risposta.class, new RispostaDAO_MySQL(this));
        registerDAO(Utente.class, new UtenteDAO_MySQL(this));
        registerDAO(Amministratore.class, new AmministratoreDAO_MySQL(this));
    }

    //helpers    
    public DomandaDAO getDomandaDAO() {
        return (DomandaDAO) getDAO(Domanda.class);
    }
    public OpzioneDAO getOpzioneDAO() {
        return (OpzioneDAO) getDAO(Opzione.class);
    }   
    public SondaggioDAO getSondaggioDAO() {
        return (SondaggioDAO) getDAO(Sondaggio.class);
    }    
    public RispostaDAO getRispostaDAO() {
        return (RispostaDAO) getDAO(Risposta.class);
    }    
    public UtenteDAO getUtenteDAO() {
        return (UtenteDAO) getDAO(Utente.class);
    }
    public AmministratoreDAO getAmministratoreDAO() {
        return (AmministratoreDAO) getDAO(Amministratore.class);
    }	
}
