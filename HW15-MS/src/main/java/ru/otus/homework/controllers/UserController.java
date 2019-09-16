package ru.otus.homework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import ru.otus.homework.WebConfig;
import ru.otus.homework.domain.User;
import ru.otus.homework.messageSystem.Address;
import ru.otus.homework.messageSystem.FrontendService;
import ru.otus.homework.messageSystem.MessageSystem;
import ru.otus.homework.messageSystem.MessageSystemContext;
import ru.otus.homework.messageSystem.MsgCreateUser;
import ru.otus.homework.messageSystem.MsgGetUsers;

import java.util.*;

import javax.annotation.Resource;

@Controller
public class UserController implements FrontendService {

	private Address address;
    private final MessageSystemContext context;
    
    private List<User> users = new ArrayList<>();

    public UserController(MessageSystemContext context) {
    	this.context = context;
    	init();
    }

    @GetMapping({"/", "/user/list"})
    public String userList(Model model) {
        getMS().sendMessage(new MsgGetUsers(this.getAddress(), context.getDbAddress()));
        
        /*
         * Тут сокет к users
         */
        
        model.addAttribute("users", users);
        return "userList.html";
    }

    @GetMapping("/user/create")
    public String userCreate(Model model) {
        model.addAttribute("user", new User());
        return "userCreate.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute User user) {
    	getMS().sendMessage(new MsgCreateUser(this.getAddress(), context.getDbAddress(), user));
        return new RedirectView("/user/list", true);
    }

    @Resource(name = WebConfig.FRONTEND_ADDRESS)
    public void setAddress(Address address) {
    	this.address = address;
    }
    
	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public MessageSystem getMS() {
		return context.getMessageSystem();
	}

	@Override
	public void init() {
		context.getMessageSystem().addAddressee(this);
	}

	@Override
	public void setUsers(List<User> users) {
		this.users = users;
	}

}
