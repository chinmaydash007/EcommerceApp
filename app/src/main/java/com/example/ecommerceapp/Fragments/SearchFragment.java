package com.example.ecommerceapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ecommerceapp.Adapter.HeaderAdapter;
import com.example.ecommerceapp.Adapter.SearchAdapter;
import com.example.ecommerceapp.Model.Furniture;
import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    SearchAdapter searchAdapter;
    RecyclerView recyclerView;
    SearchView searchView;


    View view;
    ArrayList<Furniture> furnitureList;
    StaggeredGridLayoutManager staggeredGridLayoutManager;


    CollectionReference furnitureRef = FirebaseFirestore.getInstance().collection("Furniture");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        furnitureList = new ArrayList<>();
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        furnitureList = new ArrayList<>();
        searchAdapter = new SearchAdapter(furnitureList, getActivity());
        recyclerView.setAdapter(searchAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("mytag", query);
                initOurProductRecylerView(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    initOurProductRecylerView("");
                }
                return false;
            }
        });


        initOurProductRecylerView("");


        return view;
    }

    private void initOurProductRecylerView(String query) {
        Log.d("mytag", "called");
        if (query.equals("")) {
            furnitureRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    furnitureList.clear();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        Furniture furniture = documentSnapshot.toObject(Furniture.class);
                        furniture.setDucumentId(documentSnapshot.getId());
                        Log.d("myatg", documentSnapshot.getId());
                        furnitureList.add(furniture);

                    }
                    searchAdapter.notifyDataSetChanged();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("mytag", e.getMessage());
                }
            });
        } else {

            furnitureRef.whereEqualTo("name", query).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    furnitureList.clear();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        Furniture furniture = documentSnapshot.toObject(Furniture.class);
                        furniture.setDucumentId(documentSnapshot.getId());
                        Log.d("myatg", documentSnapshot.getId());
                        furnitureList.add(furniture);

                    }
                    searchAdapter.notifyDataSetChanged();

                }
            });
        }

    }
}
