package data.dao;

import java.util.List;
import data.DataException;
import data.model.Utente;

public interface UtenteDAO {
	
    Utente createUtente();
    Utente getUtente(int utente_key) throws DataException;
    List<Utente> getUtente(Utente utente) throws DataException;
    List<Utente> getUtenti() throws DataException;
    Utente checkUtente(String email, String password) throws DataException;
    void storeUtente(Utente utente) throws DataException;
    void pruomoviUtente(int id) throws DataException;
	static boolean validate(String n, String p) {
		// TODO Auto-generated method stub
		return false;
	}

}
