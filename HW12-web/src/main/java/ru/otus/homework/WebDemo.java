package ru.otus.homework;

import org.eclipse.jetty.server.Server;
import ru.otus.homework.hibernate.ORMTemplate;
import ru.otus.homework.model.User;

public class WebDemo {
	
    private final static int PORT = 8080;

    public static void main(String[] args) throws Exception {
        WebDemo webDemo = new WebDemo();
        webDemo.start();
    }
    
    private void start() throws Exception {
    	ORMManager ormManager = new ORMManager();
    	ORMTemplate<User> orm = ormManager.getORM();
    	ServerManager serverManager = new ServerManager(orm);
        Server server = serverManager.createServer(PORT);
        server.start();
        server.join();
    }

}
