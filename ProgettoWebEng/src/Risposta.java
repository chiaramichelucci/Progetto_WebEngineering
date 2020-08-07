//struttura di ogni risposta
public class Risposta {
	private int id;
	private Sondaggio sondaggio;
	private Utente utente;
	private Risposta [] risposte = new Risposta[20];
	
	public Risposta (int id, Sondaggio sondaggio, Utente utente, Risposta [] rd) {
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
	
	public Risposta [] getRisposte() {
		return risposte;
	}
	
	public Risposta getRisposta(int index) {
		return risposte[index];
	}

	public void setRisposte(Risposta [] rd) {
		for (int i=0; i<20; i++) {
			this.risposte[i] = rd[i];
		}
	}
	
	public void setRisposta(Risposta rd, int index) {
		this.risposte[index] = rd;
	}	

}
