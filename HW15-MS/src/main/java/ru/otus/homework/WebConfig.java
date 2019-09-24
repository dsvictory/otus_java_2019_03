package ru.otus.homework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ru.otus.homework.messageSystem.Address;

@Configuration
@ComponentScan
public class WebConfig {
	
	public static final String FRONTEND_ADDRESS = "frontendAddress";
	
	public static final String DB_SERVICE_ADDRESS = "dbServiceAddress";
    
    @Bean(name = FRONTEND_ADDRESS)
    public Address frontendAddress() {
    	return new Address(FRONTEND_ADDRESS);
    }
    
    @Bean(name = DB_SERVICE_ADDRESS)
    public Address dbServiceAddress() {
    	return new Address(DB_SERVICE_ADDRESS);
    }
    
}
