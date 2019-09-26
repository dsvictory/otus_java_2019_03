package ru.otus.homework.frontend.messageSystem;

import ru.otus.homework.messageSystem.Address;
import ru.otus.homework.messageSystem.Addressee;

public abstract class Message {
    private final Address from;
    private final Address to;

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Addressee addressee);
}
