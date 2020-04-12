package com.example.ecommerceapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.ecommerceapp.DetailsActivity;
import com.example.ecommerceapp.Model.Furniture;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Utils.Utility;

import java.util.List;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.FurnitureViewHolder> {
    List<Furniture> furnitureList;
    Context context;
    int lastPosition = 0;


    public HeaderAdapter(List<Furniture> furnitures, Context context) {
        this.furnitureList = furnitures;
        this.context = context;


    }

    @NonNull
    @Override
    public FurnitureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.furniture_card_layout, parent, false);
        return new FurnitureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FurnitureViewHolder holder, int position) {
        Furniture furniture = furnitureList.get(position);
        holder.name.setText(furniture.getName());
        holder.price.setText("Rs "+furniture.getPrice());
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(new ColorDrawable(Color.parseColor("#FF2A2A")));

        Glide.with(context).load(furniture.getImageUrl()).apply(requestOptions).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).transition(DrawableTransitionOptions.withCrossFade()).into(holder.imageView);
        setAnimation(holder.frameLayout, position);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("product_id",furniture.getDucumentId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return furnitureList.size();
    }

    class FurnitureViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
        TextView name, price;
        FrameLayout frameLayout;


        public FurnitureViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.furniture_image);
            progressBar = itemView.findViewById(R.id.progress_load_photo);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            frameLayout = itemView.findViewById(R.id.container);

        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {


            Animation animation = AnimationUtils.loadAnimation(context, R.anim.push_left_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
