package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import model.Brain;

@WebServlet("/ads")

public class NotesServlet extends HttpServlet {

	private static String pathCSS = "";
	static Brain main;

	@Override
	public void init() {
		main = new Brain();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = (String) request.getAttribute("action");
		pathCSS = "\"style\\second\\second.css\"";
		if (action.equals("pageForUsers")) {
			pageForUsers(request, response);
		} else if (action.equals("pageForAnonymous")) {
			pageForAnonymous(request, response);
		}
	}

	private void pageForAnonymous(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		pw.println("<!DOCTYPE html> <html> ");
		pw.println("<head>");
		pw.println("<link rel=\"stylesheet\" type=\"text/css\" href= " + pathCSS + ">");
		pw.println("</head>");
		pw.println("<body align = 'center'>");
		pw.println("<h1 class = 'heading'>Roogle Disc</h1>");
		pw.println("<a href= 'login' class = 'button' >Login</a>");
		pw.println("<a href= 'register' class = 'button' >Register</a>");
		pw.println(showOnlineUsers());
		pw.println("</body> </html>");
	}

	private void pageForUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("name");
		if (name != null && !Brain.getOlusers().contains(name))
			Brain.getOlusers().add(name);
		PrintWriter pw = response.getWriter();
		pw.println("<!DOCTYPE html> <html> ");
		pw.println("<head>");
		pw.println("<link rel=\"stylesheet\" type=\"text/css\" href= " + pathCSS + ">");
		pw.println("</head>");
		pw.println("<body align = 'center'>");
		pw.println("<h1 class = 'heading'> Roogle Disc</h1>");
		pw.println("<br>");
		pw.println("<form action='upload' enctype='multipart/form-data' method='post'>");
		pw.println("<input type='file' name='data'>");
		pw.println("<input type='submit' value='Upload file'>");
		pw.println("</form><br>");
		pw.println(showAllFilesForUsers(name));
		pw.println(showOnlineUsers());
		pw.println("<a href= 'logout' class = 'button' >Logout</a>");
		pw.println("</body> </html>");
	}

	private String showAllFilesForUsers(String userName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table align = 'center' class = 'txt'>");
		User sublist = null;
		for (int i = 0; i < Brain.getUsers().size(); i++) {
			sublist = Brain.getUsers().get(i);
			if (sublist.getName().equals(userName))
				break;
		}

		for (int j = 0; j < sublist.getFiles().size(); j++) {
			if (sublist.getFiles().get(j) != null) {
				sb.append("<tr><td>" + userName + "</td><td>" + sublist.getFiles().get(j) + "</td>");
				sb.append("<form action='delete' method='get'>");
				sb.append("<input type='hidden' name='fileName' value='" + sublist.getFiles().get(j).toString() + "'>");
				sb.append("<td><input type='submit' value='Delete' class = 'button'></td>");
				sb.append("</form>");

				sb.append("<form action='share' method='get'>");
				sb.append("<input type='hidden' name='fileName' value='" + sublist.getFiles().get(j).toString() + "'>");
				sb.append("<td><input type='submit' value='Share' class = 'button'></td>");
				sb.append("</form>");
			}
		}

		for (Map.Entry<String, ArrayList<String>> pair : sublist.getFilesAccess().entrySet()) {
			String key = pair.getKey();
			for (int j = 0; j < pair.getValue().size(); j++) {
				ArrayList<String> anotherlist = pair.getValue();
				String value = anotherlist.get(j);
				if (value != null && key != null) {
					sb.append("<tr><td>" + key + "</td><td>" + value + "</td>");
					sb.append("<form action='download' method='get'>");
					sb.append("<input type='hidden' name='fileName' value='" + value + "'>");
					sb.append("<td><input type='submit' value='Download' class = 'button'></td>");
					sb.append("</form>");

					sb.append("<form action='share' method='get'>");
					sb.append("<input type='hidden' name='fileName' value='" + value + "'>");
					sb.append("<input type='hidden' name='userName' value='" + key + "'>");
					sb.append("<td><input type='submit' value='Share' class = 'button'></td></tr>");
					sb.append("</form>");

				}
			}
		}
		sb.append("</tr>");

		sb.append("</table>");
		return sb.toString();
	}

	private String showOnlineUsers() {
		StringBuilder sb = new StringBuilder();
		sb.append("<br><table align = 'center' class = 'txt2'");
		sb.append("<caption>Online Users</caption>");
		if (Brain.getOlusers().size() == 0) {
			sb.append("<tr><td> There is no one logged in </td></tr>");
		} else {
			for (int i = 0; i < Brain.getOlusers().size(); i++) {
				sb.append("<tr><td>" + Brain.getOlusers().get(i) + "</td></tr>");
			}
			sb.append("</table>");
		}
		return sb.toString();
	}

}
