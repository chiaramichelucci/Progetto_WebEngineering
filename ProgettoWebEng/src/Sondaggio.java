
public class Sondaggio {

	private int id;
	private String titolo;
	private boolean disponibile;
	private String modalita;
	private int n_domande;
	private Domanda [] domande;
	
	public Sondaggio(int id, String titolo, boolean disponibile, String modalita, int n_domande, Domanda [] d) {
		setId(id);
		setTitolo(titolo);
		setDisponibile(disponibile);
		setModalita(modalita);
		setN_domande(n_domande);
		Domanda [] domande = new Domanda [20];
		for (int i=0; i<20; i++) {
			domande[i] = d[i];
		}
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

	public void setDomande(Domanda [] domande) {
		this.domande = domande;
	}

	
}
