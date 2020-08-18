package data.dao;

import data.DAO;

public class UtenteDAO_MySQL extends DAO implements UtenteDAO {
    private PreparedStatement sUtenteByID;
    private PreparedStatement sUtente, sUtenteByIssue, sUnassignedUtente;
    private PreparedStatement iUtente, uUtente, dUtente;

    public UtenteDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sUtenteByID = connection.prepareStatement("SELECT * FROM utente WHERE ID=?");
            sUtenteByIssue = connection.prepareStatement("SELECT ID AS utenteID FROM utente WHERE issueID=?");
            sUtente = connection.prepareStatement("SELECT ID AS utenteID FROM utente");
            sUnassignedUtente = connection.prepareStatement("SELECT ID AS utenteID FROM utente WHERE issueID IS NULL");


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

            sUtenteByIssue.close();
            sUtente.close();
            sUnassignedUtente.close();

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
            s = dataLayer.getCache().get(Utente.class, utente_key);
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
    public List<Utente> getUtente(Issue issue) throws DataException {
        List<Utente> result = new ArrayList();

        try {
            sUtenteByIssue.setInt(1, issue.getKey());            
            try (ResultSet rs = sUtenteByIssue.executeQuery()) {
                while (rs.next()) {
                    result.add((Utente) getUtente(rs.getInt("utenteID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Utente by issue", ex);
        }
        return result;
    }

    @Override
    public List<Utente> getUtente() throws DataException {
        List<Utente> result = new ArrayList();

        try (ResultSet rs = sUtente.executeQuery()) {
            while (rs.next()) {
                result.add((Utente) getUtente(rs.getInt("utenteID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Utente", ex);
        }
        return result;
    }

    @Override
    public List<Utente> getUnassignedUtente() throws DataException {
        List<Utente> result = new ArrayList();

        try (ResultSet rs = sUnassignedUtente.executeQuery()) {
            while (rs.next()) {
                result.add((Utente) getUtente(rs.getInt("utenteID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load unassigned Utente", ex);
        }
        return result;
    }

    @Override
    public void storeUtente(Utente utente) throws DataException {
        try {
            if (utente.getKey() != null && utente.getKey() > 0) { 
                if (utente instanceof DataItemProxy && !((DataItemProxy) utente).isModified()) {
                    return;
                }

            if (utente instanceof DataItemProxy) {
                ((DataItemProxy) utente).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store Utente", ex);
        }
    }


}
