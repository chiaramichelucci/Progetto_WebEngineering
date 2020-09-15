package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataException;
import security.SecurityLayer;
import data.dao.AmministratoreDAO;
import data.dao.SondaggioDataLayer;
import data.dao.UtenteDAO;
import data.model.Amministratore;
import data.model.Utente;
import freemarker.template.TemplateException;
import template.Failure;
import template.TemplateManagerExeption;
import template.TemplateResult;


public class Login extends SondaggioBaseController {  

	private void action_error(HttpServletRequest req, HttpServletResponse res) {
		if (req.getAttribute("exception") != null) {
            (new Failure(getServletContext())).activate((Exception) req.getAttribute("exception"), req, res);
        } else {
            (new Failure(getServletContext())).activate((String) req.getAttribute("message"), req, res);
        }	
	}
	
	public void login(HttpServletRequest req, HttpServletResponse res)  throws ServletException, IOException, TemplateManagerExeption, DataException {  
		
		TemplateResult resp = new TemplateResult(getServletContext()); 
	
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		Amministratore admin = ((SondaggioDataLayer)req.getAttribute("datalayer")).getAmministratoreDAO().checkAdmin(email, password);
		Utente utente = ((SondaggioDataLayer)req.getAttribute("datalayer")).getUtenteDAO().checkUtente(email, password);
		if(admin != null) {
			HttpSession ses = SecurityLayer.checkSession(req);
			if(ses == null){
				SecurityLayer.createSession(req, admin.getEmail(), 1, "amministratore");
			}
			req.setAttribute("email", email);
			RequestDispatcher dispatcher = req.getRequestDispatcher("home");  //home e da definire
            dispatcher.forward(req, res);
			//res.sendRedirect("home");
		} else {
			if(utente != null) {
				HttpSession ses = SecurityLayer.checkSession(req);
				if(ses == null){
					SecurityLayer.createSession(req, utente.getEmail(), utente.getID(), utente.getTipo());
				}
				req.setAttribute("email", email);
				RequestDispatcher dispatcher = req.getRequestDispatcher("home");  //home e da definire
	            dispatcher.forward(req, res);
				//res.sendRedirect("home");
			} else {
				//req.setAttribute("message", "Email/Password non valide");
				//action_error(req, res);
				req.setAttribute("risposta", "Email/Password non valide");
				RequestDispatcher dispatcher = req.getRequestDispatcher("result");  //home e da definire
	            dispatcher.forward(req, res);
				//res.sendRedirect("home");
			}
		}	
    }
	
	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException{
		try {
			login(req, res);
		}catch (IOException ex) {
			req.setAttribute("exception", ex);
            action_error(req, res);
		} catch (TemplateManagerExeption ex) {
			req.setAttribute("exception", ex);
            action_error(req, res);
		}
	} 
}
