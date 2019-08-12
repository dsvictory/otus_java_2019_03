package ru.otus.homework.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.otus.homework.TemplateManager;
import ru.otus.homework.hibernate.ORMTemplate;
import ru.otus.homework.model.User;
import java.io.IOException;

public class UsersList extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private final ORMTemplate<User> orm;
	
	public UsersList(ORMTemplate<User> orm) {
		this.orm = orm;
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		boolean success = TemplateManager.getInstance()
							.launchUsersListTemplate(
									orm.getAll(User.class), 
									response.getWriter()
							);
				
		if (success) {
			response.setContentType("text/html");
	        response.setStatus(HttpServletResponse.SC_OK);
		}
		
    }

}
