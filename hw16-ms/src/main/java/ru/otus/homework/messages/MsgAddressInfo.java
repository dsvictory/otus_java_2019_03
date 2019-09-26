package ru.otus.homework.messages;

import ru.otus.homework.messageSystem.Address;

public class MsgAddressInfo extends Message {

	public MsgAddressInfo(Address from) {
		super(MsgAddressInfo.class, from, null);
	}
	
	@Override
	public String toString() {
		return "set connection with " + getFrom().getId() + " client";
	}

}
