package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ecommerceapp.Adapter.HeaderAdapter;
import com.example.ecommerceapp.Fragments.BookmarkFragment;
import com.example.ecommerceapp.Fragments.HomeFragment;
import com.example.ecommerceapp.Fragments.ProfileFragment;
import com.example.ecommerceapp.Fragments.SearchFragment;
import com.example.ecommerceapp.Model.Furniture;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {
    private static final String TAG = "mytag";
    SmoothBottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().add(R.id.container, new HomeFragment()).commit();


    }


    @Override
    public void onItemSelect(int i) {
        Fragment selectedFragment = null;

        switch (i) {
            case 0:
                selectedFragment = new HomeFragment();
                break;
            case 1:
                selectedFragment = new SearchFragment();
                break;
            case 2:
                selectedFragment = new BookmarkFragment();
                break;
            case 3:
                selectedFragment = new ProfileFragment();
                break;
            default:
                selectedFragment = new HomeFragment();


        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();

    }

}
