package data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import data.DAO;
import data.DataItemProxy;
import data.proxy.UtenteProxy;
import data.DataException;
import data.DataLayer;
import data.dao.UtenteDAO;
import data.model.Amministratore;
import data.model.Domanda;
import data.model.Utente;

public class UtenteDAO_MySQL extends DAO implements UtenteDAO {
    private PreparedStatement sUtenteByID, getUtenti, pruomoviUtente;
    private PreparedStatement sUtente, checkUtente, utenteByEmail;
    private PreparedStatement iUtente, uUtente, dUtente;

    public UtenteDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    
    @Override
    public void init() throws DataException {
        try {
            super.init();

            sUtenteByID = connection.prepareStatement("SELECT * FROM utente WHERE ID=?");
            pruomoviUtente = connection.prepareStatement("UPDATE utente SET tipo = ? WHERE email = ?");
            getUtenti = connection.prepareStatement("SELECT * FROM utente");
            utenteByEmail = connection.prepareStatement("SELECT * FROM utente WHERE email=?");
            sUtente = connection.prepareStatement("SELECT ID AS utenteID FROM utente");
            iUtente = connection.prepareStatement("INSERT INTO utente (nome, cognome, email, password, tipo) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uUtente = connection.prepareStatement("UPDATE utente SET ID=?,nome=?,cognome=?, email=?, password=?, tipo=? WHERE ID=? and email=?");
            dUtente = connection.prepareStatement("DELETE FROM utente WHERE ID=?");
            checkUtente = connection.prepareStatement("SELECT * FROM utente WHERE email=? AND password=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {

            sUtenteByID.close();
            checkUtente.close();
            getUtenti.close();
            utenteByEmail.close();
            sUtente.close();
            iUtente.close();
            uUtente.close();
            dUtente.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public UtenteProxy createUtente() {
        return new UtenteProxy(getDataLayer());
    }

    private UtenteProxy createUtente(ResultSet rs) throws DataException {
    	UtenteProxy u = createUtente();
        try {
        	u.setID(rs.getInt("ID"));
            u.setKey(rs.getInt("ID"));
            u.setNome(rs.getString("nome"));
            u.setCognome(rs.getString("cognome"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setTipo(rs.getString("tipo"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create Utente object form ResultSet", ex);
        }
        return u;
    }

    @Override
    public Utente getUtente(int utente_key) throws DataException {
    	Utente u = null;

        if (dataLayer.getCache().has(Utente.class, utente_key)) {
           u = dataLayer.getCache().get(Utente.class, utente_key);
        } else {
            try {
                sUtenteByID.setInt(1, utente_key);
                try (ResultSet rs = sUtenteByID.executeQuery()) {
                    if (rs.next()) {
                        u = createUtente(rs);
                        dataLayer.getCache().add(Utente.class, u);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Utente by ID", ex);
            }
        }
        return u;
    }

	@Override
	public List<Utente> getUtenti() throws DataException {
		List<Utente> result = new ArrayList();
        try {           
            try (ResultSet rs = getUtenti.executeQuery()) {
                while (rs.next()) {
                    result.add((Utente) getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Non e' stato possibile carica gli utenti", ex);
        }
        return result;
	}

	@Override
	public Utente checkUtente(String email, String password) throws DataException {
		Utente utente = null;
		try {
			checkUtente.setString(1, email);
			checkUtente.setString(2, password);
			ResultSet rs = checkUtente.executeQuery();
			if(rs.next()) {
				utente = createUtente(rs);
				dataLayer.getCache().add(Utente.class, utente);
			}
		return utente;
		} catch (SQLException ex) {
			throw new DataException("Il check utente non e andata a buon fine", ex);
		}
	}
	
	
	@Override
	public void storeUtente(Utente utente) throws DataException {
		try {
			if(utente.getKey() != null && utente.getID() > 0) {
				if(utente instanceof DataItemProxy && ! ((DataItemProxy) utente).isModified()) {
					return;
				}
				
			} else { 
				iUtente.setString(1, utente.getNome());
				iUtente.setString(2, utente.getCognome());
				iUtente.setString(3, utente.getEmail());
				iUtente.setString(4, utente.getPassword());
				iUtente.setString(5, "generico");
				if (iUtente.executeUpdate() == 1) {
					try (ResultSet keys = iUtente.getGeneratedKeys()) {
						if (keys.next()) {
							int key = keys.getInt(1);
							utente.setKey(key);
							utente.setID(key);
							dataLayer.getCache().add(Utente.class, utente);
						}
					}
				}
			}
		} catch (SQLException ex) {
			throw new DataException("La registrazione non e andata a buon fine", ex);
		
		}
	}

	@Override
	public void pruomoviUtente(String email) throws DataException {
		try {
			pruomoviUtente.setString(1, "responsabile");
			pruomoviUtente.setString(2, email);
			pruomoviUtente.executeUpdate();
			
		} catch (SQLException ex) {
			throw new DataException("La pruomozione dell'utente non e andata a buon fine", ex);
		}
	}


	@Override
	public Utente getUtenteByEmail(String email) throws DataException {
		Utente u = null;
            try {
                utenteByEmail.setString(1, email);
                try (ResultSet rs = utenteByEmail.executeQuery()) {
                    if (rs.next()) {
                        u = createUtente(rs);
                        dataLayer.getCache().add(Utente.class, u);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Utente by email", ex);
            }
        
        return u;
	}

}
