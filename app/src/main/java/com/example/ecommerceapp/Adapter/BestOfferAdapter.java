package com.example.ecommerceapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import com.bumptech.glide.request.RequestOptions;

import com.example.ecommerceapp.Model.BestOffers;
import com.example.ecommerceapp.R;

import java.util.ArrayList;

public class BestOfferAdapter extends RecyclerView.Adapter<BestOfferAdapter.BestOfferViewHolder> {
    ArrayList<BestOffers> bestOffersArrayList;
    Context context;

    public BestOfferAdapter(ArrayList<BestOffers> bestOffersArrayList, Context context) {
        this.bestOffersArrayList = bestOffersArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BestOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_card_layout_best_offers, parent, false);
        return new BestOfferViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BestOfferViewHolder holder, int position) {
        BestOffers bestOffers = bestOffersArrayList.get(position);
        holder.name.setText(bestOffers.getName());
        holder.price_after.setText(bestOffers.getAfter_price());
        holder.price_before.setText(bestOffers.getBefore_price());
        holder.rating.setText(bestOffers.getRating());
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(new ColorDrawable(Color.parseColor("#FF2A2A")));

        Glide.with(context).load(bestOffers.getImageUrl()).apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bestOffersArrayList.size();
    }

    class BestOfferViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView rating, price_before, price_after, name;

        public BestOfferViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            rating = itemView.findViewById(R.id.rating);
            price_before = itemView.findViewById(R.id.before);
            price_after = itemView.findViewById(R.id.now);
            name = itemView.findViewById(R.id.item_name);
        }
    }
}
