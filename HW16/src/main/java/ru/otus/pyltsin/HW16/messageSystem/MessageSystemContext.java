package ru.otus.pyltsin.HW16.messageSystem;


/**
 * Created by tully.
 */
public class MessageSystemContext {

    private final LocalMessageSystem localMessageSystem;

    private Address serviceAddress;

    public MessageSystemContext(LocalMessageSystem localMessageSystem) {
        this.localMessageSystem = localMessageSystem;
    }

    public LocalMessageSystem getLocalMessageSystem() {
        return localMessageSystem;
    }

    public Address getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(Address serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
