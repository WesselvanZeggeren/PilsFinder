package com.wessel.PilsFinder.controller;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wessel.PilsFinder.R;
import com.wessel.PilsFinder.controller.adapter.PubAdapter;
import com.wessel.PilsFinder.model.Pub.Pub;
import com.wessel.PilsFinder.model.Pub.PubDB;

import java.util.ArrayList;

public class PubActivity extends AppCompatActivity {

    private PubDB pubDB;
    private PubAdapter pubAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub);

        this.pubDB = PubDB.getInstance(this);
        this.pubAdapter = new PubAdapter(this.pubDB);

        RecyclerView recyclerView = findViewById(R.id.pub_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.pubAdapter);
    }
}
