package ru.otus.homework.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.otus.homework.hibernate.ORMTemplate;
import ru.otus.homework.model.User;
import java.io.IOException;

public class UserCreate extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final ORMTemplate<User> orm;
	
	public UserCreate(ORMTemplate<User> orm) {
		this.orm = orm;
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect("/create_user.html");
    }
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
        String userName = request.getParameter("name");
        
        User newUser = new User();
        newUser.setName(userName);
        orm.saveEntity(newUser);

        response.sendRedirect("/admin");
    }
}
