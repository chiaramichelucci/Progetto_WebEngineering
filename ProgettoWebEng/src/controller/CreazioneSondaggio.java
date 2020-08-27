package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataException;
import data.dao.SondaggioDataLayer;
import data.model.Domanda;
import data.model.Opzione;
import data.model.Sondaggio;


public class CreazioneSondaggio extends SondaggioBaseController {

	private void createSondaggio(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, DataException {
	
		String [] domande = req.getParameterValues("domanda");
		String [] tipiDomande = req.getParameterValues("tipo");
		String [] noteDomande = req.getParameterValues("nota");
		
		
		//campo obbligatoria da vedere non lo inserice bene
		
		Sondaggio sondaggio = ((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().createSondaggio();
		if (sondaggio != null && req.getParameter("titoloS") != null && req.getParameter("modalitaS") != null) {
			 sondaggio.setTitolo(req.getParameter("titoloS"));
			 sondaggio.setModalita(req.getParameter("modalitaS"));
			 ((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().storeSondaggio(sondaggio);
			 for (int i = 0; i<domande.length; i++) {
				 Domanda domanda = ((SondaggioDataLayer)req.getAttribute("datalayer")).getDomandaDAO().createDomanda();
				 if(domanda != null && domande[i] != null && tipiDomande[i] != null && noteDomande[i] != null) {
					 domanda.setTesto(domande[i]);
					 domanda.setTipo(tipiDomande[i]);
					 domanda.setNota(noteDomande[i]);
						 String obbligatorie = req.getParameter("domanda-"+i+"obb");
						 if(obbligatorie == "Obbligatoria") {
							 domanda.setObbligatoria(true);
						 } else {
							 domanda.setObbligatoria(false);
						 }
					 ((SondaggioDataLayer)req.getAttribute("datalayer")).getDomandaDAO().storeDomanda(domanda, sondaggio);
				 } 
				 if(tipiDomande[i].equalsIgnoreCase("radio") || tipiDomande[i].equalsIgnoreCase("checkbox")) {
					 	System.out.print(" dentro la if delle opzioni ");
					 	i=i+1;
					 	System.out.print(i);
					 	String [] opzioni = req.getParameterValues(i+"opzione");
					 	for(int j = 0; j<opzioni.length; j++) {
					 		Opzione opzione = ((SondaggioDataLayer)req.getAttribute("datalayer")).getOpzioneDAO().createOpzione();
					 		if(opzioni[j] != null) {
					 			opzione.setTesto(opzioni[j]);
					 			((SondaggioDataLayer)req.getAttribute("datalayer")).getOpzioneDAO().storeOpzione(opzione, domanda);
					 		}
					 	}
				   }
			 }
		 }
	}

	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException {
		
		try {
			createSondaggio(req, res);
		}catch(IOException ex) {
			req.setAttribute("exception", ex);
            //action_error(, res);
		}
	}
	
	public String getServletInfo() {
        return "Servlet per la creazione dei sondaggi";
	}
	
}
	
