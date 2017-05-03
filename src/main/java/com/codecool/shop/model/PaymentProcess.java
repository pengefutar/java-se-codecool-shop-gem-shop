package com.codecool.shop.model;

/**
 * Created by eszti on 2017.04.27..
 */
public class PaymentProcess extends AbstractProcess{

    protected void action(Orderable item){
        item.pay();
    }

}
