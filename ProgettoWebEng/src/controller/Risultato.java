package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataException;
import template.Failure;
import template.TemplateManagerExeption;
import template.TemplateResult;

public class Risultato extends SondaggioBaseController {

	private void action_error(HttpServletRequest req, HttpServletResponse res) {
		if (req.getAttribute("exception") != null) {
            (new Failure(getServletContext())).activate((Exception) req.getAttribute("exception"), req, res);
        } else {
            (new Failure(getServletContext())).activate((String) req.getAttribute("message"), req, res);
        }	
	}
	
	public void stampaRisultato(HttpServletRequest req, HttpServletResponse res) throws TemplateManagerExeption {
		TemplateResult resp = new TemplateResult(getServletContext()); 
		req.setAttribute("add_multi", false);
		req.setAttribute("use_outline", true);
		resp.activate("risultato.ftl.html", req, res);
	}
	
	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException {
		try {
			stampaRisultato(req, res);
		}catch(TemplateManagerExeption ex) {
			req.setAttribute("exception", ex);
            action_error(req, res);
		}
	}
	
	public String getServletInfo() {
        return "Servlet per la stampa dei risultati";
	}
}
