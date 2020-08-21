package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.dao.AmministratoreDAO;
import data.dao.UtenteDAO;


public class Login extends HttpServlet {  
public void doPost(HttpServletRequest request, HttpServletResponse response)  
        throws ServletException, IOException {  
  
    response.setContentType("text/html");  
    PrintWriter out = response.getWriter();  
          
    String n=request.getParameter("email");  
    String p=request.getParameter("password");  
          
    if(AmministratoreDAO.validate(n, p)){  
        RequestDispatcher rd=request.getRequestDispatcher("amministratore.html");  
        rd.forward(request,response);  
    }  
    else if(UtenteDAO.validate(n, p)){  
        RequestDispatcher rd=request.getRequestDispatcher("index.html");  
        rd.forward(request,response);  
    }
    else{  
        out.print("Sorry username or password error");  
        RequestDispatcher rd=request.getRequestDispatcher("login.html");  
        rd.include(request,response);  
    }  
          
    out.close();  
    }  
}
