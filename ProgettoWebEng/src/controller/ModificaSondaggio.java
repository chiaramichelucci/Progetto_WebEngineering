package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataException;
import data.dao.SondaggioDataLayer;
import data.model.Domanda;
import data.model.Opzione;
import data.model.Sondaggio;
import template.Failure;
import template.TemplateManagerExeption;
import template.TemplateResult;

public class ModificaSondaggio extends SondaggioBaseController {

	private void action_error(HttpServletRequest req, HttpServletResponse res) {
		if (req.getAttribute("exception") != null) {
            (new Failure(getServletContext())).activate((Exception) req.getAttribute("exception"), req, res);
        } else {
            (new Failure(getServletContext())).activate((String) req.getAttribute("message"), req, res);
        }	
	}
 
 public void visualizzaSondaggio (HttpServletRequest req, HttpServletResponse res) throws IOException, DataException, TemplateManagerExeption {
		
		TemplateResult resp = new TemplateResult(getServletContext()); 

		Sondaggio sondaggio = (Sondaggio) req.getAttribute("sondaggioDaModificare");
		req.setAttribute("poll", sondaggio);
		List<Domanda> domande = (((SondaggioDataLayer)req.getAttribute("datalayer")).getDomandaDAO().getDomande(sondaggio));
		req.setAttribute("domande", domande);
		req.setAttribute("add_multi", false);
		req.setAttribute("use_outline", true);
		System.out.print(" beginText: " + sondaggio.getBeginText());
		System.out.print(" endText: " + sondaggio.getEndText());
		resp.activate("modifica.ftl.html", req, res);
		for(int i = 0; i<domande.size(); i++) {
			Domanda domanda = domande.get(i);
			req.setAttribute("domanda", domanda);
			String tipo = domanda.getTipo();
			if (tipo.equalsIgnoreCase("radio") || tipo.equalsIgnoreCase("checkbox")) {
				List<Opzione> opzioni = (((SondaggioDataLayer)req.getAttribute("datalayer")).getOpzioneDAO().getOpzioni(domanda));
				req.setAttribute("options", opzioni);
				req.setAttribute("useMulti", "add_multi_mod");
				req.setAttribute("use_outline", true);
				resp.activate("addMultiMod.ftl.html", req, res);
			} else {
				req.setAttribute("useMulti", "add_multi_mod");
				req.setAttribute("use_outline", true);
				resp.activate("addMultiMod.ftl.html", req, res);
			}
		}
	}
 
 public void modificaSondaggio (HttpServletRequest req, HttpServletResponse res) throws DataException {
	 
	 //da vedere i nomi dei parametri nei view
	 String [] domandeTesti = req.getParameterValues("domandaTesto");
	 String [] obbDomande = req.getParameterValues("domandaObb");
	 String [] noteDomande = req.getParameterValues("domandaNota");
	 
	 if(req.getParameter("sondaggioText") != null && req.getParameter("modalitaSondaggio") != null && req.getParameter("dispSondaggio") != null) {
		 //Sondaggio sondaggio = (((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().createSondaggio());
		 Sondaggio sondaggioDummy = (Sondaggio) req.getAttribute("sondaggioDaModificare");
		 //sondaggio.setID(sondaggioDummy.getID());
		 sondaggioDummy.setTitolo(req.getParameter("sondaggioText"));
		 sondaggioDummy.setBeginText(req.getParameter("sondaggioBegin"));
		 sondaggioDummy.setEndText(req.getParameter("sondaggioEnd"));
		 sondaggioDummy.setModalita(req.getParameter("modalitaSondaggio"));
		 if(req.getParameter("dispSondaggio").equalsIgnoreCase("chiuso")) {
			 sondaggioDummy.setDisponibile(false);
		 } else {
			 sondaggioDummy.setDisponibile(true);
		 }
		 ((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().updateSondaggio(sondaggioDummy);
		 List<Domanda> domande = new ArrayList<Domanda>();
		 domande.addAll((Collection<? extends Domanda>) req.getAttribute("domande"));
		 for(int i=0; i<domandeTesti.length; i++) {
			 Domanda domandaDummy = domande.get(i);
			 domandaDummy.setTesto(domandeTesti[i]);
			 domandaDummy.setNota(noteDomande[i]);
			 if(obbDomande[i].equalsIgnoreCase("aperta") ) {
				 domandaDummy.setObbligatoria(false);
			 } else {
			 	domandaDummy.setObbligatoria(true);
			 }
			 ((SondaggioDataLayer)req.getAttribute("datalayer")).getDomandaDAO().updateDomanda(domandaDummy);
			 if (domandaDummy.getTipo().equalsIgnoreCase("radio") || domandaDummy.getTipo().equalsIgnoreCase("checkbox")) {
				 String [] opzioniTesti = req.getParameterValues("opzioni");  //da vedere il name dei view
				 List<Opzione> opzioni = new ArrayList<Opzione>();
				 opzioni =  ((SondaggioDataLayer)req.getAttribute("datalayer")).getOpzioneDAO().getOpzioni(domandaDummy);
				 for (int j=0; j<opzioni.size(); j++) {
					 Opzione opzioneDummy = opzioni.get(j);
					 opzioneDummy.setTesto(opzioniTesti[j]);
					 if(opzioneDummy.getTesto() == null || opzioneDummy.getTesto().equalsIgnoreCase("")) {
						 ((SondaggioDataLayer)req.getAttribute("datalayer")).getOpzioneDAO().deleteOpzione(opzioneDummy);
					 } else {
						 ((SondaggioDataLayer)req.getAttribute("datalayer")).getOpzioneDAO().updateOpzione(opzioneDummy);
					 }
				 }
			 }
			 if(domandaDummy.getTesto() == null || domandaDummy.getTesto().equalsIgnoreCase("")) {
				 ((SondaggioDataLayer)req.getAttribute("datalayer")).getDomandaDAO().deleteDomanda(domandaDummy);
			 }
		 }
		 if(sondaggioDummy.getTitolo() == null || sondaggioDummy.getTitolo().equalsIgnoreCase("")) {
			 ((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().deleteSondaggio(sondaggioDummy);
		 }
	 }
 }
 

	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException {
		try {
			if(req.getParameter("updateSubmit") != null) {
				modificaSondaggio(req, res);
			} else {
				visualizzaSondaggio(req, res);
			}
		}catch(TemplateManagerExeption ex) {
			req.setAttribute("exception", ex);
            action_error(req, res);
		}catch(IOException ex) {
			req.setAttribute("exception", ex);
            action_error(req, res);
		}catch(DataException ex) {
			req.setAttribute("exception", ex);
			action_error(req, res);
		}
	}

public String getServletInfo() {
return "Servlet per la modifica dei sondaggi";
}

}
