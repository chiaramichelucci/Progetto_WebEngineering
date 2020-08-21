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
import data.model.Utente;

public class UtenteDAO_MySQL extends DAO implements UtenteDAO {
    private PreparedStatement sUtenteByID;
    private PreparedStatement sUtente;
    private PreparedStatement iUtente, uUtente, dUtente;

    public UtenteDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sUtenteByID = connection.prepareStatement("SELECT * FROM utente WHERE ID=?");
            sUtente = connection.prepareStatement("SELECT ID AS utenteID FROM utente");
            iUtente = connection.prepareStatement("INSERT INTO utente (ID,nome, cognome, email, password, tipo) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uUtente = connection.prepareStatement("UPDATE utente SET ID=?,nome=?,cognome=?, email=?, password=?, tipo=? WHERE ID=? and email=?");
            dUtente = connection.prepareStatement("DELETE FROM utente WHERE ID=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {

            sUtenteByID.close();
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

    public void delete1 (Utente utente) {
    	
    }

	@Override
	public List<Utente> getUtente(Utente utente) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Utente> getUtente() throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Utente> getUnassignedUtente() throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeUtente(Utente utente) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(Utente utente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Utente utente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Utente utente) {
		// TODO Auto-generated method stub
		
	}
	
}
