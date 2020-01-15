package com.wessel.PilsFinder.model.Pub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.wessel.PilsFinder.model.Beer.Beer;
import com.wessel.PilsFinder.model.DBConstants;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PubDB extends SQLiteOpenHelper {

    private static PubDB instance;
    private static Context context;

    private ArrayList<Pub> pubs;

    // startup
    private PubDB (Context context)
    {

        super(context, DBConstants.DATABASE_NAME, null, 3);

        this.pubs = new ArrayList<>();

        this.readPubs();
    }

    public static void setContext(Context setContext)
    {

        context = setContext;
    }

    public static PubDB getInstance()
    {

        if (instance == null && context != null)
            instance = new PubDB(context);

        return instance;
    }

    // overrides
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DBConstants.CREATE_PUB_TABLE());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DBConstants.DROP_PUB_TABLE());
        this.onCreate(db);
    }

    // CRUD
    public void createPub(String name, String open, String address, double latitude, double longitude, String description, String imagePath)
    {

        Pub pub = new Pub(this.generateId(), name, open, address, latitude, longitude, description, imagePath);

        this.pubs.add(pub);

        this.getWritableDatabase().insert(
            DBConstants.PUB_TABLE_NAME,
            null,
            this.createContentValues(pub)
        );
    }

    private void readPubs() {

        Cursor cursor = this.getReadableDatabase().query(
            DBConstants.PUB_TABLE_NAME,
            new String[] {
                DBConstants.PUB_ID,
                DBConstants.PUB_NAME,
                DBConstants.PUB_OPEN,
                DBConstants.PUB_BEERS,
                DBConstants.PUB_PRICES,
                DBConstants.PUB_ADDRESS,
                DBConstants.PUB_LATITUDE,
                DBConstants.PUB_LONGITUDE,
                DBConstants.PUB_DESCRIPTION,
                DBConstants.PUB_IMAGE_PATH
            },
            null, null, null, null,
            DBConstants.PUB_ID + " DESC"
        );

        if (cursor.moveToFirst()) do {

            Pub pub = new Pub(
                cursor.getInt(cursor.getColumnIndex(DBConstants.PUB_ID)),
                cursor.getString(cursor.getColumnIndex(DBConstants.PUB_NAME)),
                cursor.getString(cursor.getColumnIndex(DBConstants.PUB_OPEN)),
                cursor.getString(cursor.getColumnIndex(DBConstants.PUB_ADDRESS)),
                cursor.getDouble(cursor.getColumnIndex(DBConstants.PUB_LATITUDE)),
                cursor.getDouble(cursor.getColumnIndex(DBConstants.PUB_LONGITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.PUB_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(DBConstants.PUB_IMAGE_PATH))
            );

            pub.parseBeers(cursor.getString(cursor.getColumnIndex(DBConstants.PUB_BEERS)));
            pub.parsePrices(cursor.getString(cursor.getColumnIndex(DBConstants.PUB_PRICES)));

            this.pubs.add(pub);

        } while (cursor.moveToNext());
    }

    public void updatePub(Pub pub)
    {

        this.getWritableDatabase().update(
            DBConstants.PUB_TABLE_NAME,
            this.createContentValues(pub),
            DBConstants.PUB_ID + " = " + pub.getId(),
            null
        );
    }

    public void deletePub(Pub pub)
    {

        this.getWritableDatabase().delete(
            DBConstants.PUB_TABLE_NAME,
            DBConstants.PUB_ID + " = " + pub.getId(),
            null
        );

        this.pubs.remove(pub);
    }

    // methods
    public ArrayList<Pub> getPubs() {

        return this.pubs;
    }

    public Pub getPubById(int id) {

        for (Pub pub : this.pubs)
            if (pub.getId() == id)
                return pub;

        return null;
    }

    public Pub getClosestPubByBeer(String beerName, Location location)
    {

        ArrayList<Pub> pubs = new ArrayList<>();

        for (Pub pub : this.pubs)
            for (Beer beer : pub.getBeers())
                if (beer.getName().equals(beerName) && !pubs.contains(pub))
                    pubs.add(pub);

        return this.getClosestPub(pubs, location);
    }

    public Pub getClosestPub(Location location) {

        return this.getClosestPub(this.pubs, location);
    }

    private Pub getClosestPub(ArrayList<Pub> pubs, Location location)
    {

        if (pubs.size() > 0) {

            int id = -1;
            float distance = -1;

            for (Pub pub : pubs) {

                Location pubLocation = new Location(pub.getName());
                pubLocation.setLatitude(pub.getLocation().latitude);
                pubLocation.setLongitude(pub.getLocation().longitude);

                float current = location.distanceTo(pubLocation);

                if (current < distance || id == -1) {

                    id = pub.getId();
                    distance = current;
                }
            }

            return this.getPubById(id);
        }

        return null;
    }

    private ContentValues createContentValues(Pub pub)
    {

        ContentValues values = new ContentValues();
        values.put(DBConstants.PUB_ID, pub.getId());
        values.put(DBConstants.PUB_NAME, pub.getName());
        values.put(DBConstants.PUB_OPEN, pub.getOpen());
        values.put(DBConstants.PUB_BEERS, pub.getParsedBeers());
        values.put(DBConstants.PUB_PRICES, pub.getParsedPrices());
        values.put(DBConstants.PUB_ADDRESS, pub.getAddress());
        values.put(DBConstants.PUB_LATITUDE, pub.getLocation().latitude);
        values.put(DBConstants.PUB_LONGITUDE, pub.getLocation().longitude);
        values.put(DBConstants.PUB_DESCRIPTION, pub.getDescription());
        values.put(DBConstants.PUB_IMAGE_PATH, pub.getImagePath());

        return values;
    }

    private int generateId()
    {

        int max = 0;

        for (Pub pub : this.pubs)
            if (pub.getId() > max)
                max = pub.getId();

        return (max + 1);
    }
}
