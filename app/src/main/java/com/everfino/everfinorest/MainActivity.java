package com.everfino.everfinorest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.everfino.everfinorest.Fragments.LiveOrderFragment;
import com.everfino.everfinorest.Fragments.MenuFragment;
import com.everfino.everfinorest.Fragments.ProfileFragment;
import com.everfino.everfinorest.Fragments.StatisticFragment;
import com.everfino.everfinorest.Fragments.TableFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView tab_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab_menu = findViewById(R.id.tab_menu);

        tab_menu.setSelectedItemId(R.id.nav_menu);
        Fragment fragment=new MenuFragment();
        loadFragment(fragment);

        tab_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.nav_liveorder:

                        //LiveOrder

                        fragment = new LiveOrderFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.nav_table:
                        //Table

                        fragment = new TableFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.nav_menu:
                        //Menu
                        fragment = new MenuFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.nav_statistic:
                        //Statistic

                        fragment = new StatisticFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.nav_profile:
                        //Profile

                        fragment = new ProfileFragment();
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
