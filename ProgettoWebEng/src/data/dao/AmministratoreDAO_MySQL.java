package data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.DAO;
import data.DataItemProxy;
import data.proxy.AmministratoreProxy;
import data.DataException;
import data.DataLayer;
import data.dao.AmministratoreDAO;
import data.model.Amministratore;
import data.model.Domanda;
import data.model.Sondaggio;

public class AmministratoreDAO_MySQL extends DAO implements AmministratoreDAO {
    private PreparedStatement sAmministratoreByID;
    private PreparedStatement sAmministratore, sAmministratoreByIssue, sUnassignedAmministratore;
    private PreparedStatement iAmministratore, uAmministratore, dAmministratore;

    public AmministratoreDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sAmministratoreByID = connection.prepareStatement("SELECT * FROM amministratore WHERE ID=?");
           
            sAmministratore = connection.prepareStatement("SELECT ID AS amministratoreID FROM amministratore");
            iAmministratore = connection.prepareStatement("INSERT INTO amministratore (ID, email, password) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uAmministratore = connection.prepareStatement("UPDATE amministratore SET ID=?,email=?,password=? WHERE ID=? ");
            dAmministratore = connection.prepareStatement("DELETE FROM amministratore WHERE ID=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {

            sAmministratoreByID.close();
            sAmministratore.close();
            iAmministratore.close();
            uAmministratore.close();
            dAmministratore.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public AmministratoreProxy createAmministratore() {
        return new AmministratoreProxy(getDataLayer());
    }

    //helper
    private AmministratoreProxy createAmministratore(ResultSet rs) throws DataException {
        AmministratoreProxy a = createAmministratore();
        try {
            a.setKey(rs.getInt("ID"));
            a.setEmail(rs.getString("email"));
            a.setPassword(rs.getString("password"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create amministratore object form ResultSet", ex);
        }
        return a;
    }

    @Override
    public Amministratore getAmministratore(int id_amministratore) throws DataException {
        Amministratore a = null;

        if (dataLayer.getCache().has(Amministratore.class, id_amministratore)) {
            a = dataLayer.getCache().get(Amministratore.class, id_amministratore);
        } else {
            try {
                sAmministratoreByID.setInt(1, id_amministratore);
                try (ResultSet rs = sAmministratoreByID.executeQuery()) {
                    if (rs.next()) {
                        a = createAmministratore(rs);
                        dataLayer.getCache().add(Amministratore.class, a);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load amministratore by ID", ex);
            }
        }
        return a;
    }
    
    public void delete1 (Amministratore amministratore) {
    	
    }

	@Override
	public List<Amministratore> getAmministratore(Amministratore amministratore) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Amministratore> getAmministratore() throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeAmministratore(Amministratore amministratore) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(Amministratore amministratore) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Amministratore amministratore) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Amministratore amministratore) {
		// TODO Auto-generated method stub
		
	}
    
    
    
}