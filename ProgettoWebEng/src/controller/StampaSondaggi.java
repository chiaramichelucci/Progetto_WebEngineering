package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataException;
import data.dao.SondaggioDataLayer;
import data.model.Sondaggio;
import security.SecurityLayer;
import template.Failure;
import template.TemplateManagerExeption;
import template.TemplateResult;

public class StampaSondaggi extends SondaggioBaseController {

	private void action_error(HttpServletRequest req, HttpServletResponse res) {
		if (req.getAttribute("exception") != null) {
            (new Failure(getServletContext())).activate((Exception) req.getAttribute("exception"), req, res);
        } else {
            (new Failure(getServletContext())).activate((String) req.getAttribute("message"), req, res);
        }	
	}
	
	
	private void stampaSondaggi(HttpServletRequest req, HttpServletResponse res) throws IOException, DataException, TemplateManagerExeption, ServletException {
		
		TemplateResult resp = new TemplateResult(getServletContext()); 
		
		HttpSession ses = req.getSession();
		String email = (String) ses.getAttribute("email");
		
		if (req.getParameter("daModificare") != null){
			System.out.print( "coso: " + req.getParameter("daModificare"));
			String sondDaModificare = req.getParameter("daModificare");
			Sondaggio sondaggio = (((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().getSondaggioByTitolo(sondDaModificare));
			req.setAttribute("sondaggioDaModificare", sondaggio);
			trasmettiSondaggio(req, res);
		}
		
		List<Sondaggio> sondaggi = (((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().getSondaggioByResponsabile(email));
		req.setAttribute("sondaggi", sondaggi);
		req.setAttribute("add_multi", false);
		req.setAttribute("use_outline", true);
		resp.activate("stampaSondaggi.ftl.html", req, res);
		
	}
	
	private void trasmettiSondaggio(HttpServletRequest req, HttpServletResponse res) throws IOException, DataException, TemplateManagerExeption, ServletException {
		ServletContext context = getServletContext();
		System.out.print(req.getAttribute("sondaggioDaModificare"));
		RequestDispatcher requestDispatcher=context.getRequestDispatcher("/mod");
        requestDispatcher.forward(req, res);
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
			RequestDispatcher rd=req.getRequestDispatcher("login");  
	        rd.forward(req, res);
		}
		return permesso;
	}
	
	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException {
		try {
			if(checkUtente(req, res)) {
				stampaSondaggi(req, res);
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
        return "Servlet per la stampa di una lista dei sondaggi";
	}

}
