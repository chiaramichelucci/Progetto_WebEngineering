//struttura di ogni risposta
public class Risposta {
	private int id;
	private int campi;
	private Sondaggio sondaggio;
	private Utente utente;
	
	public Risposta (int id, int campi, Sondaggio sondaggio, Utente utente) {
		setId(id);
		setCampi(campi);
		setSondaggio(sondaggio);
		setUtente(utente);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setCampi(int campi) {
		this.campi = campi;
	}
	
	public int getCampi() {
		return campi;
	}

	public Sondaggio getSondaggio() {
		return sondaggio;
	}

	public void setSondaggio(Sondaggio sondaggio) {
		this.sondaggio = sondaggio;
	}
	
	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}	

}
