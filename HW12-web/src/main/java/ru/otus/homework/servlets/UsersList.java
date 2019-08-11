package ru.otus.homework.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.otus.homework.hibernate.ORMTemplate;
import ru.otus.homework.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class UsersList extends HttpServlet {

	private static final String TEMPLATE_NAME = "users_list.ftl";
	
	private static final String USERS_MODEL_KEY = "users";
	
	private static final long serialVersionUID = 1L;

	private final ORMTemplate<User> orm;
	private final Configuration config;
	
	public UsersList(ORMTemplate<User> orm, Configuration config) {
		this.orm = orm;
		this.config = config;
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Map<String, Object> root = new HashMap<>();
		root.put(USERS_MODEL_KEY, orm.getAll(User.class));
		
        Template temp = config.getTemplate(TEMPLATE_NAME);
        
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        
        try {
			temp.process(root, printWriter);
		} catch (TemplateException e) {
			e.printStackTrace();
		}
    }
	
	
	
}
