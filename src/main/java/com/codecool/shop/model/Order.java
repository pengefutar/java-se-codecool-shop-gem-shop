package com.codecool.shop.model;

import com.codecool.shop.enumeration.OrderStatus;

import java.util.List;


/**
 * Created by eszti on 2017.04.27..
 */
public class Order extends BaseModel implements Orderable {
    private int id;
    private String name;
    public Address address;
    private OrderStatus status;
    private static int counter;
    private List<LineItem> lineItemList;

    public Order(String name) {
        super(name);
    }

    public Order(String name, List<LineItem> lineItemList) {
        super(name);
        this.status = OrderStatus.NEW;
        this.lineItemList = lineItemList;
    }

    public Order(String name, Address address, List<LineItem> lineItemList) {
        super(name);
        this.id = counter;
        counter++;
        this.address = address;
        this.status = OrderStatus.NEW;
        this.lineItemList = lineItemList;
    }

    public Address getAddress() {
        return address;
    }

    public OrderStatus getStatus() {
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
