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

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("name");
		String fileName = request.getParameter("fileName");
		deleteUserFile(name, fileName);
		String path = request.getContextPath() + "/mainPage";
		response.sendRedirect(path);
	}

	private void deleteUserFile(String user, String fileName) throws IOException {
		for (int i = 0; i < Brain.getUsers().size(); i++) {
			if (user.equals(Brain.getUsers().get(i).getName())
					&& Brain.getUsers().get(i).containsFileName(fileName)) {
				Brain.getUsers().get(i).deleteFile(fileName);
				break;
			}
		}
		Brain.reWriteFile(fileName);

		for (int i = 0; i < Brain.getUsers().size(); i++) {
			if (!user.equals(Brain.getUsers().get(i).getName())) {
				Brain.getUsers().get(i).deleteFileAccess(fileName);
			}
		}
		Brain.reWriteFileAccess();
	}

}