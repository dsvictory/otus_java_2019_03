package ru.otus.homework.java;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import ru.otus.homework.java.ServerMain;
import ru.otus.homework.runner.ProcessRunnerImpl;
import ru.otus.homework.server.EchoSocketMessageServer;

public class ServerMain {

	private static final String FRONTEND_START_COMMAND = "java -jar ../hw16-frontend/target/hw16-frontend-1.0-SNAPSHOT.jar -port";
    
    private static final String DBSERVICE_START_COMMAND = "java -jar ../_1611-client/target/client-jar-with-dependencies.jar -port";
    
    private static final int CLIENTS_NUMBER = 4;
    
    private static final int FRONTEND_1_PORT = 5050;
    /*
    private static final int FRONTEND_2_PORT = 5051;
    
    private static final int DBSERVICE_1_PORT = 5060;
    private static final int DBSERVICE_2_PORT = 5061;
	*/
	private static final int CLIENT_START_DELAY_SEC = 5;
	
	public static void main(String[] args) throws Exception {
		new ServerMain().start();
	}
	
	private void start() throws Exception {
		
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(CLIENTS_NUMBER);
        
        startClient(executorService, getCommands());
        
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("ru.homework.otus.java:type=Server");

        EchoSocketMessageServer server = new EchoSocketMessageServer();
        mBeanServer.registerMBean(server, objectName);

        server.start();

        executorService.shutdown();

    }
	
	private void startClient(ScheduledExecutorService executorService, List<String> commands) {
		for (String command : commands) {
			executorService.schedule(() -> {
	            try {
	                new ProcessRunnerImpl().start(command);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
		}
    }
	
	private List<String> getCommands() {
		List<String> commands = new ArrayList<>();
		commands.add(FRONTEND_START_COMMAND + " " + FRONTEND_1_PORT);
		//commands.add(FRONTEND_START_COMMAND + " " + FRONTEND_2_PORT);
		//commands.add(DBSERVICE_START_COMMAND + " " + DBSERVICE_1_PORT);
		//commands.add(DBSERVICE_START_COMMAND + " " + DBSERVICE_2_PORT);
		return commands;
	}

}
