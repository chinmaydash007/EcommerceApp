package com.example.ecommerceapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    TextInputLayout emailLayout;
    TextInputLayout passwordLayout;

    MaterialButton signInButton;
    ConstraintLayout constraintLayout;
    View view;
    SpinKitView spinKitView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        emailLayout = view.findViewById(R.id.name);
        passwordLayout = view.findViewById(R.id.password);
        signInButton = view.findViewById(R.id.button);
        firebaseAuth = FirebaseAuth.getInstance();
        constraintLayout = view.findViewById(R.id.loginLayout);
        spinKitView = view.findViewById(R.id.spin_kit);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInProcess();
            }
        });


        return view;
    }

    private void signInProcess() {
        String email = emailLayout.getEditText().getText().toString();
        String password = passwordLayout.getEditText().getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailLayout.setError("Enter your eamil");
        } else if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Enter your password");
        } else {
            spinKitView.setVisibility(View.VISIBLE);

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Log.d("mytag", "SignIn Successfully");
                    Log.d("mytag", authResult.getUser().isEmailVerified() + " ");

                    if (authResult.getUser().isEmailVerified()) {
                        spinKitView.setVisibility(View.INVISIBLE);

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        spinKitView.setVisibility(View.INVISIBLE);

                        firebaseAuth.signOut();
                        Snackbar.make(constraintLayout, "Email not verified", Snackbar.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    spinKitView.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("mytag", e.getMessage());
                }
            });
        }
    }
}
