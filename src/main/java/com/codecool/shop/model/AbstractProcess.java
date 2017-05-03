package com.codecool.shop.model;

/**
 * Created by eszti on 2017.04.27..
 */
public abstract class AbstractProcess {

    public void process(Orderable item) {
        stepBefore();
        action(item);
        stepAfter();
    }

    public void stepBefore() {
        System.out.println("step before");
    }

    protected abstract void action(Orderable item);

    public void stepAfter() {
        System.out.println("step after");
    }
}
