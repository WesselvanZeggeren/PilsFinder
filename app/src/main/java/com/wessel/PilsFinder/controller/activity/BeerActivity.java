package com.wessel.PilsFinder.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wessel.PilsFinder.R;
import com.wessel.PilsFinder.controller.adapter.BeerAdapter;
import com.wessel.PilsFinder.model.Beer.BeerDB;

public class BeerActivity extends AppCompatActivity {

    private BeerAdapter beerAdapter;

    private EditText name;
    private EditText percentage;
    private EditText imagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_beer);

        this.beerAdapter = new BeerAdapter("beer", true);

        this.name = findViewById(R.id.beer_name);
        this.percentage = findViewById(R.id.beer_percentage);
        this.imagePath = findViewById(R.id.beer_image_path);

        Button button = findViewById(R.id.beer_create);
        button.setOnClickListener(this::onCreateClicked);

        RecyclerView recyclerView = findViewById(R.id.beer_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.beerAdapter);
    }

    // events
    private void onCreateClicked(View view) {

        String name = this.name.getText().toString();
        String imagePath = this.imagePath.getText().toString();

        double percentage = Double.valueOf(this.percentage.getText().toString());

        if (name.length() > 0 && imagePath.length() > 0 && percentage >= 0) {

            BeerDB.getInstance().createBeer(name, percentage, "", imagePath);

            this.beerAdapter.notifyDataSetChanged();
        }

        this.name.setText("");
        this.imagePath.setText("");
        this.percentage.setText("");

        MapActivity.map.setSpinner();
    }
}
