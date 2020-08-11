package data;
//struttura di ogni domanda

public class Domanda {

	private String codice;
	private String testo;
	private String nota;
	private boolean obbligatoria;
	private Sondaggio sondaggio;
	
	public Domanda (String codice, String testo, String nota, boolean obbligattoria, Sondaggio sondaggio) {
		setCodice(codice);
		setTesto(testo);
		setNota(nota);
		setObbligatoria(obbligatoria);
		setSondaggio(sondaggio);
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public boolean isObbligatoria() {
		return obbligatoria;
	}

	public void setObbligatoria(boolean obbligatoria) {
		this.obbligatoria = obbligatoria;
	}

	public Sondaggio getSondaggio() {
		return sondaggio;
	}

	public void setSondaggio(Sondaggio sondaggio) {
		this.sondaggio = sondaggio;
	}
}
