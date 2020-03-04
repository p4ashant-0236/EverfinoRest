package com.everfino.everfinorest.Fragments.ChefFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.everfino.everfinorest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChefProfileFragment extends Fragment {


    public ChefProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chef_profile, container, false);
    }

}
