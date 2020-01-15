package com.wessel.PilsFinder.model.Route;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.wessel.PilsFinder.controller.activity.MapActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RouteParseTask extends AsyncTask<String, Integer, List<List<LatLng>>> {

    @Override
    protected List<List<LatLng>> doInBackground(String... jsonData) {

        List<List<LatLng>> routes = null;

        try {

            routes = RouteHelper.parseJsonRoute(new JSONObject(jsonData[0]));

        } catch (Exception e) {

            e.printStackTrace();
        }

        return routes;
    }

    @Override
    protected void onPostExecute(List<List<LatLng>> result) {

        PolylineOptions route = new PolylineOptions();
        ArrayList points;

        for (List<LatLng> latLngs : result) {

            route = new PolylineOptions();

            points = new ArrayList(latLngs);

            route.addAll(points);
            route.width(8);
            route.color(Color.BLUE);
            route.geodesic(true);
        }

        MapActivity.map.route = route;
        MapActivity.map.refresh();
    }
}
