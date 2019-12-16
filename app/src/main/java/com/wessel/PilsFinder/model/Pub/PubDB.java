package com.wessel.PilsFinder.model.Pub;

import android.content.Context;

import java.util.ArrayList;

public class PubDB {

    private static PubDB instance;
    private ArrayList<Pub> pubs;

    public PubDB (Context context)
    {

    }

    public static PubDB getInstance(Context context)
    {

        if (instance == null)
            instance = new PubDB(context);

        return instance;
    }

    public ArrayList<Pub> getPubs() {

        return this.pubs;
    }
}
