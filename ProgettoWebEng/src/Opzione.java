//definizone delle opzioni di scelta per le domande a risposta multipla

public class Opzione {

	private Domanda domanda;
	private String [] opzioni = new String[8];
	
	public Opzione(Domanda domanda, String [] opzioni) {
		setDomanda(domanda);
		setOpzioni(opzioni);
	}
	
	public Domanda getDomanda() {
		return domanda;
	}
	public void setDomanda(Domanda domanda) {
		this.domanda = domanda;
	}
	
	public String [] getOpzioni() {
		return opzioni;
	}
	
	public String getOpzione(int index) {
		return opzioni[index];
	}

	public void setOpzioni(String [] o) {
		for (int i=0; i<8; i++) {
			this.opzioni[i] = o[i];
		}
	}
	
	public void setopzione(String o, int index) {
		this.opzioni[index] = o;
	}
		
}
