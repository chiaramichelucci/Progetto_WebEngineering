package data.dao;

import java.util.List;
import data.DataException;
import data.model.Sondaggio;

public interface SondaggioDAO {
	
    Sondaggio createSondaggio();
    Sondaggio getSondaggio(int sondaggio_key) throws DataException;
    List<Sondaggio> getSondaggio(Sondaggio sondaggio) throws DataException;
    List<Sondaggio> getSondaggio() throws DataException;
    void storeSondaggio(Sondaggio sondaggio) throws DataException;
    void save(Sondaggio sondaggio);	
    void update(Sondaggio sondaggio); 	
    void delete(Sondaggio sondaggio);

}