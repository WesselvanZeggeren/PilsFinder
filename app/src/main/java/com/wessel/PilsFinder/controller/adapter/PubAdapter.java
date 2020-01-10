package com.wessel.PilsFinder.controller.adapter;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wessel.PilsFinder.R;
import com.wessel.PilsFinder.controller.activity.PubDetailedActivity;
import com.wessel.PilsFinder.model.Pub.Pub;
import com.wessel.PilsFinder.model.Pub.PubDB;

import java.util.ArrayList;

public class PubAdapter extends RecyclerView.Adapter<PubAdapter.PubViewHolder>
{

    private ArrayList<Pub> pubs;

    public PubAdapter(PubDB pubDB)
    {

        this.pubs = pubDB.getPubs();
    }

    @NonNull
    @Override
    public PubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PubViewHolder(inflater.inflate(R.layout.activity_pub_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PubViewHolder holder, int position)
    {

        Pub pub = this.pubs.get(position);
        holder.name.setText(pub.getName());
        holder.address.setText(pub.getAddress());
        holder.open.setText(pub.getOpen());
        holder.beer.setText(pub.getBeers().size());

        Picasso.get().load(pub.getImagePath()).into(holder.image);
    }

    @Override
    public int getItemCount()
    {

        return this.pubs.size();
    }

    class PubViewHolder extends RecyclerView.ViewHolder
    {

        TextView name;
        TextView address;
        TextView open;
        TextView beer;
        ImageView image;

        PubViewHolder(@NonNull View itemView)
        {

            super(itemView);
            this.name = itemView.findViewById(R.id.pub_item_name);
            this.address = itemView.findViewById(R.id.pub_item_address);
            this.open = itemView.findViewById(R.id.pub_item_open);
            this.beer = itemView.findViewById(R.id.pub_item_beer);
            this.image = itemView.findViewById(R.id.pub_item_image);

            itemView.setOnClickListener(this::onClick);
        }

        private void onClick(View view)
        {

            Intent intent = new Intent(view.getContext(), PubDetailedActivity.class);
            intent.putExtra("pub", (Parcelable) this.getCurrentPub());

            view.getContext().startActivity(intent);
        }

        private Pub getCurrentPub()
        {

            return pubs.get(PubViewHolder.super.getAdapterPosition());
        }
    }
}
