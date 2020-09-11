package controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

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
	
	public void signin(HttpServletRequest req, HttpServletResponse res) throws TemplateException, IOException, TemplateManagerExeption {
		
		TemplateResult resp = new TemplateResult(getServletContext()); 
		req.setAttribute("add_multi", false);
		req.setAttribute("use_outline", false);
		resp.activate("signin.ftl.html", req, res);
		
		Utente utente =  ((SondaggioDataLayer)req.getAttribute("datalayer")).getUtenteDAO().createUtente();
		if (req.getParameter("password") == req.getParameter("passwordCheck")) {
			utente.setPassword("password");
		} else {
			//messagio di errore
		}
		utente.setEmail(req.getParameter("email"));
		utente.setNome(req.getParameter("nome"));
		utente.setCognome(req.getParameter("cognome"));
	}
	
	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException{
		try {
			signin(req, res);
		}catch (IOException ex) {
			req.setAttribute("exception", ex);
            action_error(req, res);
		}catch (TemplateException ex) {
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
