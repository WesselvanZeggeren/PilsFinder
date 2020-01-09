package com.wessel.PilsFinder.controller.adapter;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wessel.PilsFinder.R;
import com.wessel.PilsFinder.model.Beer.Beer;
import com.wessel.PilsFinder.model.Pub.Pub;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerViewHolder> {

    private ArrayList<Beer> beers;

    private boolean isCheckable;
    private boolean isClickable;

    private Pub pub;

    public BeerAdapter(ArrayList<Beer> beers, boolean isCheckable, boolean isClickable)
    {

        this.beers = beers;
        this.isCheckable = isCheckable;
        this.isClickable = isClickable;
    }

    public void setPub(Pub pub)
    {

        this.pub = pub;
    }

    @NonNull
    @Override
    public BeerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new BeerViewHolder(inflater.inflate(R.layout.activity_beer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BeerViewHolder holder, int position) {

        Beer beer = this.beers.get(position);

    }

    @Override
    public int getItemCount() {

        return this.beers.size();
    }

    class BeerViewHolder extends RecyclerView.ViewHolder {



        public BeerViewHolder(@NonNull View itemView) {

            super(itemView);



            if (isClickable)
                itemView.setOnClickListener(this::onClick);
        }

        private void onClick(View view) {

            Intent intent = new Intent();
            intent.putExtra("beer", (Parcelable) this.getCurrentBeer());

            itemView.
        }

        private Beer getCurrentBeer() {

            return beers.get(BeerViewHolder.super.getAdapterPosition());
        }
    }
}
