package data.impl;

import data.DataItemImpl;
import data.model.Sondaggio;

public class SondaggioImpl extends DataItemImpl<Integer> implements Sondaggio{

	protected static int id;
	protected static String titolo;
	protected static boolean disponibile;
	protected static String modalita;
	protected static int n_domande;
	protected static DomandaImpl [] domande = new DomandaImpl[20];
	
	public SondaggioImpl(int id, String titolo, boolean disponibile, String modalita, int n_domande, DomandaImpl [] d) {
		setId(id);
		setTitolo(titolo);
		setDisponibile(disponibile);
		setModalita(modalita);
		setN_domande(n_domande);
		setDomande(d);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public boolean isDisponibile() {
		return disponibile;
	}

	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}

	public String getModalita() {
		return modalita;
	}

	public void setModalita(String modalita) {
		this.modalita = modalita;
	}

	public int getN_domande() {
		return n_domande;
	}

	public void setN_domande(int n_domande) {
		this.n_domande = n_domande;
	}

	public DomandaImpl [] getDomande() {
		return domande;
	}
	
	public DomandaImpl getDomanda(int index) {
		return domande[index];
	}

	public void setDomande(DomandaImpl [] d) {
		for (int i=0; i<20; i++) {
			this.domande[i] = d[i];
		}
	}
	
	public void setDomanda(DomandaImpl d, int index) {
		this.domande[index] = d;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer getKey() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
