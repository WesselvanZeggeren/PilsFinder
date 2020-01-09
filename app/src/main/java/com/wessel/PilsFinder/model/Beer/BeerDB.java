package com.wessel.PilsFinder.model.Beer;

import android.content.Context;

import java.util.ArrayList;

public class BeerDB {

    private static BeerDB instance;
    private ArrayList<Beer> beers;

    private BeerDB(Context context)
    {

        this.beers = new ArrayList<>();
    }

    public static BeerDB getInstance(Context context)
    {

        if (instance == null)
            instance = new BeerDB(context);

        return instance;
    }

    public ArrayList<Beer> getBeers()
    {

        return this.beers;
    }
}
