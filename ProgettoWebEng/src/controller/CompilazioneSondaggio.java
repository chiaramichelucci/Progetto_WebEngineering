package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataException;
import data.dao.SondaggioDataLayer;
import data.impl.RispostaImpl;
import data.model.Domanda;
import data.model.Opzione;
import data.model.Risposta;
import data.model.Sondaggio;
import data.model.Utente;
import security.SecurityLayer;
import template.Failure;
import template.TemplateManagerExeption;
import template.TemplateResult;

//fatto non provatto

public class CompilazioneSondaggio extends SondaggioBaseController {

	private void action_error(HttpServletRequest req, HttpServletResponse res) {
		if (req.getAttribute("exception") != null) {
            (new Failure(getServletContext())).activate((Exception) req.getAttribute("exception"), req, res);
        } else {
            (new Failure(getServletContext())).activate((String) req.getAttribute("message"), req, res);
        }	
	}
	
	private void stampaSondaggio (HttpServletRequest req, HttpServletResponse res, Sondaggio sondaggio) throws IOException, DataException, TemplateManagerExeption {
		
		TemplateResult resp = new TemplateResult(getServletContext()); 
		
		req.setAttribute("poll", sondaggio);
		List<Domanda> domande = (((SondaggioDataLayer)req.getAttribute("datalayer")).getDomandaDAO().getDomande(sondaggio));
		req.setAttribute("use_outline", true);
		resp.activate("comp.ftl.html", req, res);
		for(int i = 0; i<domande.size(); i++) {
			Domanda domanda = domande.get(i);
			String nota = domanda.getNota();
			System.out.print(nota);
			String tipo = domanda.getTipo();
			if (tipo.equals("Radio")) {
				List<Opzione> opzioni = (((SondaggioDataLayer)req.getAttribute("datalayer")).getOpzioneDAO().getOpzioni(domanda));
				req.setAttribute("options", opzioni);
				req.setAttribute("indiceDomanda", i);
				req.setAttribute("domanda", domanda);
				req.setAttribute("useMulti", "add_multi");
				req.setAttribute("use_outline", false);
				resp.activate("addMulti.ftl.html", req, res);
			} else if (tipo.equals("Checkbox")) {
				List<Opzione> opzioni = (((SondaggioDataLayer)req.getAttribute("datalayer")).getOpzioneDAO().getOpzioni(domanda));
				req.setAttribute("options", opzioni);
				req.setAttribute("indiceDomanda", i);
				req.setAttribute("domanda", domanda);
				req.setAttribute("useMulti", "add_multi");
				req.setAttribute("use_outline", false);
				resp.activate("addMulti.ftl.html", req, res);
			} else {
				req.setAttribute("indiceDomanda", i);
				req.setAttribute("domanda", domanda);
				req.setAttribute("use_outline", false);
				resp.activate("addMulti.ftl.html", req, res);
			}
		}
		
	}
	
	
	private void compilazioneSondaggio(HttpServletRequest req, HttpServletResponse res) throws DataException { 
		int id_sond = Integer.parseInt(req.getParameter("idSond"));
		Sondaggio sondaggio = (((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().getSondaggio(id_sond));
		List<Domanda> domande = (((SondaggioDataLayer)req.getAttribute("datalayer")).getDomandaDAO().getDomande(sondaggio));
		
		for(int i=0; i<domande.size(); i++) {
			Domanda domanda = domande.get(i);
			if(domanda.getTipo().equalsIgnoreCase("checkbox")) {
				String [] testiRisp = req.getParameterValues(i+".domanda");
				for(int j=0; j<testiRisp.length; j++) {
					Risposta risposta = (((SondaggioDataLayer)req.getAttribute("datalayer")).getRispostaDAO().creaRisposta());
					risposta.setDomanda(domanda);
					risposta.setSondaggio(sondaggio);
					risposta.setRisposta(testiRisp[j]);
					((SondaggioDataLayer)req.getAttribute("datalayer")).getRispostaDAO().storeRisposta(risposta);
				}
			} else {
				String testoRisp = req.getParameter(i+".domanda");
				Risposta risposta = (((SondaggioDataLayer)req.getAttribute("datalayer")).getRispostaDAO().creaRisposta());
				risposta.setDomanda(domanda);
				risposta.setSondaggio(sondaggio);
				risposta.setRisposta(testoRisp);
				((SondaggioDataLayer)req.getAttribute("datalayer")).getRispostaDAO().storeRisposta(risposta);
			}
		}
	}
	
	protected boolean checkUtente(HttpServletRequest req, HttpServletResponse res, Sondaggio sondaggio) throws DataException, ServletException, IOException, TemplateManagerExeption {
		
			boolean permesso = false;
			HttpSession ses = SecurityLayer.checkSession(req);
			if(ses != null) {
				int utenteId = (int) ses.getAttribute("id");
				Utente utente = ((SondaggioDataLayer)req.getAttribute("datalayer")).getUtenteDAO().getUtente(utenteId);
				permesso = ((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().checkPermesso(sondaggio, utente);
			} else {
				res.sendRedirect("login");
			}
			return permesso;
		
	}
	
	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, DataException {
		try {
			if (req.getParameter("id") != null) {
				int id = Integer.parseInt(req.getParameter("id"));
				Sondaggio sondaggio = ((SondaggioDataLayer)req.getAttribute("datalayer")).getSondaggioDAO().getSondaggio(id);
				if(sondaggio.getModalita().equalsIgnoreCase("privato")) {
					boolean permesso = checkUtente(req, res, sondaggio);
					if(permesso) {
						stampaSondaggio(req, res, sondaggio);
					} else {
						res.sendRedirect("/login.jsp");
					}
				} else {
					stampaSondaggio(req, res, sondaggio);
				}
			} else {
				if(req.getParameter("submitComp") != null) {
					compilazioneSondaggio(req, res);
					req.setAttribute("risultato", "Compilazione completata");
					RequestDispatcher rd=req.getRequestDispatcher("result");  
			        rd.forward(req, res);
				}
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
        return "Servlet per la compilazione dei sondaggi";
	}

}
