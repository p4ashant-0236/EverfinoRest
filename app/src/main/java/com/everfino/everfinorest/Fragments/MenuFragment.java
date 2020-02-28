package com.everfino.everfinorest.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.everfino.everfinorest.Adapter.MenuAdapter;
import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.AppSharedPreferences;
import com.everfino.everfinorest.ChefActivity;
import com.everfino.everfinorest.LoginActivity;
import com.everfino.everfinorest.MainActivity;
import com.everfino.everfinorest.Models.MenuList;
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
public class MenuFragment extends Fragment {
    AppSharedPreferences appSharedPreferences;
    HashMap<String,String> map;
    RecyclerView rcv_menu;
    FloatingActionButton menu_add_btn;
    List<HashMap<String, String>> ls_menu = new ArrayList<>();
    private static Api apiService;



    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        final View view = inflater.inflate(R.layout.fragment_menu, container, false);
        rcv_menu = view.findViewById(R.id.rcv_menu);
        menu_add_btn = view.findViewById(R.id.menu_add_btn);
        apiService = ApiClient.getClient().create(Api.class);
        menu_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddMenuFragment();

                loadFragment(fragment);
            }
        });
        fetch_menu();
        return view;
    }

    private void fetch_menu() {

        ls_menu.clear();
        rcv_menu.setLayoutManager(new GridLayoutManager(getContext(), 1));

        Call<List<MenuList>> call = apiService.get_Rest_Menu();
        call.enqueue(new Callback<List<MenuList>>() {
            @Override
            public void onResponse(Call<List<MenuList>> call, Response<List<MenuList>> response) {
                Toast.makeText(getContext(), "" + response, Toast.LENGTH_SHORT).show();
                for (MenuList item : response.body()) {

                    HashMap<String, String> map = new HashMap<>();
                    map.put("itemid", item.getItemid() + "");
                    map.put("itemname", item.getItemname());
                    map.put("itemprice", item.getItemprice() + "");
                    map.put("itemtype", item.getItemtype());
                    map.put("itemdesc", item.getItemdesc());
                    Log.e("####", item.getItemname());
                    ls_menu.add(map);
                }

                MenuAdapter adapter = new MenuAdapter(getContext(), ls_menu);
                rcv_menu.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<MenuList>> call, Throwable t) {
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
