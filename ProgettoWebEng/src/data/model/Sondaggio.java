package data.model;

import data.DataItem;

public interface Sondaggio extends DataItem<Integer>{

	int getID();

	Integer getKey();

	String getTitolo();

	boolean getDisponibile();

	String getModalita();

	String getUrl();

}
