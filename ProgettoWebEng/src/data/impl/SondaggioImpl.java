package data.impl;

import data.DataItemImpl;
import data.model.Sondaggio;

public class SondaggioImpl extends DataItemImpl<Integer> implements Sondaggio{

	private int id;
	private String titolo;
	private boolean disponibile;
	private String modalita;
	private String url;
	
	public SondaggioImpl(String titolo, boolean disponibile, String modalita, String url) {
		setTitolo(titolo);
		setDisponibile(disponibile);
		setModalita(modalita);
		setUrl(url);
	}

	public int getId() {
		return id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public boolean getDisponibile() {
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

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	
}
