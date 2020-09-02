package data.impl;

import data.DataItemImpl;
import data.model.Domanda;
import data.model.Opzione;

//definizone delle opzioni di scelta per le domande a risposta multipla

public class OpzioneImpl extends DataItemImpl<Integer> implements Opzione{

	private int id;
	private int id_domanda;
	private Domanda domanda;
	private String testo;
	
	public OpzioneImpl() {
		super();
		testo = "";
		id_domanda = 0;
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
		return id;
	}
	
	
	public int getIDomandaInt() {
		return id_domanda;
	}
	
	
	public void setIDomandaInt(int id) {
		this.id_domanda = id;
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
