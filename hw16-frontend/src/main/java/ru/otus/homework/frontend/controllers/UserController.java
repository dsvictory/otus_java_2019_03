package ru.otus.homework.frontend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import ru.otus.homework.frontend.SocketManager;
import ru.otus.homework.frontend.domain.User;
import ru.otus.homework.messageSystem.Address;
import ru.otus.homework.messages.*;
import ru.otus.homework.frontend.messages.MsgCreateUser;
import ru.otus.homework.frontend.messageSystem.FrontendService;

@Controller
public class UserController implements FrontendService {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    private final static String RESPONSE_URL = "/topic/response";
    
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    private final SocketManager socketManager; 
    
    public UserController(SocketManager socketManager) {
    	this.socketManager = socketManager;
    }
    
    private static final Address ADDRESS_FROM = new Address("Frontend");
    
    private static final Address ADDRESS_TO = new Address("DBService");
    
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