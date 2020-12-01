package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataException;
import template.Failure;
import template.TemplateManagerExeption;
import template.TemplateResult;

public class HomepageUtente extends SondaggioBaseController {

	private void action_error(HttpServletRequest req, HttpServletResponse res) {
		if (req.getAttribute("exception") != null) {
            (new Failure(getServletContext())).activate((Exception) req.getAttribute("exception"), req, res);
        } else {
            (new Failure(getServletContext())).activate((String) req.getAttribute("message"), req, res);
        }	
	}
	
	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException {
		TemplateResult resp = new TemplateResult(getServletContext()); 
		req.setAttribute("use_outline", true);
		HttpSession ses = req.getSession();
		System.out.print(ses.getAttribute("email"));
		req.setAttribute("email", ses.getAttribute("email"));
		//req.setAttribute("email", req.getAttribute("email"));
		try {
			resp.activate("home.ftl.html", req, res);
		} catch (TemplateManagerExeption ex) {
			req.setAttribute("exception", ex);
            action_error(req, res);
		}

	}

}
