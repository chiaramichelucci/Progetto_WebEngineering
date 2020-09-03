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
import data.model.Domanda;
import data.model.Sondaggio;

public class SondaggioDAO_MySQL extends DAO implements SondaggioDAO {
    private PreparedStatement sSondaggioByID;
    private PreparedStatement sSondaggio, sondaggioByEmail;
    private PreparedStatement iSondaggio, uSondaggio, dSondaggio;

    public SondaggioDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    //nuovo metodo Store.
    /*
    @Override
    public void storeSondaggio(Sondaggio sondaggio) throws DataException {
        try {
            if (sondaggio.getKey() != null && sondaggio.getID() > 0) { 
                if (sondaggio instanceof DataItemProxy && !((DataItemProxy) sondaggio).isModified()) {
                    return;
                }
                uSondaggio.setString(1, sondaggio.getTitolo());
                uSondaggio.setBoolean(2, sondaggio.getDisponibile());
                if (sondaggio.getKey() != null) {
                    uSondaggio.setString(3, sondaggio.getModalita());
                } else {
                    uSondaggio.setNull(3, java.sql.Types.INTEGER);
                }
                if (sondaggio.getKey() != null) {
                    uSondaggio.setInt(4, sondaggio.getID());
                } else {
                    uSondaggio.setNull(4, java.sql.Types.INTEGER);
                    uSondaggio.setNull(5, java.sql.Types.INTEGER);
                }

                long current_version = sondaggio.getVersion();
                long next_version = current_version + 1;

                if (uSondaggio.executeUpdate() == 0) {
                    throw new OptimisticLockException(sondaggio);
                }
                sondaggio.setVersion(next_version);
            } else { 
                iSondaggio.setString(1, sondaggio.getTitolo());
                iSondaggio.setBoolean(2, sondaggio.getDisponibile());
                if (sondaggio.getModalita() != null) {
                    iSondaggio.setString(3, sondaggio.getModalita());
                } else {
                    iSondaggio.setNull(3, java.sql.Types.INTEGER);
                }
                if (sondaggio.getKey() != null) {
                    iSondaggio.setInt(4, sondaggio.getKey());
                    iSondaggio.setString(5, sondaggio.getTitolo());
                } else {
                    iSondaggio.setNull(4, java.sql.Types.INTEGER);
                    iSondaggio.setNull(5, java.sql.Types.INTEGER);
                }
                if (iSondaggio.executeUpdate() == 1) {
                    try (ResultSet keys = iSondaggio.getGeneratedKeys()) {
                        if (keys.next()) {
                           
                            int key = keys.getInt(1);
                           
                            sondaggio.setKey(key);
                         
                            dataLayer.getCache().add(Sondaggio.class, sondaggio);
                        }
                    }
                }
            }
            if (sondaggio instanceof DataItemProxy) {
                ((DataItemProxy) sondaggio).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store sondaggio", ex);
        }} */

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sSondaggioByID = connection.prepareStatement("SELECT * FROM sondaggio WHERE ID=?");
            
            sondaggioByEmail = connection.prepareStatement("SELECT id FROM sondaggio WHERE id IN (SELECT sondaggio FROM interagisce INNER JOIN utente ON interagisce.utente = utente.id WHERE utente.email = ?)");
            sSondaggio = connection.prepareStatement("SELECT ID AS sondaggioID FROM sondaggio");
            iSondaggio = connection.prepareStatement("INSERT INTO sondaggio (titolo, testo_apertura, testo_chiusura, disponibile, modalita, URL) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uSondaggio = connection.prepareStatement("UPDATE sondaggio SET titolo=?, testo_apertura=?, testo_chiusura=?, disponibile=?, modalita=? WHERE ID=?");
            dSondaggio = connection.prepareStatement("DELETE FROM sondaggio WHERE ID=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {
        	
        	sondaggioByEmail.close();
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
            s.setID(rs.getInt("ID"));
            s.setTitolo(rs.getString("titolo"));
            s.setDisponibile(rs.getBoolean("disponibile"));
            s.setURL(rs.getString("URL"));
            s.setModalita(rs.getString("modalita"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create sondaggio object form ResultSet", ex);
        }
        return s;
    }

    @Override
    public Sondaggio getSondaggio(int id_sondaggio) throws DataException {
    	System.out.print(" getSond ");
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
                throw new DataException("Unable to load sondaggio by ID", ex);
            }
        }
        return s;
    }
    
    @Override
    public List<Sondaggio> getSondaggioByResponsabile(String email) throws DataException {
    	System.out.print(" getSondByEmail ");
    	List<Sondaggio> result = new ArrayList();
            try {
            	sondaggioByEmail.setString(1, email);
                try (ResultSet rs = sondaggioByEmail.executeQuery()) {
                    while (rs.next()) {
                        result.add((Sondaggio) getSondaggio(rs.getInt("id")));
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load sondaggi by email", ex);
            }
        return result;
    }
    
	@Override
	public List<Sondaggio> getSondaggio(Sondaggio sondaggio) throws DataException {
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
