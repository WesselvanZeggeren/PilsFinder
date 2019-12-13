package com.wessel.PilsFinder.model.Beer;

public class Beer {

    // attributes
    private String name;
    private String imagePath;
    private String description;

    private int id;
    private int price;
    private int percentage;

    // constructor
    public Beer(int id) {

        this.id = id;
    }

    // getters
    public String getName() {

        return this.name;
    }

    public String getImagePath() {

        return this.imagePath;
    }

    public String getDescription() {

        return this.description;
    }

    public int getId() {

        return this.id;
    }

    public int getPrice() {

        return this.price;
    }

    public int getPercentage() {

        return this.percentage;
    }

    // setters
    public void setName(String name) {

        this.name = name;
    }

    public void setImagePath(String imagePath) {

        this.imagePath = imagePath;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public void setPrice(int price) {

        this.price = price;
    }

    public void setPercentage(int percentage) {

        this.percentage = percentage;
    }
}
