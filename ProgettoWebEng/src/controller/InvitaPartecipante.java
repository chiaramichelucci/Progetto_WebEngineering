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
import data.model.Sondaggio;
import data.model.Utente;
import security.SecurityLayer;
import template.Failure;
import template.TemplateManagerExeption;
import template.TemplateResult;

public class InvitaPartecipante extends SondaggioBaseController {

	private void action_error(HttpServletRequest req, HttpServletResponse res) {
		if (req.getAttribute("exception") != null) {
            (new Failure(getServletContext())).activate((Exception) req.getAttribute("exception"), req, res);
        } else {
            (new Failure(getServletContext())).activate((String) req.getAttribute("message"), req, res);
        }	
	}
	
	private void invitaUtente(HttpServletRequest req, HttpServletResponse res) throws DataException, TemplateManagerExeption, ServletException, IOException {
		TemplateResult resp = new TemplateResult(getServletContext()); 
		
		if(req.getParameter("invita") != null) {
			String utenteDaInvitare = req.getParameter("utenteInvito");
			Utente utente = ((SondaggioDataLayer)req.getAttribute("datalayer")).getUtenteDAO().getUtenteByEmail(utenteDaInvitare);
			String sondaggioInvito = req.getParameter("sondaggioInvito");
			Sondaggio sondaggio = ((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().getSondaggioByTitolo(sondaggioInvito);
			((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().invitaUtente(utente, sondaggio);
			req.setAttribute("risultato", "Utente pruomovato con successo");
			RequestDispatcher rd=req.getRequestDispatcher("result");  
	        rd.forward(req, res);
		}
		
		HttpSession ses = req.getSession();
		String email = (String) ses.getAttribute("email");
		
		List<Utente> utenti = (((SondaggioDataLayer)req.getAttribute("datalayer")).getUtenteDAO().getUtenti());
		List<Sondaggio> sondaggi = (((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().getSondaggioByResponsabile(email));
		req.setAttribute("sondaggi", sondaggi);
		req.setAttribute("utenti", utenti);
		req.setAttribute("add_multi", false);
		req.setAttribute("use_outline", true);
		resp.activate("invita.ftl.html", req, res);
		
	}
	
	protected boolean checkUtente(HttpServletRequest req, HttpServletResponse res) throws DataException, ServletException, IOException, TemplateManagerExeption {	
		boolean permesso = false;
		HttpSession ses = SecurityLayer.checkSession(req);
		if(ses != null) {
			if(ses.getAttribute("tipo").equals("amministratore") || ses.getAttribute("tipo").equals("responsabile")) {
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
				invitaUtente(req, res);
			} else {
				res.sendRedirect("denied.jsp");
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
        return "Servlet per l'invito di un partecipante";
	}

}
