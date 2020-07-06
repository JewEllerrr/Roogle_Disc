package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Brain;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
	
	private static String pathCSS = "";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pathCSS = "\"style\\second\\second.css\"";
		PrintWriter pw = response.getWriter();
        pw.println("<!DOCTYPE html> <html> ");
        pw.println("<head>");
        pw.println("<link rel=\"stylesheet\" type=\"text/css\" href= " + pathCSS + ">");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1 class = 'heading'>Logout page</h1>");
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("name");
		Brain.removeNotOnlineUsers(name);
		session.removeAttribute("name");
		session.removeAttribute("password");
		pw.println("</body> </html>");
		String path = request.getContextPath() + "/mainPage";
        response.sendRedirect(path);
		
	}

}