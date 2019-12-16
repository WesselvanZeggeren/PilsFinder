package com.wessel.PilsFinder.model.Pub;

import com.google.android.gms.maps.model.LatLng;
import com.wessel.PilsFinder.model.Beer.Beer;

import java.util.ArrayList;

public class Pub {

    private String name;
    private String open;
    private String address;
    private String imagePath;
    private String description;

    private int id;
    private LatLng location;
    private ArrayList<Beer> beers;

    public Pub (int id) {

        this.id = id;
        this.beers = new ArrayList<>();
    }

    public String getName() {

        return this.name;
    }

    public String getOpen() {

        return this.open;
    }

    public String getAddress() {

        return this.address;
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

    public LatLng getLocation() {

        return this.location;
    }

    public ArrayList<Beer> getBeers() {

        return this.beers;
    }

    // setters
    public void setName(String name) {

        this.name = name;
    }

    public void setOpen(String open) {

        this.open = open;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public void setImagePath(String imagePath) {

        this.imagePath = imagePath;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public void setLocation(LatLng location) {
        
        this.location = location;
    }

    public void setBeers(ArrayList<Beer> beers) {

        this.beers = beers;
    }
}
