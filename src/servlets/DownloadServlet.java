package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Brain;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
	private static String pathCSS = "";

	private static final String DataBase = "C://Users//lizfr//eclipse-workspace//RoogleDisc//WebContent//RoogleDiscDownloads//";

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("name");

		String fileName = request.getParameter("fileName");
		File f = new File(DataBase + fileName);
		if (f.exists()) {
			ServletOutputStream out = response.getOutputStream();
			long len = f.length();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			response.addHeader("Content-Length", String.valueOf(len));
			response.setContentType("application/download");
			FileInputStream fileInputStream = new FileInputStream(f);
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			fileInputStream.close();
			out.close();
			String path = request.getContextPath() + "/mainPage";
			response.sendRedirect(path);
		} else {
			pathCSS = "\"style\\second\\second.css\"";
			PrintWriter pw = response.getWriter();
			pw.println("<!DOCTYPE html> <html> ");
			pw.println("<head>");
			pw.println("<link rel=\"stylesheet\" type=\"text/css\" href= " + pathCSS + ">");
			pw.println("</head>");
			pw.println("<body>");
			pw.println("<h1 class = 'heading'>Sorry, owner deleted his file</h1>");

			for (int i = 0; i < Brain.getUsers().size(); i++) {
				if (Brain.getUsers().get(i).containsFileName(fileName)) {
					Brain.getUsers().get(i).deleteFile(fileName);
					break;
				}
			}
			Brain.reWriteFile(fileName);

			for (int i = 0; i < Brain.getUsers().size(); i++) {
				Brain.getUsers().get(i).deleteFileAccess(fileName);
			}
			Brain.reWriteFileAccess();
			pw.println("<a href= 'mainPage' class = 'button'>Return</a>");
			pw.println("</body> </html>");
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}