package data.dao;

import data.model.Domanda;
import data.model.Sondaggio;

import java.util.List;

import data.DataException;

public interface DomandaDAO {
	
	Domanda creaDomanda();
	Domanda getDomanda(String domanda_key) throws DataException;
	List<Domanda> getDomande(Sondaggio sondaggio) throws DataException;
	List<Domanda> getDomande() throws DataException;
	void storeDomanda(Domanda domanda, Sondaggio sondaggio) throws DataException;
	//void update(Domanda ddomanda); 	
	void delete(Domanda domanda);
	Sondaggio getSondagio(int idSondaggio) throws DataException;
}
