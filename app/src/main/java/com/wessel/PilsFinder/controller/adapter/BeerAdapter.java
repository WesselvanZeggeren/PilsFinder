package com.wessel.PilsFinder.controller.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wessel.PilsFinder.R;
import com.wessel.PilsFinder.controller.activity.BeerDetailedActivity;
import com.wessel.PilsFinder.controller.activity.MapActivity;
import com.wessel.PilsFinder.model.Beer.Beer;
import com.wessel.PilsFinder.model.Beer.BeerDB;
import com.wessel.PilsFinder.model.Pub.Pub;
import com.wessel.PilsFinder.model.Pub.PubDB;


import java.util.ArrayList;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerViewHolder> {

    public static BeerAdapter adapter;

    private ArrayList<Beer> beers;

    private String type;
    private boolean isClickable;

    private Pub pub;

    public BeerAdapter(String type, boolean isClickable)
    {

        adapter = this;

        this.type = type;
        this.beers = BeerDB.getInstance().getBeers();

        this.isClickable = isClickable;
    }

    public void setPub(Pub pub)
    {

        this.pub = pub;

        if (this.type.equals("price"))
            this.beers = this.pub.getBeers();
    }

    @NonNull
    @Override
    public BeerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        int resource = 0;

        switch (type) {
            case "price": resource = R.layout.activity_beer_item_price; break;
            case "beer": resource = R.layout.activity_beer_item_beer; break;
            case "pub": resource = R.layout.activity_beer_item_pub; break;
        }

        return new BeerViewHolder(inflater.inflate(resource, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BeerViewHolder holder, int position) {

        Beer beer = this.beers.get(position);

        holder.name.setText(beer.getName());
        holder.percentage.setText(String.valueOf(beer.getPercentage()));

        Picasso.get().load(beer.getImagePath()).into(holder.image);

        if (this.type.equals("price")) {

            holder.price.setText("€ " + this.pub.getPrice(beer.getId()));
            holder.description.setText(beer.getDescription());
        }

        if (this.type.equals("pub")) {

            holder.has.setChecked(this.pub.getBeers().contains(beer));
            holder.input.setText(String.valueOf(this.pub.getPrice(beer.getId())));
        }
    }

    @Override
    public int getItemCount() {

        return this.beers.size();
    }

    class BeerViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView percentage;
        ImageView image;

        TextView price;
        TextView description;

        Button delete;

        CheckBox has;
        EditText input;

        BeerViewHolder(@NonNull View itemView) {

            super(itemView);

            this.name = itemView.findViewById(R.id.beer_item_name);
            this.percentage = itemView.findViewById(R.id.beer_item_percentage);
            this.image = itemView.findViewById(R.id.beer_item_image);

            if (type.equals("price")) {

                this.price = itemView.findViewById(R.id.beer_item_price);
                this.description = itemView.findViewById(R.id.beer_item_description);
            }

            if (type.equals("beer")) {

                this.delete = itemView.findViewById(R.id.beer_item_delete);
                this.delete.setOnClickListener(this::onDeleteClick);
            }

            if (type.equals("pub")) {

                this.has = itemView.findViewById(R.id.beer_item_check);
                this.has.setOnCheckedChangeListener(this::onCheckChange);
                this.input = itemView.findViewById(R.id.beer_item_price_input);
                this.input.setOnFocusChangeListener(this::onInputFocusChange);
            }

            if (isClickable)
                itemView.setOnClickListener(this::onItemViewClick);
        }

        // events
        private void onDeleteClick(View view) {

            BeerDB.getInstance().deleteBeer(this.getCurrentBeer());

            notifyDataSetChanged();

            MapActivity.map.refresh();
        }

        private void onCheckChange(CompoundButton compoundButton, boolean b) {

            if (!pub.getBeers().contains(this.getCurrentBeer()) && b) {

                pub.getBeers().add(this.getCurrentBeer());
                this.setPrice();
            } else if (pub.getBeers().contains(this.getCurrentBeer()) && !b) {

                pub.getBeers().remove(this.getCurrentBeer());
                this.savePub();
            }

            PubAdapter.adapter.notifyDataSetChanged();
        }

        private void onInputFocusChange(View view, boolean b) {

            if (!b && pub.getBeers().contains(this.getCurrentBeer())) {

                this.setPrice();

                PubAdapter.adapter.notifyDataSetChanged();
            }
        }

        private void setPrice() {

            pub.setPrice(this.getCurrentBeer().getId(), Double.valueOf(this.input.getText().toString()));

            this.savePub();
        }

        private void savePub() {

            PubDB.getInstance().updatePub(pub);
        }

        private void onItemViewClick(View view)
        {

            Intent intent = new Intent(view.getContext(), BeerDetailedActivity.class);
            intent.putExtra("id", this.getCurrentBeer().getId());

            view.getContext().startActivity(intent);
        }

        private Beer getCurrentBeer()
        {

            return beers.get(BeerViewHolder.super.getAdapterPosition());
        }
    }
}
