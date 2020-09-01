package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
		for(int i = 0; i<domande.size(); i++) {
			Domanda domanda = domande.get(i);
			System.out.print(domanda.getID());
			String tipo = domanda.getTipo();
			System.out.print(tipo);
			if (tipo == "Radio" || tipo == "Checkbox") {
				// non entra nel if
				System.out.print("Sono arrivato dentro if");
				List<Opzione> opzioni = (((SondaggioDataLayer)req.getAttribute("datalayer")).getOpzioneDAO().getOpzioni(domanda));
				req.setAttribute("options", opzioni);
			}
		}
		resp.activate("comp.ftl.html", req, res);
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
