package com.everfino.everfinorest.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.everfino.everfinorest.AppSharedPreferences;
import com.everfino.everfinorest.LoginActivity;
import com.everfino.everfinorest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    Button logoutbtn,managestaff;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_profile, container, false);
        logoutbtn=view.findViewById(R.id.logoutbtn);
        managestaff=view.findViewById(R.id.alluser);
        managestaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Fragment fragment=new RestStaffManageFragment();
                    loadFragment(fragment);
            }
        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             AppSharedPreferences appSharedPreferences=new AppSharedPreferences(getContext());
                                             appSharedPreferences.clearPref();
                                             Toast.makeText(getContext(), "Logut Successfully", Toast.LENGTH_LONG).show();
                                             Intent i=new Intent(getContext(), LoginActivity.class);
                                             startActivity(i);
                                             getActivity().finish();
                                         }
                                     }
        );
        return  view;
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
