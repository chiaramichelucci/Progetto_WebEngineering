package data.model;

import data.DataItem;

public interface Sondaggio extends DataItem<Integer>{

	int getID();
	
	void setID(int id);

	Integer getKey();

	String getTitolo();
	
	void setTitolo(String titolo);

	boolean getDisponibile();
	
	void setDisponibile(boolean disponibile);

	String getModalita();
	
	void setModalita(String modalita);

	String getUrl();
	
	void setUrl(String url);

	void setEndText(String parameter);

	void setBeginText(String parameter);
	
	String getBeginText();
	
	String getEndText();

}
