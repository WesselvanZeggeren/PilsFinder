package com.wessel.PilsFinder.controller.activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wessel.PilsFinder.R;
import com.wessel.PilsFinder.model.Beer.BeerDB;
import com.wessel.PilsFinder.model.Pub.Pub;
import com.wessel.PilsFinder.model.Pub.PubDB;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap googleMap;
    private ArrayList<Marker> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
    }

    // callbacks
    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        this.googleMap.setOnMarkerClickListener( this);

        UiSettings uiSettings = this.googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);

        for (Pub pub : PubDB.getInstance().getPubs()) {

            Marker marker = this.googleMap.addMarker(new MarkerOptions()
                .position(pub.getLocation())
                .title(pub.getName()));
            marker.setTag(pub.getId());

            this.markers.add(marker);
        }

        this.googleMap.setMinZoomPreference(10);
        this.googleMap.setMaxZoomPreference(22);

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

    // event
    private void onPubClicked(View view) {

        this.startActivity(new Intent(this, PubActivity.class));
    }

    private void onBeerClicked(View view) {

        this.startActivity(new Intent(this, BeerActivity.class));
    }
}
