package ru.otus.pyltsin.HW16.messageSystem;

/**
 * @author tully
 * update pyltsin
 */
public final class Address {
    private final String id;

    public Address(String id) {
        this.id = id;
        if (TypeAddress.contains(id)) {
            throw new IllegalArgumentException(id);
        }
    }
    public Address(TypeAddress typeAddress) {
        this.id = typeAddress.name();
    }

        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return id.equals(address.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String getId() {
        return id;
    }
}
