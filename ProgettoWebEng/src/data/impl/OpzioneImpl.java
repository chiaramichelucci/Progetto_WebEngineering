package data.impl;

import data.model.Opzione;

//definizone delle opzioni di scelta per le domande a risposta multipla

public class OpzioneImpl implements Opzione{

	private DomandaImpl domanda;
	private String [] opzioni = new String[8];
	
	public OpzioneImpl(DomandaImpl domanda, String [] opzioni) {
		setDomanda(domanda);
		setOpzioni(opzioni);
	}
	
	public DomandaImpl getDomanda() {
		return domanda;
	}
	public void setDomanda(DomandaImpl domanda) {
		this.domanda = domanda;
	}
	
	public String [] getOpzioni() {
		return opzioni;
	}
	
	public String getOpzione(int index) {
		return opzioni[index];
	}

	public void setOpzioni(String [] o) {
		for (int i=0; i<8; i++) {
			this.opzioni[i] = o[i];
		}
	}
	
	public void setopzione(String o, int index) {
		this.opzioni[index] = o;
	}
		
}
