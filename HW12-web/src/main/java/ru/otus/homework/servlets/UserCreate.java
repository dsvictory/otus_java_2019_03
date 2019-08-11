package ru.otus.homework.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.otus.homework.hibernate.ORMTemplate;
import ru.otus.homework.model.User;
import java.io.IOException;
import java.io.PrintWriter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class UserCreate extends HttpServlet {

	private static final String TEMPLATE_NAME = "create_user.ftl";
	
	private static final String USER_NAME_PARAMETER = "name";
	
	private static final String REDIRECT_URL = "/admin";
	
	private static final long serialVersionUID = 1L;

	private final ORMTemplate<User> orm;
	private final Configuration config;
	
	public UserCreate(ORMTemplate<User> orm, Configuration config) {
		this.orm = orm;
		this.config = config;
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Template temp = config.getTemplate(TEMPLATE_NAME);
        
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        
        try {
			temp.process(null, printWriter);
		} catch (TemplateException e) {
			e.printStackTrace();
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
