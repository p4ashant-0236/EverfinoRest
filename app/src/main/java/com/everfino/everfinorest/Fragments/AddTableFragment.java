package com.everfino.everfinorest.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.AppSharedPreferences;
import com.everfino.everfinorest.Models.MenuList;
import com.everfino.everfinorest.Models.TableList;
import com.everfino.everfinorest.R;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTableFragment extends Fragment {


    EditText tableno, tableqr, status;
    Button newtablebtn, cancelbtn;
    private static Api apiService;
    AppSharedPreferences appSharedPreferences;
    HashMap<String, String> map;

    public AddTableFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_table, container, false);
        apiService = ApiClient.getClient().create(Api.class);
        tableno = view.findViewById(R.id.tableno);

        tableqr = view.findViewById(R.id.tableqr);
        status = view.findViewById(R.id.status);
        newtablebtn = view.findViewById(R.id.newtablebtn);
        cancelbtn = view.findViewById(R.id.cancelbtn);
        appSharedPreferences = new AppSharedPreferences(getContext());

        map = appSharedPreferences.getPref();

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new TableFragment();


                loadFragment(fragment);
            }
        });

        newtablebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject newmenuitem = new JsonObject();
                if (tableqr.getText().length() == 0) {
                    tableqr.setError("Field is Required!");
                } else if (tableno.getText().length() == 0) {
                    tableno.setError("Field is Required!");
                } else if (status.getText().length() == 0) {
                    status.setError("Field is Required!");
                } else {
                    newmenuitem.addProperty("tableqr", tableqr.getText().toString());
                    newmenuitem.addProperty("tableno", Integer.parseInt(tableno.getText().toString()));
                    newmenuitem.addProperty("status", status.getText().toString());


                    Call<TableList> call = apiService.add_Rest_Table(Integer.parseInt(map.get("restid")), newmenuitem);
                    call.enqueue(new Callback<TableList>() {
                        @Override
                        public void onResponse(Call<TableList> call, Response<TableList> response) {
                            Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_LONG).show();
                            Fragment fragment = new TableFragment();

                            loadFragment(fragment);
                        }

                        @Override
                        public void onFailure(Call<TableList> call, Throwable t) {
                            Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        return view;
    }


    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

