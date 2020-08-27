package data.impl;

import data.DataItemImpl;
import data.model.Domanda;
import data.model.Opzione;

//definizone delle opzioni di scelta per le domande a risposta multipla

public class OpzioneImpl extends DataItemImpl<Integer> implements Opzione{

	private Domanda domanda;
	private String testoOpzione;
	private boolean modified;
	
	public OpzioneImpl() {
		setDomanda(domanda);
		setTesto(testoOpzione);
	}
	
	public Domanda getDomanda() {
		return this.domanda;
	}
	public void setDomanda(Domanda domanda) {
		this.domanda = domanda;
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
