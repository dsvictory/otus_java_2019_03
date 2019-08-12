package ru.otus.homework.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.otus.homework.TemplateManager;
import ru.otus.homework.hibernate.ORMTemplate;
import ru.otus.homework.model.User;
import java.io.IOException;

public class UserCreate extends HttpServlet {
	
	private static final String USER_NAME_PARAMETER = "name";
	
	private static final String REDIRECT_URL = "/admin";
	
	private static final long serialVersionUID = 1L;

	private final ORMTemplate<User> orm;
	
	public UserCreate(ORMTemplate<User> orm) {
		this.orm = orm;
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean success = TemplateManager.getInstance()
							.launchUserCreateTemplate(response.getWriter());
        
		if (success) {
			response.setContentType("text/html");
	        response.setStatus(HttpServletResponse.SC_OK);
		}
    }
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
        String userName = request.getParameter(USER_NAME_PARAMETER);
        
        User newUser = new User();
        newUser.setName(userName);
        orm.saveEntity(newUser);

        response.sendRedirect(REDIRECT_URL);
    }
}
