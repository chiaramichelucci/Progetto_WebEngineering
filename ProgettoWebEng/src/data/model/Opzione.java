package data.model;

import data.DataItem;

public interface Opzione extends DataItem<Integer>{

	public void setDomanda(Domanda domanda);
	
	public Domanda getDomanda();
	
	public void setTesto(String testo);
	
	public String getTesto();
	
	public int getIDomanda();
	
	public void setIDomanda(int id);
	
	public int getID();

	public void setID(int id);

	int getIDomandaInt();

	void setIDomandaInt(int id);
	
}
