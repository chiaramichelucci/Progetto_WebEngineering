package controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataException;
import data.dao.SondaggioDataLayer;
import data.model.Utente;
import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import template.Failure;
import template.TemplateManagerExeption;
import template.TemplateResult;

public class Signin extends SondaggioBaseController{

	private void action_error(HttpServletRequest req, HttpServletResponse res) {
		if (req.getAttribute("exception") != null) {
            (new Failure(getServletContext())).activate((Exception) req.getAttribute("exception"), req, res);
        } else {
            (new Failure(getServletContext())).activate((String) req.getAttribute("message"), req, res);
        }	
	}
	
	public void signin(HttpServletRequest req, HttpServletResponse res) throws IOException, TemplateManagerExeption, DataException, ServletException {
		
		Utente utente =  ((SondaggioDataLayer)req.getAttribute("datalayer")).getUtenteDAO().createUtente();
		utente.setNome(req.getParameter("nome"));
		utente.setCognome(req.getParameter("cognome"));
		utente.setEmail(req.getParameter("email"));
		if (req.getParameter("password").equals(req.getParameter("passwordCheck"))) {
			utente.setPassword("password");
		} else {
			req.setAttribute("risultato", "Password no coincidono");
			RequestDispatcher dispatcher = req.getRequestDispatcher("risultato");  //home e da definire
            dispatcher.forward(req, res);
		}
		
		((SondaggioDataLayer)req.getAttribute("datalayer")).getUtenteDAO().storeUtente(utente);
		
		res.sendRedirect("login.jsp");
	}
	
	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException{
		try {
			signin(req, res);
		}catch (IOException ex) {
			req.setAttribute("exception", ex);
            action_error(req, res);
		} catch (TemplateManagerExeption ex) {
			req.setAttribute("exception", ex);
            action_error(req, res);
		}
	}
	
	public String getServletInfo() {
        return "Servlet per la registrazione degli utenti";
	}
	
}
