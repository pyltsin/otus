package ru.otus.pyltsin.HW16.messageSystem;

/**
 * @author tully
 * update pyltsin
 */
public final class Address {
    private final String id;
    private final TypeAddress typeAddress;

    public Address(String id, TypeAddress typeAddress) {
        this.id = id;
        this.typeAddress = typeAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return id != null ? id.equals(address.id) : address.id == null;
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
