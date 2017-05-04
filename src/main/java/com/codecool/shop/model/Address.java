package com.codecool.shop.model;

/**
 * Created by keli on 2017.05.04..
 */
public class Address {

    private String country;
    private String city;
    private String zip;
    private String address;

    public Address(String country, String city, String zip, String address) {
        this.country = country;
        this.city = city;
        this.zip = zip;
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getAddress() {
        return address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
