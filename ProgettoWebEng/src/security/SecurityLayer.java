package security;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityLayer {

   public static HttpSession checkSession(HttpServletRequest r) {
       boolean check = true;

       HttpSession s = r.getSession(false);
       if (s == null) {
           return null;
       }

       if (s.getAttribute("id") == null) {
           check = false;
       } else if ((s.getAttribute("ip") == null) || !((String) s.getAttribute("ip")).equals(r.getRemoteHost())) {
           check = false;
       } else {
           Calendar begin = (Calendar) s.getAttribute("inizio-sessione");
           Calendar last = (Calendar) s.getAttribute("ultima-azione");
           Calendar now = Calendar.getInstance();
           if (begin == null) {
               check = false;
           } else {
               long secondsfrombegin = (now.getTimeInMillis() - begin.getTimeInMillis()) / 1000;
               if (secondsfrombegin > 3 * 60 * 60) {
                   check = false;
               } else if (last != null) {
                   long secondsfromlast = (now.getTimeInMillis() - last.getTimeInMillis()) / 1000;
                   if (secondsfromlast > 30 * 60) {
                       check = false;
                   }
               }
           }
       }
       if (!check) {
           s.invalidate();
           return null;
       } else {
           s.setAttribute("ultima-azione", Calendar.getInstance());
           return s;
       }
   }

   public static HttpSession createSession(HttpServletRequest request, String email, int userid, String tipo) {
       HttpSession s = request.getSession(true);
       s.setAttribute("email", email);
       s.setAttribute("tipo", tipo);
       s.setAttribute("ip", request.getRemoteHost());
       s.setAttribute("inizio-sessione", Calendar.getInstance());
       s.setAttribute("id", userid);
       return s;
   }

   public static void disposeSession(HttpServletRequest request) {
       HttpSession s = request.getSession(false);
       if (s != null) {
           s.invalidate();
       }
   }

   public static String addSlashes(String s) {
       return s.replaceAll("(['\"\\\\])", "\\\\$1");
   }

   public static String stripSlashes(String s) {
       return s.replaceAll("\\\\(['\"\\\\])", "$1");
   }

   public static int checkNumeric(String s) throws NumberFormatException {
       if (s != null) {
           return Integer.parseInt(s);
       } else {
           throw new NumberFormatException("String argument is null");
       }
   }

   public static boolean checkHttps(HttpServletRequest r) {
       return r.isSecure();
       //String httpsheader = r.getHeader("HTTPS");
       //return (httpsheader != null && httpsheader.toLowerCase().equals("on"));
   }

   public static void redirectToHttps(HttpServletRequest request, HttpServletResponse response) throws ServletException {
       String server = request.getServerName();
       String context = request.getContextPath();
       String path = request.getServletPath();
       String info = request.getPathInfo();
       String query = request.getQueryString();

       String newUrl = "https://" + server + ":8080" +  context + path + (info != null ? info : "") + (query != null ? "?" + query : "");
       try {
           response.sendRedirect(newUrl);
       } catch (IOException ex) {
           try {
               response.sendError(response.SC_INTERNAL_SERVER_ERROR, "Cannot redirect to HTTPS, blocking request");
           } catch (IOException ex1) {
               throw new ServletException("Cannot redirect to https!");
           }
       }
   }
	
}
