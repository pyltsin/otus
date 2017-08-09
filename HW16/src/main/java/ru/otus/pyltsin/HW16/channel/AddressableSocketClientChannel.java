package ru.otus.pyltsin.HW16.channel;

import ru.otus.pyltsin.HW16.app.AddressableMsgChannel;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.TypeAddress;

import java.net.Socket;

public class AddressableSocketClientChannel extends SocketClientChannel implements AddressableMsgChannel {
    private Address address;

    public AddressableSocketClientChannel(Socket client) {
        super(client);
    }

    @Override
    public TypeAddress getTypeAddress() {
        return address.getTypeAddress();
    }

    @Override
    public String getIdAddress() {
        return address.getId();
    }

    @Override
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public Address getAddress() {
        return address;
    }
}
