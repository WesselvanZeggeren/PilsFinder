package com.wessel.PilsFinder.model.Beer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wessel.PilsFinder.model.DBConstants;

import java.util.ArrayList;

public class BeerDB extends SQLiteOpenHelper {

    private static BeerDB instance;
    private static Context context;

    private ArrayList<Beer> beers;

    // startup
    private BeerDB(Context context)
    {

        super(context, DBConstants.DATABASE_NAME, null, 3);

        this.beers = new ArrayList<>();

        this.readBeers();
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

    // overrides
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DBConstants.CREATE_BEER_TABLE());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DBConstants.DROP_BEER_TABLE());
        this.onCreate(db);
    }

    // CRUD
    public void createBeer(String name, double percentage, String description, String imagePath)
    {

        Beer beer = new Beer(this.generateId(), name, percentage, description, imagePath);

        this.beers.add(beer);

        this.getWritableDatabase().insert(
            DBConstants.BEER_TABLE_NAME,
            null,
            this.createContentValues(beer)
        );
    }

    private void readBeers()
    {

        Cursor cursor = this.getReadableDatabase().query(
            DBConstants.BEER_TABLE_NAME,
            new String[] {
                DBConstants.BEER_ID,
                DBConstants.BEER_NAME,
                DBConstants.BEER_PERCENTAGE,
                DBConstants.BEER_DESCRIPTION,
                DBConstants.BEER_IMAGE_PATH
            },
            null, null, null, null,
            DBConstants.BEER_ID + " DESC"
        );

        if (cursor.moveToFirst()) do
            this.beers.add(new Beer(
                cursor.getInt(cursor.getColumnIndex(DBConstants.BEER_ID)),
                cursor.getString(cursor.getColumnIndex(DBConstants.BEER_NAME)),
                cursor.getDouble(cursor.getColumnIndex(DBConstants.BEER_PERCENTAGE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.BEER_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(DBConstants.BEER_IMAGE_PATH))
            ));
        while (cursor.moveToNext());
    }

    public void updateBeer(Beer beer)
    {

        this.getWritableDatabase().update(
            DBConstants.BEER_TABLE_NAME,
            this.createContentValues(beer),
            DBConstants.BEER_ID + " = " + beer.getId(),
            null
        );
    }

    public void deleteBeer(Beer beer)
    {

        this.getWritableDatabase().delete(
            DBConstants.BEER_TABLE_NAME,
            DBConstants.BEER_ID + " = " + beer.getId(),
            null
        );

        this.beers.remove(beer);
    }

    // methods
    public ArrayList<Beer> getBeers()
    {

        return this.beers;
    }

    public Beer getBeerById(int id)
    {

        for (Beer beer : this.beers)
            if (beer.getId() == id)
                return beer;

        return null;
    }


    private ContentValues createContentValues(Beer beer)
    {

        ContentValues values = new ContentValues();
        values.put(DBConstants.BEER_ID, beer.getId());
        values.put(DBConstants.BEER_NAME, beer.getName());
        values.put(DBConstants.BEER_PERCENTAGE, beer.getPercentage());
        values.put(DBConstants.BEER_DESCRIPTION, beer.getDescription());
        values.put(DBConstants.BEER_IMAGE_PATH, beer.getImagePath());

        return values;
    }

    private int generateId() {

        int max = 0;

        for (Beer beer : this.beers)
            if (beer.getId() > max)
                max = beer.getId();

        return (max + 1);
    }
}
