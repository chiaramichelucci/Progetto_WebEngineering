package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataException;
import data.dao.SondaggioDataLayer;
import data.impl.RispostaImpl;
import data.model.Domanda;
import data.model.Opzione;
import data.model.Risposta;
import data.model.Sondaggio;
import template.Failure;
import template.TemplateManagerExeption;
import template.TemplateResult;

//fatto non provatto

public class CompilazioneSondaggio extends SondaggioBaseController {

	private void action_error(HttpServletRequest req, HttpServletResponse res) {
		if (req.getAttribute("exception") != null) {
            (new Failure(getServletContext())).activate((Exception) req.getAttribute("exception"), req, res);
        } else {
            (new Failure(getServletContext())).activate((String) req.getAttribute("message"), req, res);
        }	
	}
	
	private void stampaSondaggio (HttpServletRequest req, HttpServletResponse res) throws IOException, DataException, TemplateManagerExeption {
		
		TemplateResult resp = new TemplateResult(getServletContext()); 
		
		int id = Integer.parseInt(req.getParameter("id"));
		
		Sondaggio sondaggio = ((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().getSondaggio(id);
		req.setAttribute("poll", sondaggio);
		List<Domanda> domande = (((SondaggioDataLayer)req.getAttribute("datalayer")).getDomandaDAO().getDomande(sondaggio));
		req.setAttribute("add_multi", false);
		req.setAttribute("use_outline", true);
		resp.activate("comp.ftl.html", req, res);
		for(int i = 0; i<domande.size(); i++) {
			Domanda domanda = domande.get(i);
			String tipo = domanda.getTipo();
			if (tipo.equals("Radio")) {
				List<Opzione> opzioni = (((SondaggioDataLayer)req.getAttribute("datalayer")).getOpzioneDAO().getOpzioni(domanda));
				req.setAttribute("options", opzioni);
				req.setAttribute("indiceDomanda", i);
				req.setAttribute("domanda", domanda);
				req.setAttribute("add_multi", true);
				req.setAttribute("use_outline", false);
				resp.activate("addMulti.ftl.html", req, res);
			} else if (tipo.equals("Checkbox")) {
				List<Opzione> opzioni = (((SondaggioDataLayer)req.getAttribute("datalayer")).getOpzioneDAO().getOpzioni(domanda));
				req.setAttribute("options", opzioni);
				req.setAttribute("indiceDomanda", i);
				req.setAttribute("domanda", domanda);
				req.setAttribute("add_multi", true);
				req.setAttribute("use_outline", false);
				resp.activate("addMulti.ftl.html", req, res);
			} else {
				req.setAttribute("indiceDomanda", i);
				req.setAttribute("domanda", domanda);
				req.setAttribute("add_multi", false);
				req.setAttribute("use_outline", false);
				resp.activate("addMulti.ftl.html", req, res);
			}
		}
		if(req.getParameter("submit") != null) {
			compilazioneSondaggio(req, res, sondaggio, domande);
		}
		
	}
	
	
	private void compilazioneSondaggio(HttpServletRequest req, HttpServletResponse res, Sondaggio sondaggio, List<Domanda> domande) throws DataException { 
		List<String> risposte = new ArrayList<String>();
		int j=0;
		while (req.getParameter(j+".domanda") != null) {
			risposte.add(req.getParameter(j+".domanda"));
		}
		for(int i=0; i<risposte.size(); i++) {
			Risposta risposta = new RispostaImpl();
			risposta.setSondaggio(sondaggio);
			risposta.setDomanda(domande.get(i));
			risposta.setRisposta(risposte.get(i)); 
			((SondaggioDataLayer)req.getAttribute("datalayer")).getRispostaDAO().storeRisposta(risposta, sondaggio, domande.get(i));
			
		}
	}
	
	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException {
		try {
			if(req.getParameter("updateSubmit") != null) {
				
			} else {
				stampaSondaggio(req, res);
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
        return "Servlet per la compilazione dei sondaggi";
	}

}
