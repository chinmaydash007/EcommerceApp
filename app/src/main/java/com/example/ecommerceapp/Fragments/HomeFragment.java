package com.example.ecommerceapp.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.ecommerceapp.Adapter.BestOfferAdapter;
import com.example.ecommerceapp.Adapter.HeaderAdapter;
import com.example.ecommerceapp.CartActivity;
import com.example.ecommerceapp.LoginSignUpActivity;
import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.Model.BestOffers;
import com.example.ecommerceapp.Model.Furniture;
import com.example.ecommerceapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    FirebaseAuth firebaseAuth;
    RecyclerView headerrecylerView, bestOfferRecyclerView;
    HeaderAdapter headerAdapter;
    List<Furniture> furnitureList;
    SwipeRefreshLayout swipeRefreshLayout;
    CollectionReference collectionReference;
    FrameLayout go_to_cart_button;
    ShimmerFrameLayout shimmerFrameLayout;


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setRefreshing(false);
        collectionReference = FirebaseFirestore.getInstance().collection("Furniture");
        go_to_cart_button = view.findViewById(R.id.go_to_cart);
        bestOfferRecyclerView = view.findViewById(R.id.recylerview);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();


        changeUserPref();
        checkUserLogin();
        initOurProductRecylerView();
        initBestOfferRecylerView();


        go_to_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void initBestOfferRecylerView() {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.INVISIBLE);

        ArrayList<BestOffers> arrayList = new ArrayList<>();
        BestOfferAdapter bestOfferAdapter = new BestOfferAdapter(arrayList, getActivity());
        bestOfferRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bestOfferRecyclerView.setAdapter(bestOfferAdapter);
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Offers");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    BestOffers bestOffers = documentSnapshot.toObject(BestOffers.class);
                    Log.d("mytag", bestOffers.getAfter_price());
                    arrayList.add(bestOffers);

                }
                bestOfferAdapter.notifyDataSetChanged();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("mytag", e.getMessage());

            }
        });


    }

    private void initOurProductRecylerView() {

        headerrecylerView = view.findViewById(R.id.headerRecyclerView);
        headerrecylerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        furnitureList = new ArrayList<>();
        headerAdapter = new HeaderAdapter(furnitureList, getActivity());
        headerrecylerView.setAdapter(headerAdapter);
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    Furniture furniture = documentSnapshot.toObject(Furniture.class);
                    furniture.setDucumentId(documentSnapshot.getId());
                    Log.d("myatg", documentSnapshot.getId());
                    furnitureList.add(furniture);

                }
                headerAdapter.notifyDataSetChanged();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("mytag", e.getMessage());
            }
        });
    }

    private void checkUserLogin() {
        FirebaseApp.initializeApp(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(getActivity(), LoginSignUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void changeUserPref() {
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("first_time", false);
        editor.apply();
    }

    @Override
    public void onRefresh() {
        initOurProductRecylerView();
        initBestOfferRecylerView();
        swipeRefreshLayout.setRefreshing(false);
    }
}
