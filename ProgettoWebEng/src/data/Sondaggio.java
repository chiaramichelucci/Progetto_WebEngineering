package data;

public class Sondaggio {

	private int id;
	private String titolo;
	private boolean disponibile;
	private String modalita;
	private int n_domande;
	private Domanda [] domande = new Domanda[20];
	
	public Sondaggio(int id, String titolo, boolean disponibile, String modalita, int n_domande, Domanda [] d) {
		setId(id);
		setTitolo(titolo);
		setDisponibile(disponibile);
		setModalita(modalita);
		setN_domande(n_domande);
		setDomande(d);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public boolean isDisponibile() {
		return disponibile;
	}

	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}

	public String getModalita() {
		return modalita;
	}

	public void setModalita(String modalita) {
		this.modalita = modalita;
	}

	public int getN_domande() {
		return n_domande;
	}

	public void setN_domande(int n_domande) {
		this.n_domande = n_domande;
	}

	public Domanda [] getDomande() {
		return domande;
	}
	
	public Domanda getDomanda(int index) {
		return domande[index];
	}

	public void setDomande(Domanda [] d) {
		for (int i=0; i<20; i++) {
			this.domande[i] = d[i];
		}
	}
	
	public void setDomanda(Domanda d, int index) {
		this.domande[index] = d;
	}

	
}
