package data.model;

import data.DataItem;

public interface Domanda extends DataItem<Integer>{
	
	void setID(int id);
	
	int getID();
	
	Sondaggio getSondaggio();

    void setSondaggio(Sondaggio sondaggio);

    String getTesto();

    void setTesto(String testo);

    String getNota();

    void setNota(String nota);
    
    void setObbligatoria(boolean obbligatoria);
    
    boolean getObbligatoria();

	String getTipo();

	void setTipo(String tipo);

	void setDomanda(String string);
    
}
