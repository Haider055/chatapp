package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chatapp.adapters.viewadapterlogin;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem tabItem1,tabItem2;
    ViewPager2 viewPager2;
    viewadapterlogin adapter;

    FirebaseAuth mauth;

    @Override
    public void onBackPressed() {

        if (tabLayout.getSelectedTabPosition()==1){
            tabLayout.selectTab(tabLayout.getTabAt(0));
        }
        else{
        super.onBackPressed();
        finishAffinity();
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mauth.getCurrentUser()!=null){
            Toast.makeText(this,"Already logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,dashboard.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mauth=FirebaseAuth.getInstance();

            tabLayout = findViewById(R.id.tab);
            tabItem1 = findViewById(R.id.tab1);
            tabItem2 = findViewById(R.id.tab2);
            viewPager2 = findViewById(R.id.pager);

            FragmentManager fm = getSupportFragmentManager();
            adapter = new viewadapterlogin(fm, getLifecycle());
            viewPager2.setAdapter(adapter);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager2.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {

                    super.onPageSelected(position);
                    tabLayout.selectTab(tabLayout.getTabAt(position));
                }
            });

    }

}