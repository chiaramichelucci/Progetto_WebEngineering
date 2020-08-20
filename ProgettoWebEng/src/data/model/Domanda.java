package data.model;

public interface Domanda {
	
	void setCodice(String codice);
	
	String getCodice();
	
	Sondaggio getSondaggio();

    void setSondaggio(Sondaggio sondaggio);

    String getTesto();

    void setTesto(String testo);

    String getNota();

    void setNota(String nota);
    
    void setObbligatoria(boolean obbligatoria);
    
    boolean getObbligatoria();
    
}
