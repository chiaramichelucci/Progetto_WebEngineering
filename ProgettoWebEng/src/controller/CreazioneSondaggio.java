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
	
	private void action_write(HttpServletRequest request, HttpServletResponse response, int article_key) throws IOException, ServletException {
        try {
        	List<Domanda> domande = ((SondaggioDataLayer) request.getAttribute("datalayer")).getDomandaDAO().getDomande();
            request.setAttribute("domande", domande);
        } catch (DataException ex) {}
    }
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		
       
    }
}
