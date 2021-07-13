package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chatapp.adapters.usersadapter;
import com.example.chatapp.models.modelusers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;

    RecyclerView recyclerView;
    usersadapter adapter;
    List<modelusers> list;

    ProgressBar progressBar;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar=findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.recyclerview);
        swipeRefreshLayout=findViewById(R.id.swipe);
        list=new ArrayList<>();

        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog=new Dialog(dashboard.this,R.style.Theme_MaterialComponents_DayNight_NoActionBar);
                dialog.setContentView(R.layout.dialog_signout_layout);

                Button no,yes;
                no=dialog.findViewById(R.id.no);
                yes=dialog.findViewById(R.id.yes);

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(dashboard.this,MainActivity.class));
                        finish();
                    }
                });

                dialog.show();


            }
        });

        showdata();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list=new ArrayList<>();
                showdata();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    private void showdata() {

        progressBar.setVisibility(View.VISIBLE);

        list=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    modelusers modelusers=snapshot1.getValue(modelusers.class);
                    list.add(modelusers);
                }

                recyclerView.hasFixedSize();
                recyclerView.setLayoutManager(new LinearLayoutManager(dashboard.this));
                adapter=new usersadapter(list,dashboard.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(dashboard.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}