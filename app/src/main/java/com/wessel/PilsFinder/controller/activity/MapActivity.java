package com.wessel.PilsFinder.controller.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.wessel.PilsFinder.R;
import com.wessel.PilsFinder.model.Beer.Beer;
import com.wessel.PilsFinder.model.Beer.BeerDB;
import com.wessel.PilsFinder.model.Pub.Pub;
import com.wessel.PilsFinder.model.Pub.PubDB;
import com.wessel.PilsFinder.model.Route.RouteDownloadTask;
import com.wessel.PilsFinder.model.Route.RouteHelper;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    public static MapActivity map;
    public PolylineOptions route;
    public GoogleMap googleMap;

    private ArrayList<Marker> markers;

    private Location location;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        map = this;

        PubDB.setContext(this);
        BeerDB.setContext(this);

        this.markers = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.map_map, mapFragment).commit();
        mapFragment.getMapAsync(this);

        Button pub = findViewById(R.id.map_manage_pub);
        pub.setOnClickListener(this::onPubClicked);

        Button beer = findViewById(R.id.map_manage_beer);
        beer.setOnClickListener(this::onBeerClicked);

        Button search = findViewById(R.id.map_beer_search);
        search.setOnClickListener(this::onSearchClicked);

        this.setSpinner();
        this.checkLocationPermissions();
    }

    // methods
    public void refresh() {

        this.googleMap.clear();
        this.markers.clear();

        this.setMarkers();
        this.setSpinner();

        if (this.route != null)
            this.googleMap.addPolyline(this.route);
    }

    private void setMarkers() {

        for (Pub pub : PubDB.getInstance().getPubs()) {

            Marker marker = this.googleMap.addMarker(new MarkerOptions()
                .position(pub.getLocation())
                .title(pub.getName()));
            marker.setTag(pub.getId());

            this.markers.add(marker);
        }
    }

    private void setSpinner() {

        ArrayList<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Any ");

        for (Beer beer : BeerDB.getInstance().getBeers())
            spinnerArray.add(beer.getName());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinner = findViewById(R.id.map_beer_selecter);
        this.spinner.setAdapter(adapter);
    }

    // callbacks
    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        this.googleMap.setOnMarkerClickListener( this);

        this.setMarkers();

        UiSettings uiSettings = this.googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);

        this.googleMap.setMinZoomPreference(10);
        this.googleMap.setMaxZoomPreference(22);

        if (hasLocationAccess())
            this.googleMap.setMyLocationEnabled(true);

        if (this.markers.size() > 0)
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(this.markers.get(0).getPosition()));
        else
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(51.5719, 4.7683)));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Integer id = (Integer) marker.getTag();

        if (id != null) {

            Intent intent = new Intent(this, WayPointActivity.class);
            intent.putExtra("id", id);

            this.startActivity(intent);
        }

        return true;
    }

    // location
    private void checkLocationPermissions()
    {

        if (!hasLocationAccess())
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        else
            setupLocationServices();
    }

    private boolean hasLocationAccess()
    {

        return (
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 0)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                setupLocationServices();
                this.googleMap.setMyLocationEnabled(true);
            }
            else
                Toast.makeText(this, "Permission denied, enable location to use the full app!", Toast.LENGTH_LONG).show();
    }

    private void setupLocationServices()
    {

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000).setFastestInterval(5000).setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationCallback locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {

                if (locationResult == null)
                    return;

                for (Location l : locationResult.getLocations())
                    if (l != null)
                        location = l;
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    // route
    private void initialiseRoute(Pub pub) {

        LatLng latLng = new LatLng(this.location.getLatitude(), this.location.getLongitude());

        String url = RouteHelper.getDirectionsUrl(latLng, pub.getLocation());

        RouteDownloadTask routeDownloadTask = new RouteDownloadTask();
        routeDownloadTask.execute(url);
    }


    // event
    private void onPubClicked(View view) {

        this.startActivity(new Intent(this, PubActivity.class));
    }

    private void onBeerClicked(View view) {

        this.startActivity(new Intent(this, BeerActivity.class));
    }

    private void onSearchClicked(View view) {

        Pub pub;
        String selected = this.spinner.getSelectedItem().toString();

        if (!selected.equals("Any "))
            pub = PubDB.getInstance().getClosestPubByBeer(selected, this.location);
        else
            pub = PubDB.getInstance().getClosestPub(this.location);

        if (pub != null)
            this.initialiseRoute(pub);
        else if (!selected.equals("Any "))
            Toast.makeText(this, "This beer isn't sold anywhere", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "App doesn't contain any pubs", Toast.LENGTH_LONG).show();
    }
}
