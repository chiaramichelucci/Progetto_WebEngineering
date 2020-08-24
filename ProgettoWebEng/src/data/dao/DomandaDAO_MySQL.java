package data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.DAO;
import data.DataException;
import data.DataLayer;
import data.model.Domanda;
import data.model.Sondaggio;
import data.proxy.DomandaProxy;
import data.DataItemProxy;

public class DomandaDAO_MySQL extends DAO implements DomandaDAO {
	
	private PreparedStatement domandeBySondaggio;
	private PreparedStatement domandaByCodice;
    private PreparedStatement cDomande, cDomandaBySondaggio;
    private PreparedStatement iDomanda, uDomanda, dDomanda;
    
    public DomandaDAO_MySQL (DataLayer d) {
    	super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();

            domandaByCodice = connection.prepareStatement("SELECT * FROM domanda WHERE codice=?");
            cDomandaBySondaggio = connection.prepareStatement("SELECT codice AS codiceDomanda FROM domanda WHERE sondaggio=?");
            cDomande = connection.prepareStatement("SELECT codice AS codiceDomanda FROM domanda");

            iDomanda = connection.prepareStatement("INSERT INTO domanda (codice,text,nota,tipo,obbligatorio,sondaggio) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uDomanda = connection.prepareStatement("UPDATE domanda SET codice=?,testo=?,nota=?,tipo=?,obbligatoria=?, sondaggio=? WHERE codice=?");
            dDomanda = connection.prepareStatement("DELETE FROM domanda WHERE codice=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
    	
        try {

            domandaByCodice.close();

            cDomandaBySondaggio.close();
            cDomande.close();

            iDomanda.close();
            uDomanda.close();
            dDomanda.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
    @Override
    public DomandaProxy creaDomanda() {
        return new DomandaProxy(getDataLayer());
    }

    private DomandaProxy creaDomanda(ResultSet rs) throws DataException {
        DomandaProxy a = creaDomanda();
        try {
            a.setCodice(rs.getString("codice"));
            a.setTesto(rs.getString("testo"));
            a.setNota(rs.getString("nota"));
            a.setObbligatoria(rs.getBoolean("obbligatorio"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create article object form ResultSet", ex);
        }
        return a;
    }
    
    @Override
    public Domanda getDomanda(String codice) throws DataException {
        Domanda a = null;
        if (dataLayer.getCache().has(Domanda.class, codice)) {
            a = dataLayer.getCache().get(Domanda.class, codice);
        } else {
            try {
                cDomandaBySondaggio.setString(1, codice);
                try (ResultSet rs = cDomandaBySondaggio.executeQuery()) {
                    if (rs.next()) {
                        a = creaDomanda(rs);
                        dataLayer.getCache().add(Domanda.class, a);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load article by ID", ex);
            }
        }
        return a;
    }
    
    @Override
    public List<Domanda> getDomande(Sondaggio sondaggio) throws DataException {
        List<Domanda> result = new ArrayList();

        try {
            domandeBySondaggio.setInt(1, sondaggio.getID());            
            try (ResultSet rs = domandeBySondaggio.executeQuery()) {
                while (rs.next()) {
                    result.add((Domanda) getDomanda(rs.getString("codice")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load articles by issue", ex);
        }
        return result;
    }
    
    public void delete(Domanda domanda) {
    	
    }

	@Override
	public List<Domanda> getDomande() throws DataException {
		List<Domanda> result = new ArrayList();

        try {
            Sondaggio sondaggio = null;
			domandeBySondaggio.setInt(1, sondaggio.getID());            
            try (ResultSet rs = domandeBySondaggio.executeQuery()) {
                while (rs.next()) {
                    result.add((Domanda) getDomanda(rs.getString("codice")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load articles by issue", ex);
        }
        return result;
	}

	@Override
	public void storeDomanda(Domanda domanda, Sondaggio sondaggio) throws DataException {
		try {
			if(domanda.getKey() != null && domanda.getCodice() == "") {
				if(domanda instanceof DataItemProxy && ! ((DataItemProxy) domanda).isModified()) {
					return;
				} //update
				uDomanda.setString(1, domanda.getCodice());
				uDomanda.setString(2, domanda.getTesto());
				uDomanda.setString(3, domanda.getNota());
				uDomanda.setString(4, domanda.getTipo());
				uDomanda.setBoolean(5, domanda.getObbligatoria());
				uDomanda.setInt(6, sondaggio.getID());
				uDomanda.setString(7, domanda.getCodice());
			} else { //insert
				iDomanda.setString(1, domanda.getCodice());
				iDomanda.setString(2, domanda.getTesto());
				iDomanda.setString(3, domanda.getNota());
				iDomanda.setString(4, domanda.getTipo());
				iDomanda.setBoolean(5, domanda.getObbligatoria());
				iDomanda.setInt(6, sondaggio.getID());
				if (iDomanda.executeUpdate() == 1) {
					try (ResultSet keys = iDomanda.getGeneratedKeys()) {
						if (keys.next()) {
							String key = keys.getString("");
							domanda.setKey("");
							dataLayer.getCache().add(Domanda.class, domanda);
						}
					}
				}
			}
		} catch (SQLException ex) {
			throw new DataException("Non e possibilie inserire la domanda", ex);
		}
		
	}

	@Override
	public Sondaggio getSondagio(int idSondaggio) throws DataException {
		return null;
		
	}
    
    
    
}
