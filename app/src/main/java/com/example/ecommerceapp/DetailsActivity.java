package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.ecommerceapp.Model.Furniture;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailsActivity extends AppCompatActivity {
    ImageView header_image;
    TextView desciption, price, item_name;
    Button add_to_cart_button;
    FirebaseFirestore firebaseFirestore;
    ImageView bookmark_button;
    FirebaseAuth firebaseAuth;
    String userId;

    CollectionReference cartREf;

    Furniture furniture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getWindow().setStatusBarColor(getColor(R.color.light_purple_one));

        String id = getIntent().getStringExtra("product_id");
        item_name = findViewById(R.id.textView2);


        header_image = findViewById(R.id.header_image);
        desciption = findViewById(R.id.textView);
        price = findViewById(R.id.price);
        add_to_cart_button = findViewById(R.id.button2);
        bookmark_button = findViewById(R.id.button);
        cartREf = FirebaseFirestore.getInstance().collection("Cart");

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();


        add_to_cart_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    cartREf.document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("User").document().set(furniture).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DetailsActivity.this, "Added to your cart", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("mytag", e.getMessage());

                        }
                    });
                }
                return false;
            }
        });



        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(new ColorDrawable(Color.parseColor("#FF2A2A")));
        firebaseFirestore.collection("Furniture").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                furniture = documentSnapshot.toObject(Furniture.class);
                Glide.with(DetailsActivity.this).load(furniture.getImageUrl()).apply(requestOptions).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).transition(DrawableTransitionOptions.withCrossFade()).into(header_image);

                desciption.setText(furniture.getDescription());
                price.setText("Rs " + furniture.getPrice());
                item_name.setText(furniture.getName());
                add_to_cart_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                bookmark_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("mytag", e.getMessage());
            }
        });


    }
}
