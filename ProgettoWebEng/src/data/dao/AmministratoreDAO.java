package data.dao;

import java.util.List;
import data.DataException;
import data.DataItem;
import data.model.Amministratore;

public interface AmministratoreDAO {
	
    Amministratore createAmministratore();
    Amministratore checkAdmin(String email, String password) throws DataException;
    Amministratore getAmministratore(int id_amministratore) throws DataException;
	static boolean validate(String n, String p) {
		return false;
	}
	
}
