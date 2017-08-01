package ru.otus.pyltsin.HW16.messageSystem;

import ru.otus.pyltsin.HW16.app.Addressee;

/**
 * @author tully
 */
public abstract class Msg {
    public static final String CLASS_NAME_VARIABLE = "className";
    private final Address from;
    private Address to;
    private final String className;

    public void setTo(Address to) {
        this.to = to;
    }

    public Msg(Address from, Address to, Class<?> clazz) {
        this.from = from;
        this.to = to;
        this.className = clazz.getName();
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(LocalMessageSystem localMessageSystem, Addressee addressee);
}
