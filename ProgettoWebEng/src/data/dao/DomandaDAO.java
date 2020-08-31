package data.dao;

import data.model.Domanda;
import data.model.Sondaggio;

import java.util.List;

import data.DataException;

public interface DomandaDAO {
	
	Domanda createDomanda();
	Domanda getDomanda(int id_domanda) throws DataException;
	List<Domanda> getDomande(Sondaggio sondaggio) throws DataException;
	List<Domanda> getDomande() throws DataException;
	List<Domanda> getDomandeById(int id_sondaggio) throws DataException;
	void storeDomanda(Domanda domanda, Sondaggio sondaggio) throws DataException;
	//void update(Domanda ddomanda); 	
	void delete(Domanda domanda);
	Sondaggio getSondagio(int idSondaggio) throws DataException;
}
