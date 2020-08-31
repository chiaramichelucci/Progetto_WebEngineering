package data.dao;

import java.util.List;

import data.DataException;
import data.model.Domanda;
import data.model.Opzione;

public interface OpzioneDAO {
	
	Opzione createOpzione();
	
	//Domanda getDomanda();
	
	void storeOpzione(Opzione opzione, Domanda domanda) throws DataException;

	Opzione getOpzione(int id_domanda) throws DataException;

	List<Opzione> getOpzioni(Domanda domanda) throws DataException;
	
}
