package com.everfino.everfinorest.Fragments.ChefFragments;


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

import com.everfino.everfinorest.Adapter.ChefLiveOrderAdapter;
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
public class ChefLiveOrderFragment extends Fragment {
    AppSharedPreferences appSharedPreferences;
    HashMap<String,String> map;
    RecyclerView rcv_liveorder;
    EditText chefliveorder;
    ChefLiveOrderAdapter adapter;
    List<HashMap<String, String>> ls_order = new ArrayList<>();
    private static Api apiService;

    public ChefLiveOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        final View view = inflater.inflate(R.layout.fragment_chef_live_order, container, false);
        rcv_liveorder = view.findViewById(R.id.rcv_liveorder);

        apiService = ApiClient.getClient().create(Api.class);
        appSharedPreferences=new AppSharedPreferences(getContext());
        chefliveorder=view.findViewById(R.id.searchliveorderchef);

        chefliveorder.addTextChangedListener(new TextWatcher() {
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

        fetch_liveorder();
        return view;
    }
    private void filter(String text) {

        List<HashMap<String,String>> ls=new ArrayList<>();

        for (HashMap<String,String> s : ls_order) {
            Log.e("abcccccc",s.toString());
            if (s.toString().toLowerCase().contains(text.toLowerCase())) {
                Log.e("true", String.valueOf(s));
                ls.add(s);
            }
        }

        adapter.filterList(ls);

    }

    private void fetch_liveorder() {

        ls_order.clear();
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

                    ls_order.add(map);
                }

                 adapter = new ChefLiveOrderAdapter(getContext(), ls_order);
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
