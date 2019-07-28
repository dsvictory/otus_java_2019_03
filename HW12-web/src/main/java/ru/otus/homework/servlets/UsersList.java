package ru.otus.homework.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.otus.homework.hibernate.ORMTemplate;
import ru.otus.homework.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UsersList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final ORMTemplate<User> orm;
	
	public UsersList(ORMTemplate<User> orm) {
		this.orm = orm;
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
        StringBuffer resultAsString = new StringBuffer("<h1>Users list:</h1> ");
        resultAsString.append("<table>" +
    							"<thead>" + 
    								"<tr>" + 
    									"<th>ID</th>" + 
    									"<th>Name</th>" + 
    								"</tr>" + 
    							"</thead>" +
    							"<tbody>");
        
        List<User> usersList = orm.getAll(User.class);
        for (User user : usersList) {
        	
        	resultAsString.append("<tr>" +
        							"<td>" + user.getId() + "</td>" +
        							"<td>" + user.getName() + "</td>" +
        						"</tr>");
        	
        }
        
        resultAsString.append("</tbody>" +
							"</table>");
        
        resultAsString.append("<a href=\"\\admin\">To the admin page!</a>");

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resultAsString);
        printWriter.flush();
    }
}
