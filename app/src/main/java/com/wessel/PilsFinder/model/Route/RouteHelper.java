package com.wessel.PilsFinder.model.Route;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.wessel.PilsFinder.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouteHelper {

    public static String getDirectionsUrl(LatLng origin, LatLng dest) {

        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        String sensor = "sensor=false";
        String mode = "mode=walking";
        String key = "key=dc195ddd-af25-4ca6-a090-7d310d20c502";

        return "http://145.48.6.80:3000/directions?" + str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + key;
    }

    public static String downloadRouteUrl(String strUrl) throws IOException {

        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        try {

            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iStream));

            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line);

            data = stringBuilder.toString();
            bufferedReader.close();

        } catch (Exception e) {

            Log.d("Exception", e.toString());

        } finally {

            if (iStream != null)
                iStream.close();

            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return data;
    }

    public static ArrayList<LatLng> parseJsonRoute(JSONObject jsonObject) {

        ArrayList<LatLng> points = new ArrayList<>();

        try {

            JSONObject route = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONObject("legs");
            JSONObject start = route.getJSONObject("start_location");
            JSONArray steps = route.getJSONArray("steps");

            points.add(new LatLng(start.getDouble("lat"), start.getDouble("ling")));

            for (int i = 0; i < steps.length(); i++) {

                JSONObject end = steps.getJSONObject(i).getJSONObject("end_location");
                points.add(new LatLng(end.getDouble("lat"), end.getDouble("ling")));
            }

            return points;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }
}
