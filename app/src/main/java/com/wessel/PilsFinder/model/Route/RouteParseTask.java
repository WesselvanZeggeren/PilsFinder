package com.wessel.PilsFinder.model.Route;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.wessel.PilsFinder.controller.activity.MapActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouteParseTask extends AsyncTask<String, Integer, ArrayList<LatLng>> {

    @Override
    protected ArrayList<LatLng> doInBackground(String... jsonData) {

        ArrayList<LatLng> routes = null;

        try {

            routes = RouteHelper.parseJsonRoute(new JSONObject(jsonData[0]));

        } catch (Exception e) {

            e.printStackTrace();
        }

        return routes;
    }

    @Override
    protected void onPostExecute(ArrayList<LatLng> result) {

        PolylineOptions route = new PolylineOptions();

        route.addAll(result);
        route.width(12);
        route.color(Color.RED);
        route.geodesic(true);

        MapActivity.map.route = route;
        MapActivity.map.refresh();
    }
}
