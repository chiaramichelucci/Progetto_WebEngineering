package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataException;
import data.dao.SondaggioDataLayer;
import data.model.Domanda;
import data.model.Sondaggio;
import security.SecurityLayer;

@WebServlet("/creazione")
public class CreazioneSondaggio extends HttpServlet {

	/*public void service(HttpServletRequest req, HttpServletResponse res) {
	
		String[] domande = req.getParameterValues("domanda");
		String[] tipi = req.getParameterValues("tipo");
		String[] note = req.getParameterValues("nota");
		
		for(int i = 0;i<domande.length;i++) {
			System.out.print("Domanda " + i + ":" + domande[i]);
			System.out.print("Tipo " + i + ":" + tipi[i]);
			System.out.print("Nota " + i + ":" + note[i]);
		}
		
	
	}*/
	
	private void action_write(HttpServletRequest request, HttpServletResponse response, int sondaggio_id) throws IOException, ServletException, DataException {
        //Sondaggio sondaggio = ((SondaggioDataLayer)request.getAttribute("datalayer")).getSondaggioDAO().createSondaggio();
		
		Sondaggio sondaggio;
		if(sondaggio_id > 0) {
			sondaggio = ((SondaggioDataLayer)request.getAttribute("datalayer")).getSondaggioDAO().getSondaggio(sondaggio_id);
		} else {
			sondaggio = ((SondaggioDataLayer)request.getAttribute("datalayer")).getSondaggioDAO().createSondaggio();
		}
		
    }
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		
    }
}
