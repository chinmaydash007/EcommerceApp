package com.example.ecommerceapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.ecommerceapp.Model.Furniture;
import com.example.ecommerceapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;

public class CartAdadpter extends RecyclerView.Adapter<CartAdadpter.CartViewHolder> {

    Context context;
    ArrayList<Furniture> cartList;
    int quantity = 1;
    CustomCLickListerner customCLickListerner;


    public CartAdadpter(Context context, ArrayList<Furniture> cartList) {
        this.context = context;
        this.cartList = cartList;
        customCLickListerner = (CustomCLickListerner) context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_item_cardview, parent, false);
        return new CartViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Furniture furniture = cartList.get(position);
        holder.product_name.setText(furniture.getName());
        holder.product_price.setText(furniture.getPrice());
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(new ColorDrawable(Color.parseColor("#FF2A2A")));

        Glide.with(context).load(furniture.getImageUrl()).apply(requestOptions).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                return false;
            }
        }).transition(DrawableTransitionOptions.withCrossFade()).into(holder.product_image);
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                holder.product_quantity.setText(quantity + "");
            }
        });
        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    holder.product_quantity.setText(quantity + "");
                }

            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCLickListerner.onClick(furniture.getDucumentId());
            }
        });


    }


    @Override
    public int getItemCount() {
        return cartList.size();

    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView product_name, product_price, product_quantity;
        MaterialButton increase, decrease, remove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.imageView);
            product_name = itemView.findViewById(R.id.textView9);
            product_price = itemView.findViewById(R.id.price);
            product_quantity = itemView.findViewById(R.id.textView10);
            increase = itemView.findViewById(R.id.decrease);
            decrease = itemView.findViewById(R.id.increase);
            remove = itemView.findViewById(R.id.remove);


        }
    }

    public interface CustomCLickListerner {
        void onClick(String id);
    }
}
