package com.example.chatapp;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.chatapp.fragments.loginfragment;
import com.example.chatapp.fragments.signfragment;

public class viewadapterlogin extends FragmentStateAdapter {

    public viewadapterlogin(FragmentManager fragmentManager,Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 1:
                return new signfragment();
            default:
                return new loginfragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
