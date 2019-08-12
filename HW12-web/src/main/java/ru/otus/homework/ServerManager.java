package ru.otus.homework;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

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

import ru.otus.homework.filters.SimpleFilter;
import ru.otus.homework.hibernate.ORMTemplate;
import ru.otus.homework.model.User;
import ru.otus.homework.servlets.AdminStart;
import ru.otus.homework.servlets.UserCreate;
import ru.otus.homework.servlets.UsersList;

public class ServerManager {
	
	private static final String ADMIN_PAGE_URL = "/admin";
	private static final String USERS_LIST_URL = "/admin/usersList";
	private static final String USER_CREATE_URL = "/admin/userCreate";
	
	private static final String DEBUG_FILTER_URL = "/*";
	
	private static final String WELCOME_FILE_NAME = "index.html";
	
	private static final String SECURITY_URL = "/admin/*";
	
	private static final String REALM_FILE_PATH = "./realm.properties";
	
	private ORMTemplate<User> orm;
	
	public ServerManager(ORMTemplate<User> orm) throws IOException {
		this.orm = orm;
	}
	
	public Server createServer(int port) throws MalformedURLException {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new UserCreate(orm)), USER_CREATE_URL);
        context.addServlet(new ServletHolder(new UsersList(orm)), USERS_LIST_URL);
        context.addServlet(new ServletHolder(new AdminStart()), ADMIN_PAGE_URL);
        
        
        context.addFilter(new FilterHolder(new SimpleFilter()), DEBUG_FILTER_URL, null);

        Server server = new Server(port);
        
        server.setHandler(new HandlerList(context));

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{createResourceHandler(), createSecurityHandler(context)});
        server.setHandler(handlers);
        
        return server;
    }
    
    
    private ResourceHandler createResourceHandler() throws MalformedURLException {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{WELCOME_FILE_NAME});
        
        URL fileDir = getDecodedURL(WebDemo.class.getClassLoader().getResource(""));
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
        mapping.setPathSpec(SECURITY_URL);
        mapping.setConstraint(constraint);

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        //как декодировать стороку с юзером:паролем https://www.base64decode.org/
        security.setAuthenticator(new BasicAuthenticator());

        URL propFile = null;
        File realmFile = new File(REALM_FILE_PATH);
        if (realmFile.exists()) {
            propFile = getDecodedURL(realmFile.toURI().toURL());
        }
        if (propFile == null) {
            System.out.println("local realm config not found, looking into Resources");
            propFile = getDecodedURL(WebDemo.class.getClassLoader().getResource("realm.properties"));
        }

        if (propFile == null) {
            throw new RuntimeException("Realm property file not found");
        }

        security.setLoginService(new HashLoginService("MyRealm", propFile.getPath()));
        security.setHandler(new HandlerList(context));
        security.setConstraintMappings(Collections.singletonList(mapping));
        
        return security;
    }
	
    private URL getDecodedURL(URL url) throws MalformedURLException {
    	return new URL(URLDecoder.decode(url.toString(), StandardCharsets.UTF_8));
    }
}
