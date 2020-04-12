package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecommerceapp.Model.User;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
    CollectionReference userRef;
    TextInputLayout name, address, pincode, phoneNumber;
    Button submit;
    SpinKitView progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userRef = FirebaseFirestore.getInstance().collection("Users");

        name = findViewById(R.id.name);
        address = findViewById(R.id.Address);
        pincode = findViewById(R.id.pin_code);
        phoneNumber = findViewById(R.id.phone_number);
        submit = findViewById(R.id.submit);
        progress = findViewById(R.id.progress);

        getUserProfile();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = name.getEditText().getText().toString();
                String user_address = address.getEditText().getText().toString();
                String user_pincode = pincode.getEditText().getText().toString();
                String user_phone = phoneNumber.getEditText().getText().toString();
                if (TextUtils.isEmpty(user_name)) {
                    name.setError("Enter the name");
                } else if (TextUtils.isEmpty(user_address)) {
                    name.setError("Enter the address");
                } else if (TextUtils.isEmpty(user_phone)) {
                    name.setError("Enter the phone");
                } else if (TextUtils.isEmpty(user_pincode)) {
                    name.setError("Enter the pincode");
                } else {
                    progress.setVisibility(View.VISIBLE);
                    User user = new User(user_name, user_address, user_pincode, user_phone);

                    userRef.document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progress.setVisibility(View.INVISIBLE);

                            Toast.makeText(ProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progress.setVisibility(View.INVISIBLE);

                            Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("mytag", e.getMessage());
                        }
                    });

                }
            }
        });

    }

    private void getUserProfile() {
        userRef.document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    User user = documentSnapshot.toObject(User.class);
                    name.getEditText().setText(user.getName());
                    address.getEditText().setText(user.getAddress());
                    pincode.getEditText().setText(user.getPincode());
                    phoneNumber.getEditText().setText(user.getPhone_number());

                }
            }
        });

    }
}
