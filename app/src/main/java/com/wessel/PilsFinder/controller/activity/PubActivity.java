package com.wessel.PilsFinder.controller.activity;

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

    private PubAdapter pubAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub);

        RecyclerView recyclerView = findViewById(R.id.pub_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PubAdapter(false));
    }
}
