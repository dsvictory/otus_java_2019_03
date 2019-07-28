package ru.otus.homework;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ru.otus.homework.filters.SimpleFilter;
import ru.otus.homework.hibernate.ORMTemplateImpl;
import ru.otus.homework.model.User;
import ru.otus.homework.servlets.AdminStart;
import ru.otus.homework.servlets.UserCreate;
import ru.otus.homework.servlets.UsersList;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

public class WebDemo {
	
	private final ORMTemplateImpl<User> orm;
    private final static int PORT = 8080;

    public static void main(String[] args) throws Exception {
        WebDemo webDemo = new WebDemo();
        webDemo.start();
    }
    
    private WebDemo() {
    	SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
    			.addAnnotatedClass(User.class)
    	        .buildSessionFactory();
    	orm = new ORMTemplateImpl<User>(sessionFactory);
    	setDefaultUsersList();
    }

    private void setDefaultUsersList() {
    	User user1 = new User();
    	user1.setName("Дядя 1");
    	
    	orm.saveEntity(user1);
    	
		User user2 = new User();
		user2.setName("Дядя 2");
		
		orm.saveEntity(user2);
		
		User user3 = new User();
		user3.setName("Дядя 3");
		
		orm.saveEntity(user3);
    }
    
    private void start() throws Exception {
        Server server = createServer(PORT);
        server.start();
        server.join();
    }

    public Server createServer(int port) throws MalformedURLException {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new UserCreate(orm)), "/admin/userCreate");
        context.addServlet(new ServletHolder(new UsersList(orm)), "/admin/usersList");
        context.addServlet(new ServletHolder(new AdminStart()), "/admin");
        

        context.addFilter(new FilterHolder(new SimpleFilter()), "/*", null);

        Server server = new Server(port);
        server.setHandler(new HandlerList(context));

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{createResourceHandler(), createSecurityHandler(context)});
        server.setHandler(handlers);
        return server;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        URL fileDir = WebDemo.class.getClassLoader().getResource("static");
        if (fileDir == null) {
            throw new RuntimeException("File Directory not found");
        }
        resourceHandler.setResourceBase(fileDir.getPath());
        return resourceHandler;
    }

    private SecurityHandler createSecurityHandler(ServletContextHandler context) throws MalformedURLException {
        Constraint constraint = new Constraint();
        constraint.setName("auth");
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{"admin"});

        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec("/admin/*");
        mapping.setConstraint(constraint);

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        //как декодировать стороку с юзером:паролем https://www.base64decode.org/
        security.setAuthenticator(new BasicAuthenticator());

        URL propFile = null;
        File realmFile = new File("./realm.properties");
        if (realmFile.exists()) {
            propFile = realmFile.toURI().toURL();
        }
        if (propFile == null) {
            System.out.println("local realm config not found, looking into Resources");
            propFile = WebDemo.class.getClassLoader().getResource("realm.properties");
        }

        if (propFile == null) {
            throw new RuntimeException("Realm property file not found");
        }

        security.setLoginService(new HashLoginService("MyRealm", propFile.getPath()));
        security.setHandler(new HandlerList(context));
        security.setConstraintMappings(Collections.singletonList(mapping));

        return security;
    }
}
