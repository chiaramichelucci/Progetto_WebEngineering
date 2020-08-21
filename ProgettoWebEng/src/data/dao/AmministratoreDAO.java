package data.dao;

import java.util.List;
import data.DataException;
import data.DataItem;
import data.model.Amministratore;

public interface AmministratoreDAO {
	
    Amministratore createAmministratore();
    Amministratore getAmministratore(int id_amministratore) throws DataException;
    List<Amministratore> getAmministratore(Amministratore amministratore) throws DataException;
    List<Amministratore> getAmministratore() throws DataException;
    void storeAmministratore(Amministratore amministratore) throws DataException;
    void save(Amministratore amministratore);	
    void update(Amministratore amministratore); 	
    void delete(Amministratore amministratore);
	static boolean validate(String n, String p) {
		// TODO Auto-generated method stub
		return false;
	}
}
