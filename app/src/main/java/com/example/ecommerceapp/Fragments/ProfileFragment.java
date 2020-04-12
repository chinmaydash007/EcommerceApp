package com.example.ecommerceapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerceapp.LoginSignUpActivity;
import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.Model.User;
import com.example.ecommerceapp.ProfileActivity;
import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    View view;
    FrameLayout edit_profile;
    CollectionReference userRef = FirebaseFirestore.getInstance().collection("Users");
    TextView name, address, mobile_no, pincide;
    ListenerRegistration listenerRegistration;
    MaterialButton logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        edit_profile = view.findViewById(R.id.edit_profile);
        name = view.findViewById(R.id.name);
        address = view.findViewById(R.id.address);
        mobile_no = view.findViewById(R.id.mobile_no);
        pincide = view.findViewById(R.id.pincode);
        logout = view.findViewById(R.id.logout);


        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                getActivity().startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginSignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listenerRegistration = userRef.document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    User user = documentSnapshot.toObject(User.class);
                    name.setText(user.getName());
                    address.setText(user.getAddress());
                    mobile_no.setText(user.getPhone_number());
                    pincide.setText(user.getPincode());


                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listenerRegistration.remove();
    }
}
