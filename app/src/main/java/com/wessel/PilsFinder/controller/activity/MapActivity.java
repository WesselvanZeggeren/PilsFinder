package com.wessel.PilsFinder.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wessel.PilsFinder.R;
import com.wessel.PilsFinder.model.Beer.BeerDB;
import com.wessel.PilsFinder.model.Pub.PubDB;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        PubDB.setContext(this);
        BeerDB.setContext(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }
}
