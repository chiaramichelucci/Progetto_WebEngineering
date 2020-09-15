package data.dao;

import java.util.List;
import data.DataException;
import data.model.Sondaggio;
import data.model.Utente;

public interface SondaggioDAO {
	
    Sondaggio createSondaggio();
    Sondaggio getSondaggio(int sondaggio_key) throws DataException;
    List<Sondaggio> getSondaggi() throws DataException;
    void storeSondaggio(Sondaggio sondaggio, int id_utente) throws DataException;
    List<Sondaggio> getSondaggioByResponsabile (String email) throws DataException;
    void invitaUtente (Utente utente, Sondaggio sondaggio) throws DataException;
    void updateSondaggio(Sondaggio sondaggio) throws DataException;
    void deleteSondaggio(Sondaggio sondaggio) throws DataException;
    boolean checkPermesso(Sondaggio sondaggio, Utente utente) throws DataException;
    Sondaggio getSondaggioByTitolo(String titolo) throws DataException;

}