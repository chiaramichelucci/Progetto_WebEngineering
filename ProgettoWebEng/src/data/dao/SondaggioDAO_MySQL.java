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
import data.model.Utente;

public class SondaggioDAO_MySQL extends DAO implements SondaggioDAO {
    private PreparedStatement sSondaggioByID;
    private PreparedStatement sSondaggio, sondaggioByEmail, sondaggioByTitolo;
    private PreparedStatement iSondaggio, uSondaggio, dSondaggio, getSondaggi;
    private PreparedStatement urlSondaggio, respSondaggio, checkPermesso, invitaUtente;

    public SondaggioDAO_MySQL(DataLayer d) {
        super(d);
    }
    

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sSondaggioByID = connection.prepareStatement("SELECT * FROM sondaggio WHERE ID=?");
            
            sondaggioByEmail = connection.prepareStatement("SELECT id FROM sondaggio WHERE id IN (SELECT sondaggio FROM interagisce INNER JOIN utente ON interagisce.utente = utente.id WHERE utente.email = ?)");
            sSondaggio = connection.prepareStatement("SELECT ID AS sondaggioID FROM sondaggio");
            sondaggioByTitolo = connection.prepareStatement("SELECT * FROM sondaggio WHERE titolo=?");
            iSondaggio = connection.prepareStatement("INSERT INTO sondaggio (titolo, testo_apertura, testo_chiusura, disponibile, modalita, URL) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uSondaggio = connection.prepareStatement("UPDATE sondaggio SET titolo=?, testo_apertura=?, testo_chiusura=?, disponibile=?, modalita=? WHERE ID=?");
            dSondaggio = connection.prepareStatement("DELETE FROM sondaggio WHERE ID=?");
            getSondaggi = connection.prepareStatement("SELECT * FROM sondaggi WHERE disponibile=1");
            urlSondaggio = connection.prepareStatement("UPDATE sondaggio SET url=? WHERE id=?");
            respSondaggio = connection.prepareStatement("INSERT INTO interagisce (utente, sondaggio, tipo) VALUES (?,?,?)");
            checkPermesso = connection.prepareStatement("SELECT tipo FROM interagisce WHERE sondaggio=? AND utente=?");
            invitaUtente = connection.prepareStatement("INSERT INTO interagisce (utente, tipo, sondaggio) VALUES(?,?,?)");

        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {
        	
        	sondaggioByEmail.close();
            sSondaggioByID.close();
            sondaggioByTitolo.close();
            sSondaggio.close();
            iSondaggio.close();
            uSondaggio.close();
            dSondaggio.close();
            urlSondaggio.close();
            checkPermesso.close();
            respSondaggio.close();

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
            s.setBeginText(rs.getString("testo_apertura"));
            s.setBeginText(rs.getString("testo_chiusura"));
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
                throw new DataException("Unable to load sondaggio by ID", ex);
            }
        }
        return s;
    }
    
    @Override
    public List<Sondaggio> getSondaggioByResponsabile(String email) throws DataException {
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
	public List<Sondaggio> getSondaggi() throws DataException {
		List<Sondaggio> result = new ArrayList();
        try {           
            try (ResultSet rs = getSondaggi.executeQuery()) {
                while (rs.next()) {
                    result.add((Sondaggio) getSondaggio(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Non e' stato possibile carica gli sondaggi", ex);
        }
        return result;
	}

	@Override
	public void storeSondaggio(Sondaggio sondaggio, int id_utente) throws DataException {
		try {
			if(sondaggio.getKey() != null && sondaggio.getID() > 0) {//update
				if(sondaggio instanceof DataItemProxy && ! ((DataItemProxy) sondaggio).isModified()) {
					return;
				}
			} else { //insert
				iSondaggio.setString(1, sondaggio.getTitolo());
				iSondaggio.setString(2, sondaggio.getBeginText());
				iSondaggio.setString(3, sondaggio.getEndText());
				iSondaggio.setBoolean(4, sondaggio.getDisponibile());
				iSondaggio.setString(5, sondaggio.getModalita());
				iSondaggio.setString(6, "");
				if(iSondaggio.executeUpdate() == 1) {
					try (ResultSet keys = iSondaggio.getGeneratedKeys()) {
						if (keys.next()) {
							int key = keys.getInt(1);
							sondaggio.setKey(key);
							sondaggio.setID(key);
							dataLayer.getCache().add(Sondaggio.class, sondaggio);
						}
					}
				}
				urlSondaggio.setString(1, "http://localhost:8080/ProgettoWebEng/comp?id=" + sondaggio.getID());
				urlSondaggio.setInt(2, sondaggio.getID());
				urlSondaggio.executeUpdate();
				respSondaggio.setInt(1, id_utente);
				respSondaggio.setInt(2, sondaggio.getID());
				respSondaggio.setString(3, "responsabile");
				respSondaggio.executeUpdate();
			}
			
			if (sondaggio instanceof DataItemProxy) {
				((DataItemProxy) sondaggio).setModified(false);
			}
		
		} catch (SQLException ex) {
            throw new DataException("Non e possibile inserire il sondaggio", ex);
         
		}
	}

	@Override
	public void updateSondaggio(Sondaggio sondaggio) throws DataException {
		System.out.print(" updateSond ");
		try {
		uSondaggio.setString(1, sondaggio.getTitolo());
		uSondaggio.setString(2, sondaggio.getBeginText());
		uSondaggio.setString(3, sondaggio.getEndText());
		uSondaggio.setBoolean(4, sondaggio.getDisponibile());
		uSondaggio.setString(5, sondaggio.getModalita());
		//uSondaggio.setString(6, sondaggio.getUrl());
		uSondaggio.setInt(6, sondaggio.getID());
		uSondaggio.executeUpdate();
		} catch (SQLException ex) {
			throw new DataException("Non e possibile aggiornare il sondaggio", ex);
		}
	}

	@Override
	public void deleteSondaggio(Sondaggio sondaggio) throws DataException {
		try {
			dSondaggio.setInt(1, sondaggio.getID());
			dSondaggio.executeQuery();
			} catch (SQLException ex) {
				throw new DataException("Non e possibile cancelare il sondaggio", ex);
			}
		
	}

	@Override
	public boolean checkPermesso(Sondaggio sondaggio,Utente utente) throws DataException {
		boolean permesso = false;
		try {
			checkPermesso.setInt(1, sondaggio.getID());
			checkPermesso.setInt(2, utente.getID());
			ResultSet rs = checkPermesso.executeQuery();
			if(rs.next()) {
				if(rs.getString("tipo").equalsIgnoreCase("partecipante")) {
					permesso=true;;
				} else {
					permesso=false;
				}
			}
			return permesso;
		} catch (SQLException ex) {
			throw new DataException("Non e possibile ottenere il tipo", ex);
		}
	}

	@Override
	public Sondaggio getSondaggioByTitolo(String titolo) throws DataException {
		Sondaggio s = null;

            try {
                sondaggioByTitolo.setString(1, titolo);
                try (ResultSet rs = sondaggioByTitolo.executeQuery()) {
                    if (rs.next()) {
                        s = createSondaggio(rs);
                        dataLayer.getCache().add(Sondaggio.class, s);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load sondaggio by titolo", ex);
            }
        return s;
	}

	@Override
	public void invitaUtente(Utente utente, Sondaggio sondaggio) throws DataException {
		try {
			invitaUtente.setInt(1, utente.getID());
			invitaUtente.setString(2, "partecipante");
			invitaUtente.setInt(3, sondaggio.getID());
			invitaUtente.executeQuery();
		}catch(SQLException ex) {
			throw new DataException("Non e stato possibile invitare l'utente al sondaggio", ex);
		}
		
	}
}
