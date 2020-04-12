package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.ecommerceapp.Adapter.CartAdadpter;
import com.example.ecommerceapp.Model.Furniture;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements CartAdadpter.CustomCLickListerner {
    CartAdadpter cartAdadpter;
    RecyclerView recyclerView;
    ImageButton back_button;
    ArrayList<Furniture> furnitureArrayList;
    CollectionReference cartRef;
    ExtendedFloatingActionButton extendedFloatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartRef = FirebaseFirestore.getInstance().collection("Cart");
        extendedFloatingActionButton = findViewById(R.id.payment);

        furnitureArrayList = new ArrayList<>();
        back_button = findViewById(R.id.button3);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recylerview);
        cartAdadpter = new CartAdadpter(this, furnitureArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdadpter);

        getCartOrders();
        extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getCartOrders() {
        cartRef.document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("User").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                furnitureArrayList.clear();
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    Furniture furniture = documentSnapshot.toObject(Furniture.class);
                    furniture.setDucumentId(documentSnapshot.getId());
                    furnitureArrayList.add(furniture);
                }
                if (furnitureArrayList.size() > 0) {
                    extendedFloatingActionButton.setVisibility(View.VISIBLE);
                } else {
                    extendedFloatingActionButton.setVisibility(View.INVISIBLE);
                }
                cartAdadpter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(String id) {
        Log.d("mytag", id);
        cartRef.document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("User").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                getCartOrders();

            }
        });
    }
}
