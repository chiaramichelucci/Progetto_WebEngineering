package data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import data.DAO;
import data.DataLayer;
import data.model.Domanda;
import data.model.Opzione;
import data.proxy.OpzioneProxy;
import data.DataException;

public class OpzioneDAO_MySQL extends DAO implements OpzioneDAO {

	private PreparedStatement iOpzione, uOpzione, dOpzione;
	private PreparedStatement dOpzioni, testoOp, testiOp;
	
	public OpzioneDAO_MySQL (DataLayer d) {
		super(d);
	}
	
	@Override
    public void init() throws DataException {
        try {
            super.init();

            dOpzioni = connection.prepareStatement("SELECT * FROM opzione WHERE codce_domanda=?");
            testoOp = connection.prepareStatement("SELECT codice_Domanda AS codiceDomanda FROM opzione WHERE codice_domanda=?");
            testiOp = connection.prepareStatement("SELECT codice_domanda AS codiceDomanda FROM opzione WHERE codice_domanda=?");
            
            iOpzione = connection.prepareStatement("INSERT INTO opzione (codice_domanda,testo) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            uOpzione = connection.prepareStatement("UPDATE opzione SET testo=? WHERE codice_domanda=?");
            dOpzione = connection.prepareStatement("DELETE FROM opzione WHERE codice_domanda=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }
	
	@Override
    public void destroy() throws DataException {
        try {

            dOpzioni.close();

            testoOp.close();

            iOpzione.close();
            uOpzione.close();
            dOpzione.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
	
	@Override
    public OpzioneProxy creaOpzione() {
        return new OpzioneProxy(getDataLayer());
    }

    //helper
    private OpzioneProxy creaOpzione(ResultSet rs) throws DataException {
        OpzioneProxy a = creaOpzione();
        try {
            a.setDomanda(rs.getString("codice_domanda"));
            a.setOpzione(rs.getString("testo"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create article object form ResultSet", ex);
        }
        return a;
    }
    
    @Override
    public Opzione getOpzione(String codice_domanda) throws DataException {
        Opzione a = null;
        if (dataLayer.getCache().has(Opzione.class, codice_domanda)) {
            a = dataLayer.getCache().get(Opzione.class, codice_domanda);
        } else {
            try {
                //dOpzioni.setInt(1, codice_domanda);
                try (ResultSet rs = dOpzioni.executeQuery()) {
                    if (rs.next()) {
                        a = creaOpzione(rs);
                        dataLayer.getCache().add(Opzione.class, a);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load article by ID", ex);
            }
        }
        return a;
    }
    
    @Override
    public List<Opzione> getOpzioni(Domanda domanda) throws DataException {
        List<Opzione> result = new ArrayList();

        try {
            testiOp.setInt(1, domanda.getCodice());            
            try (ResultSet rs = testiOp.executeQuery()) {
                while (rs.next()) {
                    result.add((Opzione) getOpzione(rs.getString("codiceDomanda")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load articles by issue", ex);
        }
        return result;
    }
 
    // funzione store

	@Override
	public void storeOpzione() {
		// TODO Auto-generated method stub
		
	}
	
}
