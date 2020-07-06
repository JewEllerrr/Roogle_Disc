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
import model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	private static String pathCSS = "";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		pathCSS = "\"style\\second\\second.css\"";
		PrintWriter pw = response.getWriter();
		pw.println("<!DOCTYPE html> <html> ");
		pw.println("<head>");
		pw.println("<link rel=\"stylesheet\" type=\"text/css\" href= " + pathCSS + ">");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1 class = 'heading'> Registration </h1>");
		pw.println(createBody());
		pw.println("</body> </html>");
	}

	private String createBody() {
		StringBuilder builder = new StringBuilder();
		builder.append("<form action='register' method='post'>");
		builder.append("<table class = 'txt'><tr><td>User name:</td><td>");
		builder.append("<input type='text' name='name' class = 'textBox'/>");
		builder.append("</td></tr><tr> <td>Password:</td> <td>");
		builder.append("<input type='text' name='password' class = 'textBox'/>");
		builder.append("</tr> </table>");
		builder.append("<input type='submit' value='Enter' class = 'button'/>");
		builder.append("</form>");
		return builder.toString();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		PrintWriter pw = response.getWriter();
		pw.println("<!DOCTYPE html> <html> ");
		pw.println("<head>");
		pw.println("<link rel=\"stylesheet\" type=\"text/css\" href= " + pathCSS + ">");
		pw.println("</head>");
		pw.println("<body>");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		User tmp = new User(name, password);
		User sublist = null;
		boolean flag = false;
		for (int i = 0; i < Brain.getUsers().size(); i++) {
			sublist = Brain.getUsers().get(i);
			if (sublist.getName().equals(name))
				flag = true;
		}
		if (flag) {
			pw.println("<p class = 'txt'>Sorry, a user with the same name already exists.</p>");
			pw.println("<a href= 'register' class = 'button' >try again</a>");
			pw.println("</body> </html>");
		} else {
			Brain.getUsers().add(tmp);
			Brain.reWriteFile();
			Brain.reWriteFileAccess(name);
			HttpSession session = request.getSession();
			session.setAttribute("name", name);
			session.setAttribute("password", password);
			pw.println("<p class = 'txt'>Welcome to Roogle disc!</p>");
			pw.println("<a href= 'mainPage' class = 'button' >main page</a>");
			pw.println("</body> </html>");
		}

	}

}
