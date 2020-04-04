package com.everfino.everfinorest.Fragments.ReceptionistFragments;



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

import com.everfino.everfinorest.Adapter.OrderAdapter;
import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.AppSharedPreferences;

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
public class ReceptionistOrderFragment extends Fragment {
    AppSharedPreferences appSharedPreferences;
    HashMap<String,String> map;
    RecyclerView rcv_order;
    EditText receorder;
    OrderAdapter adapter;
    List<HashMap<String, String>> ls_order = new ArrayList<>();
    private static Api apiService;

    public ReceptionistOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        final View view = inflater.inflate(R.layout.fragment_receptionist_order, container, false);
        rcv_order = view.findViewById(R.id.rcv_order);

        apiService = ApiClient.getClient().create(Api.class);
        appSharedPreferences=new AppSharedPreferences(getContext());
        receorder=view.findViewById(R.id.searchreceptipnistorder);
        receorder.addTextChangedListener(new TextWatcher() {
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

        fetch_order();
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

    private void fetch_order() {

        ls_order.clear();
        rcv_order.setLayoutManager(new GridLayoutManager(getContext(), 1));
        map=appSharedPreferences.getPref();
        Call<List<Order>> call = apiService.get_Rest_order(Integer.parseInt(map.get("restid")));
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                  for (Order item : response.body()) {

                    HashMap<String, String> map = new HashMap<>();
                    map.put("orderid", item.getOrderid() + "");
                    map.put("userid", item.getUserid() + "");
                    map.put("amount", item.getAmount() + "");
                    map.put("email", item.getEmail() + "");
                    map.put("name", item.getName() + "");
                    map.put("paymentstatus", item.getPaymentstatus() + "");
                    map.put("order_date", item.getOrder_date() + "");
                    Log.e("####", item.getEmail());
                    ls_order.add(map);
                }

                 adapter = new OrderAdapter(getContext(), ls_order);
                rcv_order.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
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
