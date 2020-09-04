package data.dao;

import java.util.List;
import data.DataException;
import data.model.Sondaggio;

public interface SondaggioDAO {
	
    Sondaggio createSondaggio();
    Sondaggio getSondaggio(int sondaggio_key) throws DataException;
    List<Sondaggio> getSondaggio(Sondaggio sondaggio) throws DataException;
    void storeSondaggio(Sondaggio sondaggio) throws DataException;
    List<Sondaggio> getSondaggioByResponsabile (String email) throws DataException;
    void updateSondaggio(Sondaggio sondaggio) throws DataException;
    void deleteSondaggio(Sondaggio sondaggio) throws DataException;

}