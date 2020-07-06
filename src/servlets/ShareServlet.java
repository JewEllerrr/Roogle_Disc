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

@WebServlet("/share")
public class ShareServlet extends HttpServlet {

	private static String pathCSS = "";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		pathCSS = "\"style\\second\\second.css\"";
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession();
		String fileName = request.getParameter("fileName");
		String userName = request.getParameter("userName");
		String name = (String) session.getAttribute("name");
		pw.println("<!DOCTYPE html> <html> ");
		pw.println("<head>");
		pw.println("<link rel=\"stylesheet\" type=\"text/css\" href= " + pathCSS + ">");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1 class = 'heading'>Choose user</h1>");

		pw.println("<table align = 'center' class = 'txt'>");
		for (int i = 0; i < Brain.getOlusers().size(); i++) {
			
			if (userName != null && !Brain.getOlusers().get(i).equals(userName) && !Brain.getOlusers().get(i).equals(name)) {
				pw.println("<form action='share' method='post'>");
				pw.println("<tr><td>" + Brain.getOlusers().get(i) + "</td>");
				pw.println("<input type='hidden' name='userName' value='" + Brain.getOlusers().get(i) + "'>");
				pw.println("<input type='hidden' name='fileName' value='" + fileName + "'>");
				pw.println("<td><input type='submit' value='Share' class = 'button'></td></tr>");
				pw.println("</form>");
			}
			
			else if (!Brain.getOlusers().get(i).equals(name) && userName == null) {
				pw.println("<form action='share' method='post'>");
				pw.println("<tr><td>" + Brain.getOlusers().get(i) + "</td>");
				pw.println("<input type='hidden' name='userName' value='" + Brain.getOlusers().get(i) + "'>");
				pw.println("<input type='hidden' name='fileName' value='" + fileName + "'>");
				pw.println("<td><input type='submit' value='Share' class = 'button'></td></tr>");
				pw.println("</form>");
			}
		}
		if (Brain.getOlusers().size() > 2) {
			pw.println("<form action='share' method='post'>");
			pw.println("<tr><td>To all users</td>");
			pw.println("<input type='hidden' name='userName' value='all'>");
			pw.println("<input type='hidden' name='fileName' value='" + fileName + "'>");
			pw.println("<td><input type='submit' value='Share' class = 'button'></td></tr>");
			pw.println("</form>");
		}
		pw.println("</table>");

		pw.println("<a href= 'mainPage' class = 'button'>Return</a>");
		pw.println("</body> </html>");

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		String FromUser = (String) session.getAttribute("name");
		String name = request.getParameter("userName");
		String fileName = request.getParameter("fileName");
		PrintWriter pw = response.getWriter();
		pw.println("<!DOCTYPE html> <html> ");
		pw.println("<head>");
		pw.println("<link rel=\"stylesheet\" type=\"text/css\" href= " + pathCSS + ">");
		pw.println("</head>");
		pw.println("<body>");
		if (name != null && fileName != null && !name.equals("all")) {
			for (int i = 0; i < Brain.getUsers().size(); i++) {
				if (Brain.getUsers().get(i).getName().equals(name)
						&& !Brain.getUsers().get(i).containsAccessFile(fileName)) {
					Brain.getUsers().get(i).addFileToFileAccess(FromUser, fileName);
					
				}
			}
			Brain.reWriteFileAccess();
		}
		if (name.equals("all") && fileName != null) {
			for (int i = 0; i < Brain.getUsers().size(); i++) {
				if (!Brain.getUsers().get(i).getName().equals(FromUser)
						&& !Brain.getUsers().get(i).containsAccessFile(fileName)) {
					Brain.getUsers().get(i).addFileToFileAccess(FromUser, fileName);
				}
			}
			Brain.reWriteFileAccess();
		}
		pw.println("</body> </html>");
		String path = request.getContextPath() + "/mainPage";
		response.sendRedirect(path);
	}

}