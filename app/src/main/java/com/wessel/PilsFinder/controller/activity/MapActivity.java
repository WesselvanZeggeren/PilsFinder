package com.wessel.PilsFinder.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wessel.PilsFinder.R;
import com.wessel.PilsFinder.model.Beer.BeerDB;
import com.wessel.PilsFinder.model.Pub.Pub;
import com.wessel.PilsFinder.model.Pub.PubDB;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        PubDB.setContext(this);
        BeerDB.setContext(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_map);
        mapFragment.getMapAsync(this);

        Button pub = findViewById(R.id.map_pub);
        pub.setOnClickListener(this::onPubClicked);

        Button beer = findViewById(R.id.map_beer);
        beer.setOnClickListener(this::onBeerClicked);
    }

    // callbacks
    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setOnMarkerClickListener( this);

        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);

        for (Pub pub : PubDB.getInstance().getPubs()) {

            Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(pub.getLocation())
                .title(pub.getName()));
            marker.setTag(pub.getId());
        }

        googleMap.setMinZoomPreference(10);
        googleMap.setMaxZoomPreference(22);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Integer id = (Integer) marker.getTag();

        if (id != null) {

            Intent intent = new Intent(this, WayPointActivity.class);
            intent.putExtra("pub", (Parcelable) PubDB.getInstance().getPubById(id));

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
