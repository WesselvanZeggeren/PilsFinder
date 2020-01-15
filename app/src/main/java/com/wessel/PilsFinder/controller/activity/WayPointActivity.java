package com.wessel.PilsFinder.controller.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wessel.PilsFinder.R;
import com.wessel.PilsFinder.controller.adapter.BeerAdapter;
import com.wessel.PilsFinder.model.Pub.Pub;
import com.wessel.PilsFinder.model.Pub.PubDB;

public class WayPointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_point);

        Pub pub = PubDB.getInstance().getPubById(getIntent().getIntExtra("id", 0));

        BeerAdapter beerAdapter = new BeerAdapter("price", false);
        beerAdapter.setPub(pub);

        RecyclerView recyclerView = findViewById(R.id.way_point_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(beerAdapter);

        TextView title = findViewById(R.id.way_point_title);
        title.setText(pub.getName());

        TextView open = findViewById(R.id.way_point_open);
        open.setText(pub.getOpen());

        TextView address = findViewById(R.id.way_point_address);
        address.setText(pub.getAddress());

        TextView description = findViewById(R.id.way_point_description);
        description.setText(pub.getAddress());

        Picasso.get().load(pub.getImagePath()).into((ImageView) findViewById(R.id.way_point_image));
    }
}
