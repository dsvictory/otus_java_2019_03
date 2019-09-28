package ru.otus.homework.workers;

import ru.otus.homework.annotation.Blocks;
import ru.otus.homework.messageSystem.Address;
import ru.otus.homework.messages.Message;

import java.io.IOException;

public interface MessageWorker {
    Message pool();

    void send(Message message);

    @Blocks
    Message take() throws InterruptedException;

    void close() throws IOException;
    
    Address getConnectedAddress();
}
