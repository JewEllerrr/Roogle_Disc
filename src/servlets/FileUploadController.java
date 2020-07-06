package servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.Brain;

@WebServlet("/upload")
@MultipartConfig(location = "C://Users//lizfr//eclipse-workspace//RoogleDisc//WebContent//RoogleDiscDownloads//", fileSizeThreshold = 1024
		* 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("name");
		Part file = request.getPart("data");
		String fileName = getFileName(file);

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		addUserFile(name, fileName);
		Brain.reWriteFile();
		for (Part part : request.getParts()) {
			part.write(fileName);
		}
		String path = request.getContextPath() + "/mainPage";
		response.sendRedirect(path);
	}

	private void addUserFile(String user, String fileName) {
		for (int i = 0; i < Brain.getUsers().size(); i++) {
			if (user.equals(Brain.getUsers().get(i).getName())
					&& !Brain.getUsers().get(i).containsFileName(fileName)) {
				Brain.getUsers().get(i).addFile(fileName);
			}
		}
	}

	private static String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
			}
		}
		return null;
	}
}