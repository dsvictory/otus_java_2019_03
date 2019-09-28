package ru.otus.homework.frontend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import ru.otus.homework.domain.User;
import ru.otus.homework.frontend.socketSystem.FrontendService;
import ru.otus.homework.frontend.socketSystem.SocketManager;
import ru.otus.homework.messageSystem.Address;
import ru.otus.homework.messages.*;

@Controller
public class UserController implements FrontendService {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    public static final Address ADDRESS_FROM = new Address("Frontend");
    
    public static final Address ADDRESS_TO = new Address("DBService");
    
    private final static String RESPONSE_URL = "/topic/response";
    
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    private SocketManager socketManager; 
    
    public void setSocketManager(SocketManager socketManager) {
    	this.socketManager = socketManager;
    }
    
    @MessageMapping("/user")
    public void saveNewUser(User newUser) {
        logger.info("got message:{}", newUser);
        Message message = new MsgCreateUser(ADDRESS_FROM, ADDRESS_TO, newUser);
        socketManager.sendMessage(message);
    }
    
	@Override
	public Address getAddress() {
		return ADDRESS_FROM;
	}
	
	public Address getAddressTo() {
		return ADDRESS_TO;
	}

	@Override
	public void addUser(User newUser) {
		logger.info("got returning user:{}", newUser);
		simpMessagingTemplate.convertAndSend(RESPONSE_URL, newUser);
	}


}