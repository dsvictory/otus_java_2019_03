package ru.otus.homework.controllers;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import ru.otus.homework.WebConfig;
import ru.otus.homework.domain.User;
import ru.otus.homework.messageSystem.Address;
import ru.otus.homework.messageSystem.FrontendService;
import ru.otus.homework.messageSystem.MessageSystem;
import ru.otus.homework.messageSystem.MessageSystemContext;
import ru.otus.homework.messages.MsgCreateUser;

@Controller
public class UserController implements FrontendService {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    private final static String RESPONSE_URL = "/topic/response";
    
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    private final MessageSystemContext context;
    private Address address;
    
    public UserController(MessageSystemContext context) {
    	this.context = context;
    }
    
    @MessageMapping("/user")
    public void saveNewUser(User newUser) {
        logger.info("got message:{}" + newUser);
        context.getMessageSystem().start();
        context.getMessageSystem().sendMessage(new MsgCreateUser(getAddress(), context.getDbAddress(), newUser));
        
        //simpMessagingTemplate.convertAndSend("/topic/response", newUser);
    }

    @Resource(name = WebConfig.FRONTEND_ADDRESS)
    public void setAddress(Address address) {
		this.address = address;
	}
    
	@Override
	public Address getAddress() {
		return this.address;
	}

	@Override
	public MessageSystem getMS() {
		return context.getMessageSystem();
	}

	@PostConstruct
	@Override
	public void init() {
		context.setFrontAddress(getAddress());
		context.getMessageSystem().addAddressee(this);
	}

	@Override
	public void addUser(User newUser) {
		logger.info("got returning user:{}" + newUser);
		simpMessagingTemplate.convertAndSend(RESPONSE_URL, newUser);
	}

}
