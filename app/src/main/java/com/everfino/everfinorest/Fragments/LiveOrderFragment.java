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

import com.everfino.everfinorest.Adapter.LiveOrderAdapter;
import com.everfino.everfinorest.Adapter.MenuAdapter;
import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.AppSharedPreferences;
import com.everfino.everfinorest.ChefActivity;
import com.everfino.everfinorest.LoginActivity;
import com.everfino.everfinorest.MainActivity;
import com.everfino.everfinorest.Models.Liveorder;
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
public class LiveOrderFragment extends Fragment {
    AppSharedPreferences appSharedPreferences;
    HashMap<String,String> map;
    RecyclerView rcv_liveorder;

    List<HashMap<String, String>> ls_menu = new ArrayList<>();
    private static Api apiService;

    public LiveOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        final View view = inflater.inflate(R.layout.fragment_live_order, container, false);
        rcv_liveorder = view.findViewById(R.id.rcv_liveorder);

        apiService = ApiClient.getClient().create(Api.class);
        appSharedPreferences=new AppSharedPreferences(getContext());


        fetch_liveorder();
        return view;
    }

    private void fetch_liveorder() {

        ls_menu.clear();
        rcv_liveorder.setLayoutManager(new GridLayoutManager(getContext(), 1));
        map=appSharedPreferences.getPref();
        Call<List<Liveorder>> call = apiService.get_Rest_Liveorder(Integer.parseInt(map.get("restid")));
        call.enqueue(new Callback<List<Liveorder>>() {
            @Override
            public void onResponse(Call<List<Liveorder>> call, Response<List<Liveorder>> response) {

                for (Liveorder item : response.body()) {


                    HashMap<String, String> map = new HashMap<>();
                    map.put("liveid", item.getLiveid() + "");
                    map.put("orderid", item.getOrderid() + "");
                    map.put("tableid", item.getTableid() + "");
                    map.put("itemid", item.getItemid() + "");
                    map.put("itemprice", item.getItemprice() + "");
                    map.put("userid", item.getUserid() + "");
                    map.put("quntity", item.getQuntity() + "");
                    map.put("itemname", item.getItemname());
                    map.put("status", item.getStatus());
                    map.put("order_date", item.getOrder_date().toString());

                    ls_menu.add(map);
                }

                LiveOrderAdapter adapter = new LiveOrderAdapter(getContext(), ls_menu);
                rcv_liveorder.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Liveorder>> call, Throwable t) {
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
