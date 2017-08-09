package ru.otus.pyltsin.HW16.messageSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tully
 * update pyltsin
 */
public final class Address {
    private String id;
    private final TypeAddress typeAddress;

    private static Logger log = LoggerFactory.getLogger(Address.class);


    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", typeAddress=" + typeAddress +
                '}';
    }

    public Address(String id, TypeAddress typeAddress) {
        this.typeAddress = typeAddress;
        this.id = id;
        if (typeAddress != TypeAddress.DB && typeAddress != TypeAddress.MESSAGE_SERVER
                && (id == null || "".equals(id))) {
            throw new IllegalArgumentException();
        }

        if (typeAddress == null) {
            throw new IllegalArgumentException();
        }
        log.debug("address: " + this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (id != null ? !id.equals(address.id) : address.id != null) return false;
        return typeAddress == address.typeAddress;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (typeAddress != null ? typeAddress.hashCode() : 0);
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || "".equals(id)) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    public TypeAddress getTypeAddress() {
        return typeAddress;
    }
}
