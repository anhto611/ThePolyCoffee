package com.project.pro112.hydrateam.thepolycoffee.models;

/**
 * Created by VI on 01/12/2017.
 */

public class Food {
    private String discription,
            image,
            name,
            price;

    public Food() {
    }

    public Food(String discription, String image, String name, String price) {
        this.discription = discription;
        this.image = image;
        this.name = name;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}