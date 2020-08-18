package data.dao;

import data.DAO;

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
            sSondaggioByIssue = connection.prepareStatement("SELECT ID AS sondaggioID FROM sondaggio WHERE issueID=?");
            sSondaggio = connection.prepareStatement("SELECT ID AS sondaggioID FROM sondaggio");
            sUnassignedSondaggio = connection.prepareStatement("SELECT ID AS sondaggioID FROM sondaggio WHERE issueID IS NULL");


            iSondaggio = connection.prepareStatement("INSERT INTO sondaggio (ID, titolo,disponibile, modalità, URL, n_domande) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uSondaggio = connection.prepareStatement("UPDATE sondaggio SET ID=?,titolo=?,disponibile=?, modalità=?, URL=?, n_domande=? WHERE ID=? and titolo=?");
            dSondaggio = connection.prepareStatement("DELETE FROM sondaggio WHERE ID=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {

            sSondaggioByID.close();

            sSondaggioByIssue.close();
            sSondaggio.close();
            sUnassignedSondaggio.close();

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
            s.setModalita(rs.getString("modalità"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create sondaggio object form ResultSet", ex);
        }
        return s;
    }

    @Override
    public Sondaggio getSondaggio(int sondaggio_key) throws DataException {
    	Sondaggio s = null;

        if (dataLayer.getCache().has(Sondaggio.class, sondaggio_key)) {
            s = dataLayer.getCache().get(Sondaggio.class, sondaggio_key);
        } else {
            try {
                sSondaggioByID.setInt(1, sondaggio_key);
                try (ResultSet rs = sSondaggioByID.executeQuery()) {
                    if (rs.next()) {
                        s = createSondaggio(rs);
                        dataLayer.getCache().add(Sondaggio.class, s);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Sondaggio by ID", ex);
            }
        }
        return s;
    }

    @Override
    public List<Sondaggio> getSondaggio(Issue issue) throws DataException {
        List<Sondaggio> result = new ArrayList();

        try {
            sSondaggioByIssue.setInt(1, issue.getKey());            
            try (ResultSet rs = sSondaggioByIssue.executeQuery()) {
                while (rs.next()) {
                    result.add((Sondaggio) getSondaggio(rs.getInt("sondaggioID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Sondaggio by issue", ex);
        }
        return result;
    }

    @Override
    public List<Sondaggio> getSondaggio() throws DataException {
        List<Sondaggio> result = new ArrayList();

        try (ResultSet rs = sSondaggio.executeQuery()) {
            while (rs.next()) {
                result.add((Sondaggio) getSondaggio(rs.getInt("sondaggioID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Sondaggio", ex);
        }
        return result;
    }

    @Override
    public List<Sondaggio> getUnassignedSondaggio() throws DataException {
        List<Sondaggio> result = new ArrayList();

        try (ResultSet rs = sUnassignedSondaggio.executeQuery()) {
            while (rs.next()) {
                result.add((Sondaggio) getSondaggio(rs.getInt("sondaggioID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load unassigned Sondaggio", ex);
        }
        return result;
    }

    @Override
    public void storeSondaggio(Sondaggio sondaggio) throws DataException {
        try {
            if (sondaggio.getKey() != null && sondaggio.getKey() > 0) { 
                if (sondaggio instanceof DataItemProxy && !((DataItemProxy) sondaggio).isModified()) {
                    return;
                }

            if (sondaggio instanceof DataItemProxy) {
                ((DataItemProxy) sondaggio).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store Sondaggio", ex);
        }
    }

}
