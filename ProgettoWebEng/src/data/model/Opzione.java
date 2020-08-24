package data.model;

import data.DataItem;

public interface Opzione extends DataItem<String>{

	public void setID(int id);
	
	public int getID();
	
	public void setTesto(String testo);
	
	public String getTesto();
	
}
