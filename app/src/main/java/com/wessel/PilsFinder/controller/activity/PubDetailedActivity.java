package com.wessel.PilsFinder.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;
import com.wessel.PilsFinder.R;
import com.wessel.PilsFinder.controller.adapter.BeerAdapter;
import com.wessel.PilsFinder.controller.adapter.PubAdapter;
import com.wessel.PilsFinder.model.Pub.Pub;
import com.wessel.PilsFinder.model.Pub.PubDB;

public class PubDetailedActivity extends AppCompatActivity {

    private Pub pub;

    private EditText name;
    private EditText address;
    private EditText open_start;
    private EditText open_end;
    private EditText latitude;
    private EditText longitude;
    private EditText image_path;
    private EditText description;

    private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_detailed);

        this.pub = PubDB.getInstance().getPubById(getIntent().getIntExtra("id", -1));

        this.name = findViewById(R.id.pub_detailed_name);
        this.address = findViewById(R.id.pub_detailed_address);
        this.open_start = findViewById(R.id.pub_detailed_open_start);
        this.open_end = findViewById(R.id.pub_detailed_open_end);
        this.latitude = findViewById(R.id.pub_detailed_latitude);
        this.longitude = findViewById(R.id.pub_detailed_longitude);
        this.description = findViewById(R.id.pub_detailed_description);
        this.image = findViewById(R.id.pub_detailed_image);

        this.name.setText(this.pub.getName());
        this.address.setText(this.pub.getAddress());
        this.open_start.setText(this.pub.getOpen().split(" t/m ")[0]);
        this.open_end.setText(this.pub.getOpen().split(" t/m ")[1]);
        this.latitude.setText(String.valueOf(this.pub.getLocation().latitude));
        this.longitude.setText(String.valueOf(this.pub.getLocation().longitude));
        this.description.setText(this.pub.getDescription());

        this.name.setOnFocusChangeListener(this::onNameFocusChange);
        this.address.setOnFocusChangeListener(this::onAddressFocusChange);
        this.open_start.setOnFocusChangeListener(this::onOpenFocusChange);
        this.open_end.setOnFocusChangeListener(this::onOpenFocusChange);
        this.latitude.setOnFocusChangeListener(this::onLocationFocusChange);
        this.longitude.setOnFocusChangeListener(this::onLocationFocusChange);
        this.description.setOnFocusChangeListener(this::onDescriptionFocusChange);

        this.image_path = findViewById(R.id.pub_detailed_image_path);
        this.image_path.setText(this.pub.getImagePath());
        this.image_path.setOnFocusChangeListener(this::onImagePathFocusChange);

        Button delete = findViewById(R.id.pub_detailed_delete);
        delete.setOnClickListener(this::onDeleteClick);

        BeerAdapter beerAdapter = new BeerAdapter("pub", false);
        beerAdapter.setPub(this.pub);

        RecyclerView recyclerView = findViewById(R.id.pub_detailed_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(beerAdapter);

        this.setPicasso();
    }

    // methods
    private void savePub()
    {

        PubDB.getInstance().updatePub(this.pub);

        PubAdapter.adapter.notifyDataSetChanged();

        MapActivity.map.refresh();
    }

    private void setPicasso()
    {

        Picasso.get().load(this.pub.getImagePath()).into(this.image);
    }

    // events
    private void onImagePathFocusChange(View view, boolean b) {

        if (!b && !this.pub.getImagePath().equals(this.image_path.getText().toString())) {

            this.pub.setImagePath(this.image_path.getText().toString());

            this.savePub();
            this.setPicasso();
        }
    }

    private void onNameFocusChange(View view, boolean b) {

        if (!b && !this.pub.getName().equals(this.name.getText().toString())) {

            this.pub.setName(this.name.getText().toString());

            this.savePub();
        }
    }

    private void onAddressFocusChange(View view, boolean b) {

        if (!b && !this.pub.getAddress().equals(this.address.getText().toString())) {

            this.pub.setAddress(this.address.getText().toString());

            this.savePub();
        }
    }

    private void onOpenFocusChange(View view, boolean b) {

        if (!b) {

            this.pub.setOpen(this.open_start.getText().toString() + " t/m " + this.open_end.getText().toString());

            this.savePub();
        }
    }

    private void onLocationFocusChange(View view, boolean b) {

        if (!b) {

            this.pub.setLocation(new LatLng(
                Double.valueOf(this.latitude.getText().toString()),
                Double.valueOf(this.longitude.getText().toString())
            ));

            this.savePub();
        }
    }

    private void onDescriptionFocusChange(View view, boolean b) {

        if (!b && !this.pub.getDescription().equals(this.description.getText().toString())) {

            this.pub.setDescription(this.description.getText().toString());

            this.savePub();
        }
    }

    private void onDeleteClick(View view) {

        PubDB.getInstance().deletePub(pub);

        this.finishActivity(0);

        PubAdapter.adapter.notifyDataSetChanged();

        MapActivity.map.refresh();
    }
}
