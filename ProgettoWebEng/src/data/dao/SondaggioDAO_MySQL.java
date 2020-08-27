package data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import data.DAO;
import data.DataItemProxy;
import data.proxy.SondaggioProxy;
import data.DataException;
import data.OptimisticLockException;
import data.DataLayer;
import data.dao.SondaggioDAO;
import data.model.Amministratore;
import data.model.Sondaggio;

public class SondaggioDAO_MySQL extends DAO implements SondaggioDAO {
    private PreparedStatement sSondaggioByID;
    private PreparedStatement sSondaggio, sSondaggioByIssue, sUnassignedSondaggio;
    private PreparedStatement iSondaggio, uSondaggio, dSondaggio;

    public SondaggioDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sSondaggioByID = connection.prepareStatement("SELECT * FROM sondaggio WHERE ID=?");
            
            sSondaggio = connection.prepareStatement("SELECT ID AS sondaggioID FROM sondaggio");
            iSondaggio = connection.prepareStatement("INSERT INTO sondaggio (titolo,disponibile, modalita, URL) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uSondaggio = connection.prepareStatement("UPDATE sondaggio SET titolo=?,disponibile=?, modalit�=? WHERE ID=?");
            dSondaggio = connection.prepareStatement("DELETE FROM sondaggio WHERE ID=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {

            sSondaggioByID.close();
            sSondaggio.close();
            iSondaggio.close();
            uSondaggio.close();
            dSondaggio.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public SondaggioProxy createSondaggio() {
        return new SondaggioProxy(getDataLayer());
    }

    private SondaggioProxy createSondaggio(ResultSet rs) throws DataException {
    	SondaggioProxy s = createSondaggio();
        try {
            s.setKey(rs.getInt("ID"));
            s.setTitolo(rs.getString("titolo"));
            s.setDisponibile(rs.getBoolean("disponibile"));
            s.setNDomande(rs.getInt("n_domande"));
            s.setURL(rs.getString("URL"));
            s.setModalita(rs.getString("modalita"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create sondaggio object form ResultSet", ex);
        }
        return s;
    }

    @Override
    public Sondaggio getSondaggio(int id_sondaggio) throws DataException {
        Sondaggio s = null;

        if (dataLayer.getCache().has(Sondaggio.class, id_sondaggio)) {
            s = dataLayer.getCache().get(Sondaggio.class, id_sondaggio);
        } else {
            try {
                sSondaggioByID.setInt(1, id_sondaggio);
                try (ResultSet rs = sSondaggioByID.executeQuery()) {
                    if (rs.next()) {
                        s = createSondaggio(rs);
                        dataLayer.getCache().add(Sondaggio.class, s);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load amministratore by ID", ex);
            }
        }
        return s;
    }
    

	@Override
	public void save(Sondaggio sondaggio) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Sondaggio sondaggio) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Sondaggio sondaggio) {
		// TODO Auto-generated method stub	
	}

	@Override
	public List<Sondaggio> getSondaggio(Sondaggio sondaggio) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sondaggio> getSondaggio() throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeSondaggio(Sondaggio sondaggio) throws DataException {
		System.out.print("Sono arrivato qui 2");
		try {
			if(sondaggio.getKey() != null && sondaggio.getID() > 0) {//update
				if(sondaggio instanceof DataItemProxy && ! ((DataItemProxy) sondaggio).isModified()) {
					return;
				}
				uSondaggio.setString(1, sondaggio.getTitolo());
				uSondaggio.setBoolean(2, sondaggio.getDisponibile());
				uSondaggio.setString(3, sondaggio.getModalita());
				//uSondaggio.setString(4, sondaggio.getUrl());
				uSondaggio.setInt(4, sondaggio.getID());
			} else { //insert
				iSondaggio.setString(1, sondaggio.getTitolo());
				iSondaggio.setBoolean(2, sondaggio.getDisponibile());
				iSondaggio.setString(3, sondaggio.getModalita());
				iSondaggio.setString(4, "");
				if(iSondaggio.executeUpdate() == 1) {
					try (ResultSet keys = iSondaggio.getGeneratedKeys()) {
						if (keys.next()) {
							int key = keys.getInt(1);
							sondaggio.setKey(key);
							sondaggio.setID(key);
							System.out.print("chiave generata" + key);
							dataLayer.getCache().add(Sondaggio.class, sondaggio);
						}
					}
				}
			}
			
			if (sondaggio instanceof DataItemProxy) {
				((DataItemProxy) sondaggio).setModified(false);
			}
		
		} catch (SQLException ex) {
            throw new DataException("Non e possibile inserire il sondaggio", ex);
         
		}
	}
}
