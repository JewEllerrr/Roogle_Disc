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

@WebServlet("/login")
public class LogInServlet extends HttpServlet {
	
	private static String pathCSS = "";
   
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	pathCSS = "\"style\\second\\second.css\"";
    	PrintWriter pw = response.getWriter();
        pw.println("<!DOCTYPE html> <html> ");
        pw.println("<head>");
        pw.println("<link rel=\"stylesheet\" type=\"text/css\" href= " + pathCSS + ">");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1 class = 'heading'> Authorization </h1>");
		pw.println(createBody());
		pw.println("</body> </html>");
	}

	private String createBody() {
		StringBuilder builder = new StringBuilder();
		builder.append("<form action='login' method='post'>");
		builder.append("<table class = 'txt'><tr><td>User name:</td><td>");
		builder.append("<input type='text' name='name' class = 'textBox'/>");
		builder.append("</td></tr><tr> <td>Password:</td> <td>");
		builder.append("<input type='password' name='password' class = 'textBox'/>");
		builder.append("</tr> </table>");
		builder.append("<input type='submit' value='Enter' class = 'button'/>");
		builder.append("</form>");
		return builder.toString();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PrintWriter pw = response.getWriter();
        pw.println("<!DOCTYPE html> <html> ");
        pw.println("<head>");
        pw.println("<link rel=\"stylesheet\" type=\"text/css\" href= " + pathCSS + ">");
		pw.println("</head>");
		pw.println("<body>");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		boolean checkInBase = Brain.isItCorrectPassword(name, password);
		if(checkInBase){
			HttpSession session = request.getSession();
			session.setAttribute("name", name);
			session.setAttribute("password", password);
			pw.println("</body> </html>");
			String path = request.getContextPath() + "/mainPage";
            response.sendRedirect(path);
			
		}else{
			pw.println("<p class = 'txt'>Error! Unregistered user.</p>");
			pw.println("<a href= 'mainPage' class = 'button' >main page</a>");
		}
		pw.println("</body> </html>");
	}
	
}
