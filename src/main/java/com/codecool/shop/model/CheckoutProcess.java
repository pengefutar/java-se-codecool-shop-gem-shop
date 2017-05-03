package com.codecool.shop.model;

/**
 * Created by eszti on 2017.04.27..
 */
public class CheckoutProcess extends AbstractProcess{

    protected void action(Orderable item){
        item.checkout();
    }

}
