package data.model;

import java.util.List;

public interface Domanda {
	
	void setCodice(String codice);
	
	String getCodice();
	
	Sondaggio getSondaggio();

    void setSondaggio(Sondaggio sondaggio);

    String getTesto();

    void setTesto(String testo);

    String getNota();

    void setNota(String nota);

    List<Opzione> getOpzioni();

    void setOpzioni(List<Opzione> opzioni);

    void addOpzione(Opzione opzione);
    
    void setObbligatoria(boolean obbligatoria);
    
    boolean getObbligatoria();
    
}
