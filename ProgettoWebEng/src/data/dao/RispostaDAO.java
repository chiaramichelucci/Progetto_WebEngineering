package data.dao;

import data.DataException;
import data.model.Domanda;
import data.model.Opzione;
import data.model.Risposta;
import data.model.Sondaggio;

public interface RispostaDAO {

	Risposta creaRisposta();

	void storeRisposta(Risposta risposta) throws DataException;

}
