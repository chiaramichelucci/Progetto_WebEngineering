package data.model;

import data.DataItem;

public interface Sondaggio extends DataItem<Integer>{

	int getID();

	Integer getKey();

	String getTitolo();
	
	void setTitolo(String titolo);

	boolean getDisponibile();
	
	void setDisponibile(boolean disponibile);

	String getModalita();
	
	void setModalita(String modalita);

	String getUrl();

}
