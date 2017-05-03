package com.codecool.shop.model;

import com.codecool.shop.enumeration.OrderStatus;

/**
 * Created by eszti on 2017.04.27..
 */
public class Order implements Orderable{

    private int id;
    private OrderStatus status;
    private static int counter;

    public void Order(){
        this.id = counter;
        counter++;
        this.status = OrderStatus.NEW;
    }

    public OrderStatus getStatus(){
        return this.status;
    }

    public boolean checkout(){
        if (this.status==OrderStatus.NEW) {
            this.status = OrderStatus.CHECKED;
            return true;
        }
        return false;
    }

    public boolean pay() {
        if (this.status == OrderStatus.CHECKED) {
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
