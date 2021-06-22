package com.example.chatapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.modelforprofile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Pattern;

public class signfragment extends Fragment {


    EditText email,password,name,confirm_password;
    Button register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signfragment, container, false);

        register=view.findViewById(R.id.register);

        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);
        confirm_password=view.findViewById(R.id.confirm_password);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String names,emails,passwords,con_passs;

                names=name.getText().toString();
                emails=email.getText().toString();
                passwords=password.getText().toString();
                con_passs=confirm_password.getText().toString();

                if (names.isEmpty()){
                    name.setError("Enter name");
                    return;
                }
                if (names.length()<4){
                    name.setError("name must contain atleast 4 characters");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emails).matches()){
                    email.setError("Enter valid eamil");
                    return;
                }
                if (passwords.isEmpty()){
                    password.setError("Enter password");
                    return;
                }
                if (passwords.length()<7){
                    password.setError("password must be greater than 7");
                    return;
                }
                if (con_passs.isEmpty()){
                    confirm_password.setError("Enter confirm password");
                    return;
                }
                if (!con_passs.equals(passwords)){
                    Toast.makeText(getContext(), "Both Passwords did not match", Toast.LENGTH_SHORT).show();
                    return;
                }


                modelforprofile profile=new modelforprofile(names,emails,passwords);

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emails,passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Profile created", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });





            }
        });


        return view;
    }
}