package data.impl;

import data.DataItemImpl;
import data.model.Domanda;
import data.model.Opzione;

//definizone delle opzioni di scelta per le domande a risposta multipla

public class OpzioneImpl extends DataItemImpl<Integer> implements Opzione{

	private int id;
	private Domanda domanda;
	private String testo;
	private boolean modified;
	
	public OpzioneImpl() {
		super();
		testo = "";
		domanda = null;
	}
	
	public Domanda getDomanda() {
		return this.domanda;
	}
	public void setDomanda(Domanda domanda) {
		this.domanda = domanda;
	}

	@Override
	public void setTesto(String testo) {
		this.testo = testo;
	}

	@Override
	public String getTesto() {
		return this.testo;
	}

	@Override
	public int getIDomanda() {
		int id = this.domanda.getID();
		return id;
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public void setID(int id) {
		this.id = id;
		
	}

	@Override
	public void setIDomanda(int id) {
		this.id = id;
		
	}

}
