package ru.otus.homework.messages;

import ru.otus.homework.messageSystem.Address;

public abstract class Message {
    public static final String CLASS_NAME_VARIABLE = "className";
    private final String className;
    
    private final Address from;
    private final Address to;

    public Message(Class<?> klass, Address from, Address to) {
        this.className = klass.getName();
        this.to = to;
        this.from = from;
    }
    
    public Address getFrom() {
    	return this.from;
    }
    
    public Address getTo() {
    	return this.to;
    }
}
