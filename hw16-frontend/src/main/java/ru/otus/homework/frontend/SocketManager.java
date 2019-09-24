package ru.otus.homework.frontend;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import ru.otus.homework.frontend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.otus.homework.frontend.messageSystem.FrontendService;
import ru.otus.homework.messages.Message;

import com.google.gson.Gson;

@Service
public class SocketManager {

	private static final String HOST = "localhost";
    private static final int PORT = 6000;
    
    @Autowired
    private FrontendService messageExecutor;
    
    private ClientSocketMessageWorker client;
    /*
    @PostConstruct
    public void init() throws Exception {
    	start();
    }
    */
    private void start() throws Exception {
        client = new ClientSocketMessageWorker(HOST, PORT);
        client.init();
        System.out.println("Start client!");

        ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.submit(() -> {
            while (true){
                Message msg = client.take();
                messageExecutor.addUser((User)new Gson().fromJson(msg.toString(), User.class));
            }
        });
    }
    
    
    public void setMessageExecutor(FrontendService messageExecutor) {
    	this.messageExecutor = messageExecutor;
    }
    
    public void sendMessage(Message message) {
    	client.send(message);
    }
	
}
