package data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import data.DAO;
import data.DataException;
import data.DataLayer;
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
            
            iRisposta = connection.prepareStatement("INSERT INTO risposta (codice_domanda,id_utente,testo) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            uRisposta = connection.prepareStatement("UPDATE risposta SET testo=? WHERE codice_domanda=?");
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
            a.setDomanda(rs.getString("codice_domanda"));
            a.setUtente(rs.getString("id_utente"));
            a.setRisposta(rs.getString("testo"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create article object form ResultSet", ex);
        }
        return a;
    }
	
}
