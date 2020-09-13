package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataException;
import data.dao.SondaggioDataLayer;
import data.model.Utente;
import security.SecurityLayer;
import template.Failure;
import template.TemplateManagerExeption;
import template.TemplateResult;

public class StampaUtenti extends SondaggioBaseController {

	private void action_error(HttpServletRequest req, HttpServletResponse res) {
		if (req.getAttribute("exception") != null) {
            (new Failure(getServletContext())).activate((Exception) req.getAttribute("exception"), req, res);
        } else {
            (new Failure(getServletContext())).activate((String) req.getAttribute("message"), req, res);
        }	
	}
		
	private void stampaUtenti(HttpServletRequest req, HttpServletResponse res) throws DataException, TemplateManagerExeption, ServletException, IOException {
		TemplateResult resp = new TemplateResult(getServletContext()); 
		
		if(req.getParameter("daPruomovere") != null) {
			System.out.print(" output: " + req.getParameter("daPruomovere"));
			String utenteDaPruomovere = req.getParameter("daPruomovere");
			((SondaggioDataLayer)req.getAttribute("datalayer")).getUtenteDAO().pruomoviUtente(utenteDaPruomovere);
			req.setAttribute("risultato", "Utente pruomovato con successo");
			RequestDispatcher rd=req.getRequestDispatcher("result");  
	        rd.forward(req, res);
		}
		
		List<Utente> utenti = (((SondaggioDataLayer)req.getAttribute("datalayer")).getUtenteDAO().getUtenti());
		req.setAttribute("users", utenti);
		req.setAttribute("add_multi", false);
		req.setAttribute("use_outline", true);
		resp.activate("stampaUtenti.ftl.html", req, res);
		
	}
	
	protected boolean checkUtente(HttpServletRequest req, HttpServletResponse res) throws DataException, ServletException, IOException, TemplateManagerExeption {	
		boolean permesso = false;
		HttpSession ses = SecurityLayer.checkSession(req);
		if(ses != null) {
			if(ses.getAttribute("tipo").equals("amministratore")) {
				permesso = true;
			} else {
				permesso = false;
			}
		} else {
			res.sendRedirect("login.jsp");;
		}
		return permesso;
	}
	
	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException {
		try {
			if(checkUtente(req, res)) {
				stampaUtenti(req, res);
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
        return "Servlet per la stampa degli utenti";
	}

}
