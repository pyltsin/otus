package ru.otus.pyltsin.HW16.messageSystem;


/**
 * Created by tully.
 */
public class MessageSystemContext {

    private final LocalMessageSystem localMessageSystem;
    private Address serviceAddress = new Address(TypeAddress.DB);

    public MessageSystemContext(LocalMessageSystem localMessageSystem) {
        this.localMessageSystem = localMessageSystem;
    }

    public LocalMessageSystem getLocalMessageSystem() {
        return localMessageSystem;
    }

    public Address getServiceAddress() {
        return serviceAddress;
    }
}
