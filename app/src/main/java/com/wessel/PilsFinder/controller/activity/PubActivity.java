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
import com.wessel.PilsFinder.controller.adapter.PubAdapter;
import com.wessel.PilsFinder.model.Pub.PubDB;

public class PubActivity extends AppCompatActivity {

    private PubAdapter pubAdapter;

    private EditText name;
    private EditText address;
    private EditText openStart;
    private EditText openEnd;
    private EditText latitude;
    private EditText longitude;
    private EditText imagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub);

        this.pubAdapter = new PubAdapter(true);

        this.name = findViewById(R.id.pub_name);
        this.address = findViewById(R.id.pub_address);
        this.openStart = findViewById(R.id.pub_open_start);
        this.openEnd = findViewById(R.id.pub_open_end);
        this.latitude = findViewById(R.id.pub_latitude);
        this.longitude = findViewById(R.id.pub_longitude);
        this.imagePath = findViewById(R.id.pub_image_path);

        Button button = findViewById(R.id.pub_create);
        button.setOnClickListener(this::onCreateClick);

        RecyclerView recyclerView = findViewById(R.id.pub_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.pubAdapter);
    }

    // events
    private void onCreateClick(View view) {

        String name = this.name.getText().toString();
        String address = this.address.getText().toString();
        String open = this.openStart.getText().toString() + " t/m " + this.openEnd.getText().toString();
        String imagePath = this.imagePath.getText().toString();

        double latitude = Double.valueOf(this.latitude.getText().toString());
        double longitude = Double.valueOf(this.longitude.getText().toString());


        if (name.length() > 0 && address.length() > 0 && open.length() > 0 && imagePath.length() > 0 && latitude > 0 && longitude > 0) {

            PubDB.getInstance().createPub(name, open, address, latitude, longitude, "", imagePath);

            this.pubAdapter.notifyDataSetChanged();
        }
    }
}
