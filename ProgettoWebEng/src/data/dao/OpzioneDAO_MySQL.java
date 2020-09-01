package data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.DAO;
import data.DataLayer;
import data.model.Domanda;
import data.model.Opzione;
import data.proxy.OpzioneProxy;
import data.DataException;
import data.DataItemProxy;

public class OpzioneDAO_MySQL extends DAO implements OpzioneDAO {

	private PreparedStatement iOpzione, uOpzione, dOpzione;
	private PreparedStatement dOpzioni, testiOp, allOpzione;
	
	public OpzioneDAO_MySQL (DataLayer d) {
		super(d);
	}
	
	@Override
    public void init() throws DataException {
        try {
            super.init();

            dOpzioni = connection.prepareStatement("SELECT id FROM opzione WHERE id_domanda=?");
            allOpzione = connection.prepareStatement("SELECT * FROM opzione WHERE id=?");
            testiOp = connection.prepareStatement("SELECT testo FROM opzione WHERE id_domanda=?");
            
            iOpzione = connection.prepareStatement("INSERT INTO opzione (id_domanda,testo) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
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


            iOpzione.close();
            uOpzione.close();
            dOpzione.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
	
	@Override
    public OpzioneProxy createOpzione() {
        return new OpzioneProxy(getDataLayer());
    }

    //helper
    private OpzioneProxy createOpzione(ResultSet rs) throws DataException {
        OpzioneProxy a = createOpzione();
        try {
        	a.setID(rs.getInt("id"));
            //a.setIDomanda(rs.getInt("id_domanda"));
            a.setTesto(rs.getString("testo"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create article object form ResultSet", ex);
        }
        return a;
    }
    
    @Override
    public Opzione getOpzione(int id) throws DataException {
        Opzione a = null;
        if (dataLayer.getCache().has(Opzione.class, id)) {
            a = dataLayer.getCache().get(Opzione.class, id);
        } else {
            try {
                allOpzione.setInt(1, id);
                try (ResultSet rs = allOpzione.executeQuery()) {
                    if (rs.next()) {
                        a = createOpzione(rs);
                        dataLayer.getCache().add(Opzione.class, a);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Non e' stato possibile carica l'opzione in base al ID", ex);
            }
        }
        return a;
    }
    
    @Override
    public List<Opzione> getOpzioni(Domanda domanda) throws DataException {
        List<Opzione> result = new ArrayList();
        try {
            dOpzioni.setInt(1, domanda.getID());            
            try (ResultSet rs = dOpzioni.executeQuery()) {
                while (rs.next()) {
                    result.add((Opzione) getOpzione(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Non e' stato possibile carica le opzioni in base al ID", ex);
        }
        return result;
    }

	@Override
	public void storeOpzione(Opzione opzione, Domanda domanda) throws DataException {
		try {
			if(opzione.getKey() != null && opzione.getDomanda() != null) {
				if(opzione instanceof DataItemProxy && ! ((DataItemProxy) opzione).isModified()) {
					return;
				} //update
				uOpzione.setString(1, opzione.getTesto());
				uOpzione.setInt(2, domanda.getID());
			} else { //insert
				iOpzione.setInt(1, domanda.getID());
				iOpzione.setNString(2, opzione.getTesto());
				if (iOpzione.executeUpdate() == 1) {
					try (ResultSet keys = iOpzione.getGeneratedKeys()) {
						if (keys.next()) {
							int key = keys.getInt(1);
							opzione.setKey(key);
							opzione.setID(key);
							dataLayer.getCache().add(Opzione.class, opzione);
						}
					}
				}
			}
		} catch (SQLException ex) {
			throw new DataException("Non e possibilie inserire la opzione", ex);
		
		}
	
	}
	
}
