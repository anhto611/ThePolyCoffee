package com.project.pro112.hydrateam.thepolycoffee.models;

/**
 * Created by VI on 02/12/2017.
 */

public class OrderedFood {
    private String discription,
            image,
            name;
    private double price;

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public OrderedFood() {

    }

    public OrderedFood(String discription, String image, String name, double price, int amount) {
        this.discription = discription;
        this.image = image;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    private int amount;

    public OrderedFood(String discription, String image, String name, double price, int amount, int _id) {
        this.discription = discription;
        this.image = image;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this._id = _id;
    }

    private int _id;

}
