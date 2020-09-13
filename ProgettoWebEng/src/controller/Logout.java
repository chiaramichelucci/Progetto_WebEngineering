package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataException;
import template.Failure;

public class Logout extends SondaggioBaseController {

	private void action_error(HttpServletRequest req, HttpServletResponse res) {
		if (req.getAttribute("exception") != null) {
            (new Failure(getServletContext())).activate((Exception) req.getAttribute("exception"), req, res);
        } else {
            (new Failure(getServletContext())).activate((String) req.getAttribute("message"), req, res);
        }	
	}
	
	public void logout(HttpServletRequest req, HttpServletResponse res) {
		HttpSession ses = req.getSession();
		if(ses != null) {
			ses.invalidate();
			try {
				res.sendRedirect("login.jsp");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException {
		logout(req, res);
	}

}
