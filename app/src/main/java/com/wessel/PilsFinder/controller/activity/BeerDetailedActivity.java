package com.wessel.PilsFinder.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.wessel.PilsFinder.R;
import com.wessel.PilsFinder.model.Beer.Beer;
import com.wessel.PilsFinder.model.Beer.BeerDB;

public class BeerDetailedActivity extends AppCompatActivity {

    private Beer beer;

    private EditText name;
    private EditText percentage;
    private EditText imagePath;
    private EditText description;

    private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_beer_detailed);

        this.beer = (Beer) savedInstanceState.get("beer");

        this.name = findViewById(R.id.beer_detailed_name);
        this.percentage = findViewById(R.id.beer_detailed_percentage);
        this.imagePath = findViewById(R.id.beer_detailed_image_path);
        this.description = findViewById(R.id.beer_detailed_description);

        this.name.setOnFocusChangeListener(this::onNameFocusChanged);
        this.percentage.setOnFocusChangeListener(this::onPercentageFocusChanged);
        this.imagePath.setOnFocusChangeListener(this::onImagePathFocusChanged);
        this.description.setOnFocusChangeListener(this::onDescriptionFocusChanged);

        this.image = findViewById(R.id.beer_detailed_image);

        this.setPicasso();
    }

    // methods
    private void saveBeer() {

        BeerDB.getInstance().updateBeer(this.beer);
    }

    private void setPicasso() {

        Picasso.get().load(this.beer.getImagePath()).into(this.image);
    }

    // events
    private void onNameFocusChanged(View view, boolean b) {

        if (!b)
            this.beer.setName(this.name.getText().toString());

        this.saveBeer();
    }

    private void onPercentageFocusChanged(View view, boolean b) {

        if (!b)
            this.beer.setPercentage(Double.valueOf(this.percentage.getText().toString()));

        this.saveBeer();
    }

    private void onImagePathFocusChanged(View view, boolean b) {

        if (!b)
            this.beer.setImagePath(this.imagePath.getText().toString());

        this.saveBeer();
        this.setPicasso();
    }

    private void onDescriptionFocusChanged(View view, boolean b) {

        if (!b)
            this.beer.setDescription(this.description.getText().toString());

        this.saveBeer();
    }
}
