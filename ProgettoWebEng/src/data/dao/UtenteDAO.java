package data.dao;

import java.util.List;
import data.DataException;
import data.model.Utente;

public interface UtenteDAO {
	
    Utente createUtente();
    Utente getUtente(int utente_key) throws DataException;
    List<Utente> getUtente(Utente utente) throws DataException;
    List<Utente> getUtente() throws DataException;
    List<Utente> getUnassignedUtente() throws DataException;
    void storeUtente(Utente utente) throws DataException;
    void save(Utente utente);	
    void update(Utente utente); 	
    void delete(Utente utente);

}
