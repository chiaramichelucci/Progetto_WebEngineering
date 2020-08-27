package data.model;

import data.DataItem;

public interface Opzione extends DataItem<Integer>{

	public void setDomanda(Domanda domanda);
	
	public Domanda getDomanda();
	
	public void setTesto(String testo);
	
	public String getTesto();
	
}
