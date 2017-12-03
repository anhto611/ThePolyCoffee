package com.project.pro112.hydrateam.thepolycoffee.models;

/**
 * Created by VI on 03/12/2017.
 */

public class OrderedFireBaseFood {
    private String discription,
            image,
            name;
    private double price;
    private int amount;

    public OrderedFireBaseFood() {
    }

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

    public OrderedFireBaseFood(String discription, String image, String name, double price, int amount) {
        this.discription = discription;
        this.image = image;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
}
