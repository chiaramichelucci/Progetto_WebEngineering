package data;

import java.sql.Connection;

import data.model.Domanda;
import data.proxy.DomandaProxy;

public class DAO {
	protected final DataLayer dataLayer;
    protected final Connection connection;

    public DAO(DataLayer d) {
        this.dataLayer = d;
        this.connection = d.getConnection();
    }

    protected DataLayer getDataLayer() {
        return dataLayer;
    }

    protected Connection getConnection() {
        return connection;
    }

    public void init() throws DataException {

    }

    public void destroy() throws DataException {

    }

	public void storeDomanda(Domanda domanda) throws DataException {
		// TODO Auto-generated method stub
		
	}

}
