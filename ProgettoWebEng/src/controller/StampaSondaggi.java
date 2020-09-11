package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataException;
import data.dao.SondaggioDataLayer;
import data.model.Sondaggio;
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
		
		String email = req.getParameter("email");
		
		List<Sondaggio> sondaggi = (((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().getSondaggioByResponsabile(email));
		req.setAttribute("sondaggi", sondaggi);
		req.setAttribute("add_multi", false);
		req.setAttribute("use_outline", true);
		resp.activate("stampaSondaggi.ftl.html", req, res);
		
		int sondDaModificare = Integer.parseInt(req.getParameter("daModificare"));
		Sondaggio sondaggio = (((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().getSondaggio(sondDaModificare));
		req.setAttribute("sondaggioDaModificare", sondaggio);
		
	}
	
	private void trasmettiSondaggio(HttpServletRequest req, HttpServletResponse res) throws IOException, DataException, TemplateManagerExeption, ServletException {
		ServletContext context = getServletContext();
		RequestDispatcher requestDispatcher=context.getRequestDispatcher("mod");
        requestDispatcher.forward(req, res);
	}
	
	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException {
		try {
			if(req.getParameter("daModificare") != null) {
				trasmettiSondaggio(req, res);
			} else {
				stampaSondaggi(req, res);
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
