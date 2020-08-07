//struttura di ogni risposta
public class Risposta {
	private int id;
	private Sondaggio sondaggio;
	private Utente utente;
	private String [] risposte = new Risposta[20];
	
	public Risposta (int id, Sondaggio sondaggio, Utente utente, String [] rd) {
		setId(id);
		setSondaggio(sondaggio);
		setUtente(utente);
		setRisposte(rd);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public String [] getRisposte() {
		return risposte;
	}
	
	public String getRisposta(int index) {
		return risposte[index];
	}

	public void setRisposte(String [] rd) {
		for (int i=0; i<20; i++) {
			this.risposte[i] = rd[i];
		}
	}
	
	public void setRisposta(String rd, int index) {
		this.risposte[index] = rd;
	}	

}
