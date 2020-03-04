package com.everfino.everfinorest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.everfino.everfinorest.Fragments.ChefFragments.ChefLiveOrderFragment;
import com.everfino.everfinorest.Fragments.ChefFragments.ChefProfileFragment;
import com.everfino.everfinorest.Fragments.MenuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class ChefActivity extends AppCompatActivity {

    BottomNavigationView tab_menu_chef;
    AppSharedPreferences appSharedPreferences;
    HashMap<String,String> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chef);
        appSharedPreferences = new AppSharedPreferences(this);
        map = appSharedPreferences.getPref();

        tab_menu_chef = findViewById(R.id.tab_menu_chef);

        tab_menu_chef.setSelectedItemId(R.id.nav_liveorder);
        Fragment fragment=new ChefLiveOrderFragment();
        loadFragment(fragment);

        tab_menu_chef.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.nav_liveorder:

                        //LiveOrder

                        fragment = new ChefLiveOrderFragment();
                        loadFragment(fragment);
                        break;

                    case R.id.nav_logout:
                        //Menu
                        AppSharedPreferences appSharedPreferences=new AppSharedPreferences(ChefActivity.this);
                        appSharedPreferences.clearPref();
                        Intent i=new Intent(ChefActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                        break;

                    case R.id.nav_profile:
                        //Profile

                        fragment = new ChefProfileFragment();
                        loadFragment(fragment);
                        break;
                }
                return true;
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }







}
