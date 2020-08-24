package data.impl;

import data.DataItemImpl;
import data.model.Domanda;
import data.model.Opzione;

//definizone delle opzioni di scelta per le domande a risposta multipla

public class OpzioneImpl extends DataItemImpl<String> implements Opzione{

	private int domanda;
	private String testoOpzione;
	
	public OpzioneImpl() {
		setID(domanda);
		setTesto(testoOpzione);
	}
	
	public int getID() {
		return this.domanda;
	}
	public void setID(int id_domanda) {
		this.domanda = id_domanda;
	}

	@Override
	public void setTesto(String testo) {
		this.testoOpzione = testo;
	}

	@Override
	public String getTesto() {
		return this.testoOpzione;
	}

}
