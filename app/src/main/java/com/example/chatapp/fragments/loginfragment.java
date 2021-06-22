package com.example.chatapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatapp.R;
import com.example.chatapp.dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginfragment extends Fragment {

    EditText email,password;
    Button login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_loginfragment, container, false);

        login=view.findViewById(R.id.login);

        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()){
                    email.setError("Enter email");
                    return;
                }
                if (password.getText().toString().isEmpty()){
                    email.setError("Enter password");
                    return;
                }

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(getContext(), dashboard.class));
                        }
                    }
                });

            }
        });


        return view;
    }
}