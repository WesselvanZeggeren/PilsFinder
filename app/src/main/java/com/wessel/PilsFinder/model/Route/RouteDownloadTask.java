package com.wessel.PilsFinder.model.Route;

import android.os.AsyncTask;
import android.util.Log;

public class RouteDownloadTask extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] objects) {

        String data = "";

        try {

            data = RouteHelper.downloadRouteUrl((String) objects[0]);

        } catch (Exception e) {

            Log.d("Background Task", e.toString());
        }

        return data;
    }

    @Override
    protected void onPostExecute(Object o) {

        super.onPostExecute(o);

        RouteParseTask routeParseTask = new RouteParseTask();

        routeParseTask.execute((String) o);
    }
}
