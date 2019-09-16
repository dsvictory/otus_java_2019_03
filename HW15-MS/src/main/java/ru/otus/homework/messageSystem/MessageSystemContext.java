package ru.otus.homework.messageSystem;

import org.springframework.stereotype.*;

import ru.otus.homework.WebConfig;
import ru.otus.homework.messageSystem.Address;
import ru.otus.homework.messageSystem.MessageSystem;

import javax.annotation.*;

@Service
public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private Address frontAddress;
    private Address dbAddress;

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    @Resource(name = WebConfig.FRONTEND_ADDRESS)
    public void setFrontAddress(Address frontAddress) {
        this.frontAddress = frontAddress;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    @Resource(name = WebConfig.DB_SERVICE_ADDRESS)
    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }
}
