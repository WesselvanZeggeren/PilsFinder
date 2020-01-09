package com.wessel.PilsFinder.model.Beer;

import android.content.Context;

import java.util.ArrayList;

public class BeerDB {

    private static BeerDB instance;
    private static Context context;

    private ArrayList<Beer> beers;

    // startup
    private BeerDB(Context context)
    {

        this.beers = new ArrayList<>();
    }

    public static void setContext(Context setContext)
    {

        context = setContext;
    }

    public static BeerDB getInstance()
    {

        if (instance == null && context != null)
            instance = new BeerDB(context);

        return instance;
    }

    // methods
    public ArrayList<Beer> getBeers()
    {

        return this.beers;
    }
}
