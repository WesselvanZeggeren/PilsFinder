package com.wessel.PilsFinder.model.Beer;

public class Beer {

    // attributes
    private String name;
    private String imagePath;
    private String description;

    private int id;

    private double percentage;

    // constructor
    public Beer(int id, String name, double percentage, String description, String imagePath) {

        this.id = id;
        this.name = name;
        this.percentage = percentage;
        this.description = description;
        this.imagePath = imagePath;
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

    public double getPercentage() {

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

    public void setPercentage(double percentage) {

        this.percentage = percentage;
    }
}
