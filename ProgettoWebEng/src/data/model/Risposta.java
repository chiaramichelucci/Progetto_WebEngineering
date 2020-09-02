package data.model;

import data.DataItem;

public interface Risposta extends DataItem<Integer>{

	Domanda getDomanda();
	Utente getUtente();
	Sondaggio getSondaggio();
	String getRisposta();
	int getID();
	void setDomanda(Domanda domanda);
	void setSondaggio(Sondaggio sondaggio);
	void setUtente(Utente utente);
	void setRisposta(String risposta);
	void setID(int id);
	
}
