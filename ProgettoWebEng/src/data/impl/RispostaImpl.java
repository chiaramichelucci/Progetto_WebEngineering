package data.impl;

import data.model.Risposta;

//struttura di ogni risposta
public class RispostaImpl implements Risposta{
	private int id;
	private SondaggioImpl sondaggio;
	private UtenteImpl utente;
	private RispostaImpl[] risposte = new RispostaImpl[20];
	
	public RispostaImpl (int id, SondaggioImpl sondaggio, UtenteImpl utente, String [] rd) {
		setId(id);
		setSondaggio(sondaggio);
		setUtente(utente);
		setRisposte(rd);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SondaggioImpl getSondaggio() {
		return sondaggio;
	}

	public void setSondaggio(SondaggioImpl sondaggio) {
		this.sondaggio = sondaggio;
	}
	
	public UtenteImpl getUtente() {
		return utente;
	}

	public void setUtente(UtenteImpl utente) {
		this.utente = utente;
	}	
	
	public RispostaImpl[] getRisposte() {
		return risposte;
	}
	
	public RispostaImpl getRisposta(int index) {
		return risposte[index];
	}

	public void setRisposte(String[] rd) {
		for (int i=0; i<20; i++) {
			this.risposte[i] = rd[i];
		}
	}
	
	public void setRisposta(RispostaImpl rd, int index) {
		this.risposte[index] = rd;
	}	

}
