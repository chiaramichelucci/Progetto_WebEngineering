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
import data.model.Utente;

public class AmministratoreDAO_MySQL extends DAO implements AmministratoreDAO {
    private PreparedStatement getAdmin, checkAdmin;


    public AmministratoreDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            getAdmin = connection.prepareStatement("SELECT * FROM amministratore WHERE ID=?");
            checkAdmin = connection.prepareStatement("SELECT * FROM amministratore WHERE email=? AND password=?");
           

        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {

            getAdmin.close();
            checkAdmin.close();

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
                getAdmin.setInt(1, id_amministratore);
                try (ResultSet rs = getAdmin.executeQuery()) {
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
	public Amministratore checkAdmin(String email, String password) throws DataException {
		Amministratore admin = null;
		try {
			checkAdmin.setString(1, email);
			checkAdmin.setString(2, password);
			ResultSet rs = checkAdmin.executeQuery();
			if(rs.next()) {
				admin = createAmministratore(rs);
				dataLayer.getCache().add(Amministratore.class, admin);
			}
		return admin;
		} catch (SQLException ex) {
			throw new DataException("Il check utente non e andata a buon fine", ex);
		}
	}
       
}