package template;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import template.Failure;

public class Failure {

	protected ServletContext context;
    private final TemplateResult template;

    public Failure(ServletContext context) {
        this.context = context;
        template = new TemplateResult(context);
    }

    public void activate(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        String message;
        if (exception != null && exception.getMessage() != null) {
            message = exception.getMessage();
        } else if (exception != null) {
            message = exception.getClass().getName();
        } else {
            message = "Unknown Error";
        }
        activate(message, request, response);
    }

    public void activate(String message, HttpServletRequest req, HttpServletResponse res) {
        try {
            if (context.getInitParameter("view.error_template") != null) {
                req.setAttribute("error", message);
                req.setAttribute("outline_tpl", "");
                template.activate(context.getInitParameter("view.error_template"), req, res);
            } else {
                res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
            }
        } catch (Exception ex) {
            message += ". In addition, the following exception was generated while trying to display the error page: " + ex.getMessage();
            try {
                res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
            } catch (IOException ex1) {
                Logger.getLogger(Failure.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    
    }
}
