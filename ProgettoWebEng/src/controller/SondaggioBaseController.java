package controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import data.DataException;
import data.dao.SondaggioDataLayer;

public abstract class SondaggioBaseController extends HttpServlet {

	 @Resource(name = "jdbc/pollweb")
	    private DataSource ds;
	    
	    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException;

	    private void processBaseRequest(HttpServletRequest request, HttpServletResponse response) {

	        try (SondaggioDataLayer datalayer = new SondaggioDataLayer(ds)) {
	            datalayer.init();
	            request.setAttribute("datalayer", datalayer);
	            processRequest(request, response);
	        } catch (Exception ex) {
	            ex.printStackTrace(); //for debugging only
	            //(new FailureResult(getServletContext())).activate(
	                    //(ex.getMessage() != null || ex.getCause() == null) ? ex.getMessage() : ex.getCause().getMessage(), request, response);
	        }
	    }

	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        processBaseRequest(request, response);
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        processBaseRequest(request, response);
	    }
	
}
