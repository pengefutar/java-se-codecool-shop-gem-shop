package com.codecool.shop.model;

/**
 * Created by eszti on 2017.04.27..
 */
public class Order implements Orderable{

    private int id;
    private String status;
    private static int counter;

    public void Order(){
        this.id = counter;
        counter++;
        this.status = "new";
    }

    public String getStatus(){
        return this.status;
    }

    public boolean checkout(){
        if (this.status=="new") {
            this.status ="checked";
            return true;
        }
        return false;
    }

    public boolean pay() {
        if (this.status == "checked") {
            this.status = "paid";
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

    @Override
    public void add() {

    }

    @Override
    public void remove() {

    }

}
