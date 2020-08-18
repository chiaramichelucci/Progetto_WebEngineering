package data.dao;

public interface UtenteDAO {
    Utente createUtente();

    Utente getUtente(int utente_key) throws DataException;

    List<Utente> getUtente(Issue issue) throws DataException;

    List<Utente> getUtente() throws DataException;

    List<Utente> getUnassignedUtente() throws DataException;

    void storeUtente(Utente utente) throws DataException;

}
