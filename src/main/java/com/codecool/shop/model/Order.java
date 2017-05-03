package com.codecool.shop.model;

import com.codecool.shop.enumeration.OrderStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eszti on 2017.04.27..
 */
public class Order extends BaseModel implements Orderable {

    private int id;
    private OrderStatus status;
    private static int counter;
    private List<LineItem> lineItemList;
    public Order(String name) {
        super(name);
    }


    public void Order(){
        this.id = counter;
        counter++;
        this.status = OrderStatus.NEW;
        this.lineItemList = new ArrayList<>();
    }

    public OrderStatus getStatus(){
        return this.status;
    }

    public boolean pay() {
        if (this.status == OrderStatus.NEW) {
            this.status = OrderStatus.PAID;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }

}
