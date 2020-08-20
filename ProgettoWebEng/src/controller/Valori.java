package controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//classe per provare i valori pressi dalla pagina crea.html
//guarda la console per vedere i valori ricevuti

public class Valori extends HttpServlet {

public void service(HttpServletRequest req, HttpServletResponse res) {
		
		String[] domande = req.getParameterValues("domanda");
		String[] tipi = req.getParameterValues("tipo");
		String[] note = req.getParameterValues("nota");
		
		for(int i = 0;i<domande.length;i++) {
			System.out.print("Domanda " + i + ":" + domande[i]);
			System.out.print("Tipo " + i + ":" + tipi[i]);
			System.out.print("Nota " + i + ":" + note[i]);
		}
		
		
		//System.out.print("Campo 1: " + line[0]);
		//System.out.print("Campo 2: " + line[1]);
		//System.out.print("Campo 3: " + line[2]);
		
	}
	
}
