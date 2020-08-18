package data.dao;

public interface AmministratoreDAO {
    Amministratore createAmministratore();

    Amministratore getAmministratore(int amministratore_key) throws DataException;

    List<Amministratore> getAmministratore(Issue issue) throws DataException;

    List<Amministratore> getAmministratore() throws DataException;

    List<Amministratore> getUnassignedAmministratore() throws DataException;

    void storeAmministratore(Amministratore amministratore) throws DataException;

}
