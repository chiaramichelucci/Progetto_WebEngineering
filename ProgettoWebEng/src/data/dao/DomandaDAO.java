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
	void storeDomanda(Domanda domanda) throws DataException;
	void save(Domanda domanda);	
	void update(Domanda ddomanda); 	
	void delete(Domanda domanda);
}
