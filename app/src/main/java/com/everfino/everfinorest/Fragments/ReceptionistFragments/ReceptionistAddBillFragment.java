package com.everfino.everfinorest.Fragments.ReceptionistFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.everfino.everfinorest.Adapter.LiveOrderAdapter;
import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.AppSharedPreferences;
import com.everfino.everfinorest.Models.Liveorder;
import com.everfino.everfinorest.Models.Order;
import com.everfino.everfinorest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceptionistAddBillFragment extends Fragment {
    AppSharedPreferences appSharedPreferences;
    HashMap<String, String> map;
    List<HashMap<String, String>> ls_liveorder = new ArrayList<>();
    EditText orderid, paymentstauts;
    Button addBillbtn, searchorder;
    RecyclerView rcv_liveorderlist;
    Order o;
    private static Api apiService;

    public ReceptionistAddBillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_receptionist_add_bill, container, false);
        orderid = view.findViewById(R.id.orderid);
        o = new Order();
        paymentstauts = view.findViewById(R.id.paymentstatus);
        rcv_liveorderlist = view.findViewById(R.id.rcv_liveorderlist);
        searchorder = view.findViewById(R.id.searchorder);
        addBillbtn = view.findViewById(R.id.addbill);
        addBillbtn.setVisibility(View.INVISIBLE);
        apiService = ApiClient.getClient().create(Api.class);
        searchorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetch_liveorder();
            }
        });
        appSharedPreferences = new AppSharedPreferences(getContext());
        addBillbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_Bill();
            }
        });
        return view;
    }

    void fetch_liveorder() {
        ls_liveorder.clear();
        rcv_liveorderlist.setLayoutManager(new GridLayoutManager(getContext(), 1));
        if(orderid.getText().length()==0)
        {
            orderid.setError("Orderid is Required!");
        }
        else {
            map = appSharedPreferences.getPref();
            Log.e("#######-----------", map.get("restid") + Integer.parseInt(orderid.getText().toString()));
            Call<List<Liveorder>> call = apiService.get_Rest_Liveorder_per_order(Integer.parseInt(map.get("restid")), Integer.parseInt(orderid.getText().toString()));
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
                        ls_liveorder.add(map);
                    }

                    LiveOrderAdapter adapter = new LiveOrderAdapter(getContext(), ls_liveorder);
                    rcv_liveorderlist.setAdapter(adapter);
                    if (ls_liveorder.size() > 0) {
                        addBillbtn.setVisibility(View.VISIBLE);
                        paymentstauts.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Liveorder>> call, Throwable t) {
                    Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    void add_Bill() {
        map = appSharedPreferences.getPref();
        Call<Order> call = apiService.get_Rest_single_order(Integer.parseInt(map.get("restid")), Integer.parseInt(orderid.getText().toString()));
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                o = response.body();
                int a = 0;
                for (HashMap<String, String> item : ls_liveorder) {
                    if (item.get("status").equals("Done")) {
                        int am = Integer.parseInt(item.get("itemprice")) * Integer.parseInt(item.get("quntity"));
                        a = a + am;
                    }
                }
                Log.e("##--", a + "" + paymentstauts.getText().toString());
                o.setAmount(a);
                o.setPaymentstatus(paymentstauts.getText().toString());
                Toast.makeText(getContext(), "Total=" + a, Toast.LENGTH_LONG);
                Call<Order> call1 = apiService.update_Rest_Order(Integer.parseInt(map.get("restid")), o.orderid, o);
                call1.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        Log.e("##--", "done");
                        Fragment fragment = new ReceptionistOrderFragment();
                        loadFragment(fragment);

                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        Log.e("##", t.getMessage());
                    }
                });

            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.e("##----", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG);
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
