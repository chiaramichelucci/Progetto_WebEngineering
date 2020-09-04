package data.impl;

import data.DataItemImpl;
import data.model.Sondaggio;

public class SondaggioImpl extends DataItemImpl<Integer> implements Sondaggio{

	private int id;
	private String titolo;
	private boolean disponibile;
	private String modalita;
	private String beginText, endText;
	private String url;
	
	public SondaggioImpl() {
		setID(0);
		setTitolo("");
		setDisponibile(false);
		setModalita("");
		setBeginText("");
		setEndText("");
		setUrl("");
	}

	public int getID() {
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

	@Override
	public void setID(int id) {
		this.id = id;
	}

	@Override
	public void setEndText(String beginText) {
		this.beginText = beginText;
		
	}

	@Override
	public void setBeginText(String endText) {
		this.endText = beginText;
		
	}

	@Override
	public String getBeginText() {
		return this.beginText;
	}

	@Override
	public String getEndText() {
		return this.endText;
	}

	
}
