package ru.otus.homework.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import ru.otus.homework.TemplateManager;

public class AdminStart extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		boolean success = TemplateManager.getInstance()
							.launchAdminPageTemplate(
									request.getUserPrincipal().getName(), 
									response.getWriter()
							);
		
		if (success) {
			response.setContentType("text/html");
	        response.setStatus(HttpServletResponse.SC_OK);
		}
    }
}
