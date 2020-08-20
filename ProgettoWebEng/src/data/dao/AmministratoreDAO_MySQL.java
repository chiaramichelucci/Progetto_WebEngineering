package data.dao;

import java.sql.PreparedStatement;
import java.sql.*;

import data.DAO;
import data.DataException;
import data.DataLayer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.DAO;
import data.DataItemProxy;
import data.proxy.AmministratoreProxy;
import data.DataException;
import data.DataLayer;
import data.dao.AmministratoreDAO;
import data.model.Amministratore;


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
            sAmministratoreByIssue = connection.prepareStatement("SELECT ID AS amministratoreID FROM amministratore WHERE issueID=?");
            sAmministratore = connection.prepareStatement("SELECT ID AS amministratoreID FROM amministratore");
            sUnassignedAmministratore = connection.prepareStatement("SELECT ID AS amministratoreID FROM amministratore WHERE issueID IS NULL");


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

            sAmministratoreByIssue.close();
            sAmministratore.close();
            sUnassignedAmministratore.close();

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
    public Amministratore getAmministratore(int amministratore_key) throws DataException {
        Amministratore a = null;

        if (dataLayer.getCache().has(Amministratore.class, amministratore_key)) {
            a = dataLayer.getCache().get(Amministratore.class, amministratore_key);
        } else {
            try {
                sAmministratoreByID.setInt(1, amministratore_key);
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

    @Override
    public List<Amministratore> getAmministratore(Amministratore amministratore) throws DataException {
        List<Amministratore> result = new ArrayList();

        try {
            sAmministratoreByIssue.setInt(1, amministratore.getKey());            
            try (ResultSet rs = sAmministratoresByIssue.executeQuery()) {
                while (rs.next()) {
                    result.add((Amministratore) getAmministratore(rs.getInt("amministratoreID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load amministratore by issue", ex);
        }
        return result;
    }

    @Override
    public List<Amministratore> getAmministratore() throws DataException {
        List<Amministratore> result = new ArrayList();

        try (ResultSet rs = sAmministratore.executeQuery()) {
            while (rs.next()) {
                result.add((Amministratore) getAmministratore(rs.getInt("amministratoreID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load amministratore", ex);
        }
        return result;
    }

    @Override
    public List<Amministratore> getUnassignedAmministratore() throws DataException {
        List<Amministratore> result = new ArrayList();

        try (ResultSet rs = sUnassignedAmministratore.executeQuery()) {
            while (rs.next()) {
                result.add((Amministratore) getAmministratore(rs.getInt("amministratoreID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load unassigned amministratore", ex);
        }
        return result;
    }

    @Override
    public void storeAmministratore(Amministratore amministratore) throws DataException {
        try {
            if (amministratore.getKey() != null && amministratore.getKey() > 0) { 
                if (amministratore instanceof DataItemProxy && !((DataItemProxy) amministratore).isModified()) {
                    return;
                }

            if (amministratore instanceof DataItemProxy) {
                ((DataItemProxy) amministratore).setModified(false);
            }
        } 
        }
