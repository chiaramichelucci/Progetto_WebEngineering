package data.impl;

import data.DataItemImpl;
import data.model.Domanda;
import data.model.Opzione;

//definizone delle opzioni di scelta per le domande a risposta multipla

public class OpzioneImpl extends DataItemImpl<String> implements Opzione{

	private String domanda;
	private String testoOpzione;
	
	public OpzioneImpl() {
		setDomanda(domanda);
		setOpzione(testoOpzione);
	}
	
	public String getDomanda() {
		return domanda;
	}
	public void setDomanda(String stringa) {
		this.domanda = stringa;
	}
	
	public String getOpzione() {
		return this.testoOpzione;
	}

	public void setOpzione(String o) {
			this.testoOpzione= o;
	}
		
}
