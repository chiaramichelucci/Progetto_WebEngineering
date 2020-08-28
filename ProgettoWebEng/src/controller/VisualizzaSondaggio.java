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

public abstract class VisualizzaSondaggio extends HttpServlet {

	 @Resource(name = "jdbc/pollweb")
	    private DataSource ds;
 

}
