package ru.otus.pyltsin.HW2.StandSize;

import ru.otus.pyltsin.HW2.StandSize.Interfaces.ObjectFactory;

/**
 * Created by Pyltsin on 09.04.2017. Algo8
 */
public class FactoryStandSize extends AbstractStandSize {

    private ObjectFactory objectFactory;

    public FactoryStandSize(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    protected Object getObject() {
        return objectFactory.getObject();
    }
}
