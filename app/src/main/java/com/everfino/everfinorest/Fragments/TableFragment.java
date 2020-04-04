package com.everfino.everfinorest.Fragments;


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
import com.everfino.everfinorest.Adapter.TableAdapter;
import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.AppSharedPreferences;
import com.everfino.everfinorest.Models.MenuList;
import com.everfino.everfinorest.Models.TableList;
import com.everfino.everfinorest.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {

    RecyclerView rcv_table;
    FloatingActionButton table_add_btn;
    List<HashMap<String,String>> ls_menu=new ArrayList<>();
    AppSharedPreferences appSharedPreferences;
    TableAdapter adapter;
    EditText searchtable;
    HashMap<String,String> map;
    private static Api apiService;
    public TableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_table, container, false);
        rcv_table=view.findViewById(R.id.rcv_table);
        table_add_btn=view.findViewById(R.id.table_add_btn);
        apiService= ApiClient.getClient().create(Api.class);
        table_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new AddTableFragment();

                loadFragment(fragment);
            }
        });
        appSharedPreferences=new AppSharedPreferences(getContext());
        adapter=new TableAdapter(getContext(),ls_menu);
        searchtable=view.findViewById(R.id.searchtable);
        searchtable.addTextChangedListener(new TextWatcher() {
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
        fetch_menu();
        return view;
    }
    private void filter(String text) {

        List<HashMap<String,String>> ls=new ArrayList<>();

        for (HashMap<String,String> s : ls_menu) {
            Log.e("abcccccc",s.toString());
            if (s.toString().toLowerCase().contains(text.toLowerCase())) {
                Log.e("true", String.valueOf(s));
                ls.add(s);
            }
        }

        adapter.filterList(ls);

    }
    private void fetch_menu(){

        ls_menu.clear();
        rcv_table.setLayoutManager(new GridLayoutManager(getContext(),1));
        map=appSharedPreferences.getPref();
        Call<List<TableList>> call=apiService.get_Rest_Table(Integer.parseInt(map.get("restid")));
        call.enqueue(new Callback<List<TableList>>() {
            @Override
            public void onResponse(Call<List<TableList>> call, Response<List<TableList>> response) {
                 for(TableList item: response.body()) {

                    HashMap<String,String> map=new HashMap<>();
                    map.put("tableid",item.getTableid()+"");
                    map.put("status",item.getStatus());
                    map.put("tableno",item.getTableno()+"");
                    map.put("tableqr",item.getTableqr());
                    ls_menu.add(map);
                }

                 adapter=new TableAdapter(getContext(),ls_menu);
                rcv_table.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<TableList>> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
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
