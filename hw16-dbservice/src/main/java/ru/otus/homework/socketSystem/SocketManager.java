package ru.otus.homework.socketSystem;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.otus.homework.dbservice.orm.DBService;
import ru.otus.homework.domain.User;
import ru.otus.homework.messages.Message;
import ru.otus.homework.messages.MsgAddressInfo;
import ru.otus.homework.messages.MsgCreateUser;

import com.google.gson.Gson;

public class SocketManager {

	private static final String HOST = "localhost";
    private static final int PORT = 6000;
    
    private DBService messageExecutor;
    
    private ClientSocketMessageWorker client;
    
    public SocketManager(DBService messageExecutor) {
    	this.messageExecutor = messageExecutor;
    	start();
    }
    
    private void start() {
        try {
			client = new ClientSocketMessageWorker(HOST, PORT);
			client.init();
	        System.out.println("Start client!");
	        
	    	sendMessage(new MsgAddressInfo(messageExecutor.getAddress()));

	        ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	        executorService.submit(() -> {
	            while (true){
	                Message msg = client.take();
	                
	                User newUser = (User)new Gson().fromJson(((MsgCreateUser)msg).getUserJson(), User.class);
	                
	                messageExecutor.saveUser(newUser);
	            }
	        });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void sendMessage(Message message) {
    	client.send(message);
    }
	
}
