package com.wessel.PilsFinder.model.Pub;

import android.util.SparseArray;

import com.google.android.gms.maps.model.LatLng;
import com.wessel.PilsFinder.model.Beer.Beer;
import com.wessel.PilsFinder.model.Beer.BeerDB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Pub {

    public boolean closeBy = false;

    private String name;
    private String open;
    private String address;
    private String imagePath;
    private String description;

    private int id;
    private LatLng location;
    private ArrayList<Beer> beers;
    private HashMap<Integer, Double> prices;

    public Pub (int id, String name, String open, String address, double latitude, double longitude, String description, String imagePath) {

        this.id = id;
        this.name = name;
        this.open = open;
        this.beers = new ArrayList<>();
        this.prices = new HashMap<>();
        this.address = address;
        this.location = new LatLng(latitude, longitude);
        this.description = description;
        this.imagePath = imagePath;
    }

    // getters
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

    public double getPrice(int id) {

        if (this.prices.containsKey(id))
            return this.prices.get(id);
        
        return 0;
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

    public void setPrice(int id, double price){

        this.prices.put(id, price);
    }

    // parse
    public String getParsedBeers() {

        StringBuilder stringBuilder = new StringBuilder();

        for (Beer beer : this.beers)
            stringBuilder.append(beer.getId()).append(":");

        return stringBuilder.toString();
    }

    public String getParsedPrices() {

        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<Integer, Double> price : this.prices.entrySet())
            stringBuilder.append(price.getKey()).append("-").append(price.getValue()).append(":");

        return stringBuilder.toString();
    }

    public void parseBeers(String beers) {

        this.beers = new ArrayList<>();

        for (String id : beers.split(":"))
            if (id.length() > 0)
                this.beers.add(BeerDB.getInstance().getBeerById(Integer.valueOf(id)));
    }

    public void parsePrices(String prices) {

        this.prices = new HashMap<>();

        String[] params;
        for (String price : prices.split(":"))
            if ((params = price.split("-")).length == 2)
                this.prices.put(Integer.valueOf(params[0]), Double.valueOf(params[1]));
    }
}
