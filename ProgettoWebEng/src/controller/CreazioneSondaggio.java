package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataException;


@WebServlet("/creazione")
public class CreazioneSondaggio extends SondaggioBaseController {
	
	private void createSondaggio(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		
	
	}

	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException {
		
		try {
			createSondaggio(req, res);
		}catch(IOException ex) {
			req.setAttribute("exception", ex);
            //action_error(req, res);
		}
	}
	
}
	
