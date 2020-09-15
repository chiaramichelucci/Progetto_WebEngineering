package data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import data.DAO;
import data.DataException;
import data.DataItemProxy;
import data.DataLayer;
import data.model.Domanda;
import data.model.Risposta;
import data.model.Sondaggio;
import data.proxy.OpzioneProxy;
import data.proxy.RispostaProxy;

public class RispostaDAO_MySQL extends DAO implements RispostaDAO {
	
	private PreparedStatement iRisposta, uRisposta, dRisposta;
	private PreparedStatement userRisp, domRisp, testoRisp;
	
	public RispostaDAO_MySQL (DataLayer d) {
		super(d);
	}
	
	@Override
    public void init() throws DataException {
        try {
            super.init();

            userRisp = connection.prepareStatement("SELECT utente FROM risposta WHERE codice_domanda=?");
            domRisp = connection.prepareStatement("SELECT codice_domanda AS codiceDomanda FROM risposta");
            testoRisp = connection.prepareStatement("SELECT testo FROM risposta WHERE codice_domanda=?");
            
            iRisposta = connection.prepareStatement("INSERT INTO risposta (id_domanda, id_sondaggio, id_utente, testo_risposta) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uRisposta = connection.prepareStatement("UPDATE risposta SET id_domanda=?, id_sondaggio=?, id_utente=?, testo_risposta=? WHERE id=?");
            dRisposta = connection.prepareStatement("DELETE FROM risposta WHERE codice_domanda=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }
	
	@Override
    public void destroy() throws DataException {
        try {

            userRisp.close();
            domRisp.close();
            testoRisp.close();

            iRisposta.close();
            uRisposta.close();
            dRisposta.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
	
	@Override
    public RispostaProxy creaRisposta() {
        return new RispostaProxy(getDataLayer());
    }

    //helper
    private RispostaProxy creaRisposta(ResultSet rs) throws DataException {
        RispostaProxy a = creaRisposta();
        try {
            a.setDomandaId(rs.getInt("id_domanda"));
            a.setUtenteKey(rs.getInt("id_utente"));
            a.setRisposta(rs.getString("testo"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create article object form ResultSet", ex);
        }
        return a;
    }
    
    public void storeRisposta(Risposta risposta) throws DataException {
    	Domanda domanda = risposta.getDomanda();
    	Sondaggio sondaggio = risposta.getSondaggio();
		try {
			if(risposta.getKey() != null && risposta.getID() > 0) {
				if(risposta instanceof DataItemProxy && ! ((DataItemProxy) risposta).isModified()) {
					return;
				} //update
				uRisposta.setInt(1, domanda.getID());
				uRisposta.setInt(2, sondaggio.getID());
				uRisposta.setNull(3, Types.INTEGER);
				uRisposta.setString(4, risposta.getRisposta());
			} else { //insert
				iRisposta.setInt(1, domanda.getID());
				iRisposta.setInt(2, sondaggio.getID());
				iRisposta.setNull(3, Types.INTEGER);
				iRisposta.setString(4, risposta.getRisposta());
				if (iRisposta.executeUpdate() == 1) {
					try (ResultSet keys = iRisposta.getGeneratedKeys()) {
						if (keys.next()) {
							int key = keys.getInt(1);
							risposta.setKey(key);
							risposta.setID(key);
							dataLayer.getCache().add(Risposta.class, risposta);
						}
					}
				}
			}
		} catch (SQLException ex) {
			throw new DataException("Non e possibilie inserire la risposta", ex);
		}
		
	}
	
}
