package data.impl;

import data.DataItemImpl;
import data.model.Domanda;
import data.model.Sondaggio;


public class DomandaImpl extends DataItemImpl<Integer> implements Domanda{

	private int id;
	private String testo;
	private String nota;
	private String tipo;
	private boolean obbligatoria;
	private Sondaggio sondaggio;
	
	public DomandaImpl () {
		super();
		setTesto("");
		setNota("");
		setObbligatoria(false);
		setSondaggio(null);
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public Sondaggio getSondaggio() {
		return sondaggio;
	}

	public void setSondaggio(Sondaggio sondaggio) {
		this.sondaggio = sondaggio;
	}

	@Override
	public void setObbligatoria(boolean obbligatoria) {
		this.obbligatoria = obbligatoria;
	}

	@Override
	public boolean getObbligatoria() {
		return obbligatoria;
	}

	@Override
	public String getTipo() {
		return this.tipo;
	}

}
