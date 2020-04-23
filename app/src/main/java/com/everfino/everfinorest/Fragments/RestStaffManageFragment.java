package com.everfino.everfinorest.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.everfino.everfinorest.Adapter.MenuAdapter;
import com.everfino.everfinorest.Adapter.RestUserAdapter;
import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.AppSharedPreferences;
import com.everfino.everfinorest.ChefActivity;
import com.everfino.everfinorest.LoginActivity;
import com.everfino.everfinorest.MainActivity;
import com.everfino.everfinorest.Models.MenuList;
import com.everfino.everfinorest.Models.RestUserResponse;
import com.everfino.everfinorest.R;
import com.everfino.everfinorest.ReceptionistActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestStaffManageFragment extends Fragment {
    AppSharedPreferences appSharedPreferences;
    HashMap<String, String> map;
    RecyclerView rcv_restuser;
    FloatingActionButton restuser_add_btn;
    RestUserAdapter adapter;
    EditText searchstaff;
    List<HashMap<String, String>> ls_menu = new ArrayList<>();
    private static Api apiService;

    public RestStaffManageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_rest_staff_manage, container, false);
        rcv_restuser = view.findViewById(R.id.rcv_restuser);
        restuser_add_btn = view.findViewById(R.id.restuser_add_btn);
        apiService = ApiClient.getClient().create(Api.class);
        appSharedPreferences = new AppSharedPreferences(getContext());
        restuser_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddRestUserFragment();

                loadFragment(fragment);
            }
        });
        searchstaff = view.findViewById(R.id.searchstaffmanage);
        searchstaff.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fetch_user();
        restuser_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddRestUserFragment();
                loadFragment(fragment);
            }
        });
        return view;
    }

    private void filter(String text) {

        List<HashMap<String, String>> ls = new ArrayList<>();

        for (HashMap<String, String> s : ls_menu) {
            Log.e("abcccccc", s.toString());
            if (s.toString().toLowerCase().contains(text.toLowerCase())) {
                Log.e("true", String.valueOf(s));
                ls.add(s);
            }
        }

        adapter.filterList(ls);

    }

    private void fetch_user() {

        ls_menu.clear();
        rcv_restuser.setLayoutManager(new GridLayoutManager(getContext(), 1));
        map = appSharedPreferences.getPref();
        Call<List<RestUserResponse>> call = apiService.get_Rest_User(Integer.parseInt(map.get("restid")));
        call.enqueue(new Callback<List<RestUserResponse>>() {
            @Override
            public void onResponse(Call<List<RestUserResponse>> call, Response<List<RestUserResponse>> response) {
                for (RestUserResponse item : response.body()) {
                    if (item.userid != Integer.parseInt(map.get("restid"))) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("userid", item.getUserid() + "");
                        map.put("name", item.getName());
                        map.put("password", item.getPassword() + "");
                        map.put("email", item.getEmail());
                        map.put("mobileno", item.getMobileno());
                        map.put("role", item.getRole());
                        Log.e("####", item.getName());
                        ls_menu.add(map);
                    }
                }

                adapter = new RestUserAdapter(getContext(), ls_menu);
                rcv_restuser.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<RestUserResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
