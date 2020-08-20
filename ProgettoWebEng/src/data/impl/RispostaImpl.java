package data.impl;

import data.DataItemImpl;
import data.model.Domanda;
import data.model.Risposta;
import data.model.Utente;

public class RispostaImpl extends DataItemImpl<String> implements Risposta{
	
	private Domanda domanda;
	private Utente utente;
	private String risposta;
	
	public RispostaImpl () {
		super();
		domanda = null;
		utente = null;
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

}
