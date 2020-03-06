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
import com.everfino.everfinorest.Fragments.ReceptionistFragments.ReceptionistAddBillFragment;
import com.everfino.everfinorest.Fragments.ReceptionistFragments.ReceptionistOrderFragment;
import com.everfino.everfinorest.Fragments.ReceptionistFragments.ReceptionistProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class ReceptionistActivity extends AppCompatActivity {

    BottomNavigationView tab_menu_receptionist;
    AppSharedPreferences appSharedPreferences;
    HashMap<String,String> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_receptionist);
        appSharedPreferences = new AppSharedPreferences(this);
        map = appSharedPreferences.getPref();

        tab_menu_receptionist = findViewById(R.id.tab_menu_receptionist);

        tab_menu_receptionist.setSelectedItemId(R.id.nav_orders);
        Fragment fragment=new ReceptionistOrderFragment();
        loadFragment(fragment);

        tab_menu_receptionist.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.nav_orders:

                        //LiveOrder

                        fragment = new ReceptionistOrderFragment();
                        loadFragment(fragment);
                        break;

                    case R.id.nav_logout:
                        //Menu
                        AppSharedPreferences appSharedPreferences=new AppSharedPreferences(ReceptionistActivity.this);
                        appSharedPreferences.clearPref();
                        Intent i=new Intent(ReceptionistActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                        break;

                    case R.id.nav_profile:
                        //Profile

                        fragment = new ReceptionistProfileFragment();
                        loadFragment(fragment);
                        break;

                    case R.id.nav_addbills:
                        //Profile

                        fragment = new ReceptionistAddBillFragment();
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
