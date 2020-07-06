package servlets;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/mainPage")
public class StartPageServlet extends HttpServlet {

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			RequestDispatcher notesDispatcher = request.getRequestDispatcher("ads");
	        HttpSession session = request.getSession();
	        String name = (String) session.getAttribute("name");
	        String password = (String) session.getAttribute("password"); 
	        
	        if(name == null || password == null) {
	        	request.setAttribute("action", "pageForAnonymous");
	        	notesDispatcher.include(request, response);
	        }
	        else {
	        	request.setAttribute("action", "pageForUsers");
	        	notesDispatcher.include(request, response);
	        }
		}
		
	}