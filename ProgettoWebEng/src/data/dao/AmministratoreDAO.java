package data.dao;

import java.util.List;
import data.DataException;
import data.model.Amministratore;

public interface AmministratoreDAO {
	
    Amministratore createAmministratore();
    Amministratore getAmministratore(int amministratore_key) throws DataException;
    List<Amministratore> getAmministratore(Amministratore amministratore) throws DataException;
    List<Amministratore> getAmministratore() throws DataException;
    void storeAmministratore(Amministratore amministratore) throws DataException;
    void save(Amministratore amministratore);	
    void update(Amministratore amministratore); 	
    void delete(Amministratore amministratore);
}
