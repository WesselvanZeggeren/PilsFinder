package com.wessel.PilsFinder.model;

public class DBConstants {

    public static String DATABASE_NAME = "pilsFinder";

    public static String BEER_TABLE_NAME = "beer";
    public static String PUB_TABLE_NAME = "pub";

    public static String BEER_ID = "id";
    public static String BEER_NAME = "name";
    public static String BEER_PERCENTAGE = "percentage";
    public static String BEER_DESCRIPTION = "description";
    public static String BEER_IMAGE_PATH = "image_path";

    public static String PUB_ID = "id";
    public static String PUB_NAME = "name";
    public static String PUB_OPEN = "open";
    public static String PUB_BEERS = "beers";
    public static String PUB_PRICES = "prices";
    public static String PUB_ADDRESS = "address";
    public static String PUB_LATITUDE = "latitude";
    public static String PUB_LONGITUDE = "longitude";
    public static String PUB_DESCRIPTION = "description";
    public static String PUB_IMAGE_PATH = "image_path";

    public static String CREATE_BEER_TABLE()
    {

        return
            "CREATE TABLE IF EXISTS " + BEER_TABLE_NAME + " ( " +
                BEER_ID + " INT(32) NOT NULL, " +
                BEER_NAME + " VARCHAR(64) NOT NULL, " +
                BEER_PERCENTAGE + " DOUBLE NOT NULL, " +
                BEER_DESCRIPTION + " VARCHAR(512) NOT NULL, " +
                BEER_IMAGE_PATH + " VARCHAR(512) NOT NULL, " +
                "PRIMARY KEY (" + BEER_ID + ")" +
            ");"
        ;
    }

    public static String DROP_BEER_TABLE()
    {

        return "DROP TABLE IF EXISTS " + BEER_TABLE_NAME;
    }

    public static String CREATE_PUB_TABLE()
    {

        return
            "CREATE TABLE IF EXISTS " + PUB_TABLE_NAME + " ( " +
                PUB_ID + " INT(32) NOT NULL, " +
                PUB_NAME + " VARCHAR(64) NOT NULL, " +
                PUB_OPEN + " VARCHAR(64) NOT NULL, " +
                PUB_BEERS + " VARCHAR(512) NOT NULL, " +
                PUB_PRICES + " VARCHAR(512) NOT NULL, " +
                PUB_ADDRESS + " VARCHAR(128) NOT NULL, " +
                PUB_LATITUDE + " DOUBLE NOT NULL, " +
                PUB_LONGITUDE + " DOUBLE NOT NULL, " +
                PUB_DESCRIPTION + " VARCHAR(512) NOT NULL, " +
                PUB_IMAGE_PATH + " VARCHAR(512) NOT NULL, " +
                "PRIMARY KEY (" + PUB_ID + ")" +
            ");"
        ;
    }

    public static String DROP_PUB_TABLE()
    {

        return "DROP TABLE IF EXISTS " + PUB_TABLE_NAME;
    }
}
