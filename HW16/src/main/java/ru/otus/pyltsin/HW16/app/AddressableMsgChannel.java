package ru.otus.pyltsin.HW16.app;

import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.TypeAddress;

public interface AddressableMsgChannel extends MsgChannel {
    TypeAddress getTypeAddress();

    String getIdAddress();

    void setAddress(Address address);

    Address getAddress();
}
