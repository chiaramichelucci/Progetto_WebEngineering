package data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.DAO;
import data.DataException;
import data.DataLayer;
import data.OptimisticLockException;
import data.model.Domanda;
import data.model.Sondaggio;
import data.proxy.DomandaProxy;
import data.DataItemProxy;

public class DomandaDAO_MySQL extends DAO implements DomandaDAO {
	
	private PreparedStatement domandeBySondaggio;
	private PreparedStatement domandaById;
    private PreparedStatement idDomanda, cDomandaBySondaggio;
    private PreparedStatement iDomanda, uDomanda, dDomanda;
    
    public DomandaDAO_MySQL (DataLayer d) {
    	super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();

            domandaById = connection.prepareStatement("SELECT * FROM domanda WHERE id=?");
            cDomandaBySondaggio = connection.prepareStatement("SELECT * FROM domanda WHERE sondaggio=?");
            idDomanda = connection.prepareStatement("SELECT id AS idDomanda FROM domanda");
            domandeBySondaggio = connection.prepareStatement("SELECT * FROM domanda WHERE id_sondaggio=?");

            iDomanda = connection.prepareStatement("INSERT INTO domanda (testo,nota,tipo,obbligatoria,id_sondaggio) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uDomanda = connection.prepareStatement("UPDATE domanda SET testo=?,nota=?,tipo=?,obbligatoria=? WHERE id=?");
            dDomanda = connection.prepareStatement("DELETE FROM domanda WHERE id=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }
    
    @Override
    public void storeDomanda(Domanda domanda) throws DataException {
        try {
            if (domanda.getKey() != null && domanda.getID() > 0) { 
                if (domanda instanceof DataItemProxy && !((DataItemProxy) domanda).isModified()) {
                    return;
                }
                uDomanda.setString(1, domanda.getTesto());
                uDomanda.setString(2, domanda.getNota());
                if (domanda.getKey() != null) {
                    uDomanda.setBoolean(3, domanda.getObbligatoria());
                } else {
                    uDomanda.setNull(3, java.sql.Types.INTEGER);
                }
                if (domanda.getKey() != null) {
                    uDomanda.setInt(4, domanda.getID());
                } else {
                    uDomanda.setNull(4, java.sql.Types.INTEGER);
                    uDomanda.setNull(5, java.sql.Types.INTEGER);
                }

                long current_version = domanda.getVersion();
                long next_version = current_version + 1;

                if (uDomanda.executeUpdate() == 0) {
                    throw new OptimisticLockException(domanda);
                }
                domanda.setVersion(next_version);
            } else { 
                iDomanda.setString(1, domanda.getTesto());
                iDomanda.setString(2, domanda.getNota());
                if (domanda.getKey() != null) {
                    iDomanda.setBoolean(3, domanda.getObbligatoria());
                } else {
                    iDomanda.setNull(3, java.sql.Types.INTEGER);
                }
                if (domanda.getKey() != null) {
                    iDomanda.setInt(4, domanda.getKey());
                    iDomanda.setString(5, domanda.getTesto());
                } else {
                    iDomanda.setNull(4, java.sql.Types.INTEGER);
                    iDomanda.setNull(5, java.sql.Types.INTEGER);
                }
                if (iDomanda.executeUpdate() == 1) {
                    try (ResultSet keys = iDomanda.getGeneratedKeys()) {
                        if (keys.next()) {
                           
                            int key = keys.getInt(1);
                           
                            domanda.setKey(key);
                         
                            dataLayer.getCache().add(Domanda.class, domanda);
                        }
                    }
                }
            }
            if (domanda instanceof DataItemProxy) {
                ((DataItemProxy) domanda).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store domanda", ex);
        }}
    
    @Override
    public void destroy() throws DataException {
    	
        try {

            domandaById.close();

            cDomandaBySondaggio.close();
            idDomanda.close();

            iDomanda.close();
            uDomanda.close();
            dDomanda.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
    @Override
    public DomandaProxy createDomanda() {
        return new DomandaProxy(getDataLayer());
    }

    private DomandaProxy createDomanda(ResultSet rs) throws DataException {
        DomandaProxy a = createDomanda();
        try {
            a.setID(rs.getInt("id"));
            a.setTesto(rs.getString("testo"));
            a.setNota(rs.getString("nota"));
            a.setTipo(rs.getString("tipo"));
            a.setObbligatoria(rs.getBoolean("obbligatoria"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create article object form ResultSet", ex);
        }
        return a;
    }
    
    @Override
    public Domanda getDomanda(int id) throws DataException {
        Domanda a = null;
        if (dataLayer.getCache().has(Domanda.class, id)) {
            a = dataLayer.getCache().get(Domanda.class, id);
        } else {
            try {
                domandaById.setInt(1, id);
                try (ResultSet rs = domandaById.executeQuery()) {
                    if (rs.next()) {
                        a = createDomanda(rs);
                        dataLayer.getCache().add(Domanda.class, a);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Non e' stato possibile carica la domanda in base al ID", ex);
            }
        }
        return a;
    }
    
    @Override
    public List<Domanda> getDomande(Sondaggio sondaggio) throws DataException {
        List<Domanda> result = new ArrayList();
        try {
            domandeBySondaggio.setInt(1, sondaggio.getID());            
            try (ResultSet rs = domandeBySondaggio.executeQuery()) {
                while (rs.next()) {
                    result.add((Domanda) getDomanda(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Non e' stato possibile carica le domande in base al ID", ex);
        }
        return result;
    }
    
    @Override
	public List<Domanda> getDomandeById(int id_sondaggio) throws DataException {
        List<Domanda> result = new ArrayList();
        try {
            domandeBySondaggio.setInt(1, id_sondaggio);            
            try (ResultSet rs = domandeBySondaggio.executeQuery()) {
                while (rs.next()) {
                    result.add((Domanda) getDomanda(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Non e' stato possibile carica le domande in base al ID", ex);
        }
        return result;
	}
    
    public void delete(Domanda domanda) {
    	
    }

	@Override
	public List<Domanda> getDomande() throws DataException {
		List<Domanda> result = new ArrayList();

        try {
            Sondaggio sondaggio = null;
			domandeBySondaggio.setInt(1, sondaggio.getID());            
            try (ResultSet rs = domandeBySondaggio.executeQuery()) {
                while (rs.next()) {
                    result.add((Domanda) getDomanda(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load articles by issue", ex);
        }
        return result;
	}

	@Override
	public void storeDomanda(Domanda domanda, Sondaggio sondaggio) throws DataException {
		try {
			if(domanda.getKey() != null && domanda.getID() > 0) {
				if(domanda instanceof DataItemProxy && ! ((DataItemProxy) domanda).isModified()) {
					return;
				}
				
			} else { 
				iDomanda.setString(1, domanda.getTesto());
				iDomanda.setString(2, domanda.getNota());
				iDomanda.setString(3, domanda.getTipo());
				iDomanda.setBoolean(4, domanda.getObbligatoria());
				iDomanda.setInt(5, sondaggio.getID());
				if (iDomanda.executeUpdate() == 1) {
					try (ResultSet keys = iDomanda.getGeneratedKeys()) {
						if (keys.next()) {
							int key = keys.getInt(1);
							domanda.setKey(key);
							domanda.setID(key);
							dataLayer.getCache().add(Domanda.class, domanda);
						}
					}
				}
			}
		} catch (SQLException ex) {
			throw new DataException("Non e possibilie inserire la domanda", ex);
		}
		
	}

	@Override
	public Sondaggio getSondagio(int idSondaggio) throws DataException {
		return null;
		
	}

	@Override
	public void updateDomanda(Domanda domanda) throws DataException{
		try {
			uDomanda.setString(1, domanda.getTesto());
			uDomanda.setString(2, domanda.getNota());
			uDomanda.setString(3, domanda.getTipo());
			uDomanda.setBoolean(4, domanda.getObbligatoria());
			uDomanda.setInt(5, domanda.getID());
			uDomanda.executeUpdate();
		}catch(SQLException ex) {
			throw new DataException("Non e possibile aggiornare la domanda", ex);
		}
		
	}

	@Override
	public void deleteDomanda(Domanda domanda) throws DataException {
		try {
			dDomanda.setInt(1, domanda.getID());
			dDomanda.executeQuery();
		}catch(SQLException ex) {
			throw new DataException("Non e possibile cancelare la domanda", ex);
		}
	}
    
    
}
