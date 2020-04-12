package com.example.ecommerceapp.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpFragment extends Fragment {
    TextInputLayout emailLayout;
    TextInputLayout passwordLayout;
    TextInputLayout confirmPasswordLayout;
    MaterialButton button;
    FirebaseAuth firebaseAuth;
    ConstraintLayout constraintLayout;
    ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        emailLayout = view.findViewById(R.id.name);
        passwordLayout = view.findViewById(R.id.password);
        confirmPasswordLayout = view.findViewById(R.id.confirm_password);
        button = view.findViewById(R.id.button);
        firebaseAuth = FirebaseAuth.getInstance();
        constraintLayout = view.findViewById(R.id.parent_layout);
        progressBar = view.findViewById(R.id.spin_kit);
        progressBar.setVisibility(View.INVISIBLE);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpNewUser();
            }
        });

        return view;
    }

    private void signUpNewUser() {
        String email = emailLayout.getEditText().getText().toString();
        String password = passwordLayout.getEditText().getText().toString();
        String confirmPassword = confirmPasswordLayout.getEditText().getText().toString();


        if (TextUtils.isEmpty(email)) {
            emailLayout.getEditText().setError("Enter your password");


        }
        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Enter your password");
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordLayout.setError("Enter your password");
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), "Both the password don't match", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Snackbar.make(constraintLayout, "Successfully SignIn", Snackbar.LENGTH_SHORT).show();

                    authResult.getUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Check your email for verification email", Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("mytag", e.getMessage().toString());
                }
            });

        }
    }
}
