package ru.otus.homework.workers;

import ru.otus.homework.messageSystem.Address;
import ru.otus.homework.messages.Message;

import java.io.IOException;

public interface MessageWorker {
    Message pool();

    void send(Message message);

    Message take() throws InterruptedException;

    void close() throws IOException;
    
    Address getConnectedAddress();
}
