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
		req.setAttribute("questions", domande);
		resp.activate("comp.ftl.html", req, res);
		for(int i = 0; i<domande.size(); i++) {
			Domanda domanda = domande.get(i);
			String tipo = domanda.getTipo();
			if (tipo.equals("Radio")) {
				List<Opzione> opzioni = (((SondaggioDataLayer)req.getAttribute("datalayer")).getOpzioneDAO().getOpzioni(domanda));
				req.setAttribute("options", opzioni);
				req.setAttribute("domanda", domanda);
				resp.activate2("addMulti.ftl.html", req, res);
			} else if (tipo.equals("Checkbox")) {
				List<Opzione> opzioni = (((SondaggioDataLayer)req.getAttribute("datalayer")).getOpzioneDAO().getOpzioni(domanda));
				req.setAttribute("options", opzioni);
				req.setAttribute("domanda", domanda);
				resp.activate2("addMulti.ftl.html", req, res);
			} else {
				req.setAttribute("domanda", domanda);
				resp.activate2("addMulti.ftl.html", req, res);
			}
		}
		compilazioneSondaggio(req, res, sondaggio, domande);
	}
	
	//da conpletare ancora da vedere cosa fare per i radio
	private void compilazioneSondaggio(HttpServletRequest req, HttpServletResponse res, Sondaggio sondaggio, List<Domanda> domande) throws DataException { 
		String[] risposte = req.getParameterValues("risposta");
		if(risposte[0] != null) {
			for(int i=0; i<risposte.length; i++) {
				Risposta risposta = new RispostaImpl();
				risposta.setSondaggio(sondaggio);
				risposta.setDomanda(domande.get(i));
				risposta.setRisposta(risposte[i]);
				((SondaggioDataLayer)req.getAttribute("datalayer")).getRispostaDAO().storeRisposta(risposta, sondaggio, domande.get(i));
			}
		}
	}
	
	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException {
		try {
			stampaSondaggio(req, res);
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
