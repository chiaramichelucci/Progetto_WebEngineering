package data.dao;

public interface SondaggioDAO {
    Sondaggio createSondaggio();

    Sondaggio getSondaggio(int sondaggio_key) throws DataException;

    List<Sondaggio> getSondaggio(Issue issue) throws DataException;

    List<Sondaggio> getSondaggio() throws DataException;

    List<Sondaggio> getUnassignedSondaggio() throws DataException;

    void storeSondaggio(Sondaggio sondaggio) throws DataException;

}
