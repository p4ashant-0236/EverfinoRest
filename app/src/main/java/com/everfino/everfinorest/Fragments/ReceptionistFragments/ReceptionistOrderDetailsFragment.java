package com.everfino.everfinorest.Fragments.ReceptionistFragments;


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

import com.everfino.everfinorest.Adapter.LiveOrderAdapter;
import com.everfino.everfinorest.Adapter.MenuAdapter;
import com.everfino.everfinorest.Adapter.OrderItemAdapter;
import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.AppSharedPreferences;
import com.everfino.everfinorest.ChefActivity;
import com.everfino.everfinorest.LoginActivity;
import com.everfino.everfinorest.MainActivity;
import com.everfino.everfinorest.Models.Liveorder;
import com.everfino.everfinorest.Models.MenuList;
import com.everfino.everfinorest.Models.OrderItem;
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
public class ReceptionistOrderDetailsFragment extends Fragment {
    AppSharedPreferences appSharedPreferences;
    HashMap<String,String> map;
    RecyclerView rcv_orderdetails;
    OrderItemAdapter adapter;
    List<HashMap<String, String>> ls_orderitem = new ArrayList<>();
    private static Api apiService;
    EditText receorderdetails;

    public ReceptionistOrderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        final View view = inflater.inflate(R.layout.fragment_receptionist_order_details, container, false);
        rcv_orderdetails = view.findViewById(R.id.rcv_orderdetails);

        apiService = ApiClient.getClient().create(Api.class);
        appSharedPreferences=new AppSharedPreferences(getContext());

        receorderdetails=view.findViewById(R.id.searchrecertionistorderdetails);
        receorderdetails.addTextChangedListener(new TextWatcher() {
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
        fetch_orderdetails();
        return view;
    }
    private void filter(String text) {

        List<HashMap<String,String>> ls=new ArrayList<>();

        for (HashMap<String,String> s : ls_orderitem) {
            Log.e("abcccccc",s.toString());
            if (s.toString().toLowerCase().contains(text.toLowerCase())) {
                Log.e("true", String.valueOf(s));
                ls.add(s);
            }
        }

        adapter.filterList(ls);

    }
    private void fetch_orderdetails() {

        ls_orderitem.clear();
        rcv_orderdetails.setLayoutManager(new GridLayoutManager(getContext(), 1));
        map=appSharedPreferences.getPref();
        Call<List<OrderItem>> call = apiService.get_Rest_single_order_orderitem(Integer.parseInt(map.get("restid")),getArguments().getInt("orderid"));
        call.enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                       for (OrderItem item : response.body()) {

                    HashMap<String, String> map = new HashMap<>();
                    map.put("orderid", item.getOrderid() + "");
                    map.put("itemid", item.getItemid() + "");
                    map.put("itemprice", item.getItemprice() + "");
                    map.put("quntity", item.getQuntity() + "");
                    map.put("remark", item.getRemark() + "");
                    map.put("itemname", item.getItemname() + "");
                    ls_orderitem.add(map);
                }
                       adapter = new OrderItemAdapter(getContext(), ls_orderitem);
                rcv_orderdetails.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {
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
