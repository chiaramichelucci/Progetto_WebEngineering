package controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import data.DataException;
import data.dao.SondaggioDataLayer;
import data.model.Domanda;
import data.model.Opzione;
import data.model.Sondaggio;
import template.Failure;
import template.TemplateManagerExeption;
import template.TemplateResult;

public abstract class ModificaSondaggio extends HttpServlet {

	 @Resource(name = "jdbc/pollweb")
	    private DataSource ds;

		private void action_error(HttpServletRequest req, HttpServletResponse res) {
			if (req.getAttribute("exception") != null) {
	            (new Failure(getServletContext())).activate((Exception) req.getAttribute("exception"), req, res);
	        } else {
	            (new Failure(getServletContext())).activate((String) req.getAttribute("message"), req, res);
	        }	
		}
	 
	 private void visualizzaSondaggio (HttpServletRequest req, HttpServletResponse res) throws IOException, DataException, TemplateManagerExeption {
			
			TemplateResult resp = new TemplateResult(getServletContext()); 
			
			int id = Integer.parseInt(req.getParameter("id"));
			
			Sondaggio sondaggio = ((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().getSondaggio(id);
			req.setAttribute("poll", sondaggio);
			String titolo = sondaggio.getTitolo();
			System.out.print(titolo);
			Boolean disponibile = sondaggio.getDisponibile();
			System.out.print(disponibile);
			String modalita = sondaggio.getModalita();
			System.out.print(modalita);
			String url = sondaggio.getUrl();
			System.out.print(url);
			List<Domanda> domande = (((SondaggioDataLayer)req.getAttribute("datalayer")).getDomandaDAO().getDomande(sondaggio));
			req.setAttribute("questions", domande);
			for(int i = 0; i<domande.size(); i++) {
				Domanda domanda = domande.get(i);
				System.out.print(domanda.getID());
				String tipo = domanda.getTipo();
				System.out.print(tipo);
				if (tipo == "Radio" || tipo == "Checkbox") {
					List<Opzione> opzioni = (((SondaggioDataLayer)req.getAttribute("datalayer")).getOpzioneDAO().getOpzioni(domanda));
					req.setAttribute("options", opzioni);
				}
			}
		}

		protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException {
			try {
				visualizzaSondaggio(req, res);
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
    return "Servlet per la modifica dei sondaggi";
}

}
