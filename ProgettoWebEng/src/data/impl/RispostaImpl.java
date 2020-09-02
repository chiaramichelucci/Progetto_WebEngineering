package data.impl;

import data.DataItemImpl;
import data.model.Domanda;
import data.model.Risposta;
import data.model.Sondaggio;
import data.model.Utente;

public class RispostaImpl extends DataItemImpl<Integer> implements Risposta{
	
	int id;
	private Domanda domanda;
	private Sondaggio sondaggio;
	private Utente utente;
	private String risposta;
	
	public RispostaImpl () {
		super();
		domanda = null;
		utente = null;
		sondaggio = null;
		risposta = "";
	}
	
	public Domanda getDomanda() {
		return domanda;
	}

	public void setDomanda(Domanda domanda) {
		this.domanda = domanda;
	}
	
	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}	
	
	public void setRisposta(String rd) {
		this.risposta = rd;
	}
	public String getRisposta() {
		return risposta;
	}
	
	public void setSondaggio(Sondaggio sondaggio) {
		this.sondaggio = sondaggio;
	}
	
	public Sondaggio getSondaggio() {
		return sondaggio;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void setID(int id) {
		this.id = id;
		
	}
}
