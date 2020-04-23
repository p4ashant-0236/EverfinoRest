package com.everfino.everfinorest.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
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

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditTableFragment extends Fragment {


    EditText tableno, tableqr, status;
    Button edittablebtn, cancelbtn;
    AppSharedPreferences appSharedPreferences;
    HashMap<String, String> map;
    private static Api apiService;
    TableList m;

    public EditTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        m = new TableList(Integer.parseInt(getArguments().getString("tableid")), Integer.parseInt(getArguments().getString("tableno")), getArguments().getString("status"), getArguments().getString("tableqr"));
        Log.e("###", m.tableno + m.status);
        View view = inflater.inflate(R.layout.fragment_edit_table, container, false);
        apiService = ApiClient.getClient().create(Api.class);
        tableno = view.findViewById(R.id.etableno);
        tableqr = view.findViewById(R.id.etableqr);
        status = view.findViewById(R.id.estatus);

        Log.e("##no", "" + m.tableno);
        tableno.setText(m.tableno + "");
        status.setText(m.status);
        tableqr.setText(m.tableqr);


        edittablebtn = view.findViewById(R.id.editablebtn);
        cancelbtn = view.findViewById(R.id.ecancelbtn);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new TableFragment();

                loadFragment(fragment);
            }
        });

        edittablebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSharedPreferences = new AppSharedPreferences(getContext());
                map = appSharedPreferences.getPref();
                if (tableqr.getText().length() == 0) {
                    tableqr.setError("Field is Required!");
                } else if (tableno.getText().length() == 0) {
                    tableno.setError("Field is Required!");
                } else if (status.getText().length() == 0) {
                    status.setError("Field is Required!");
                } else {
                    m.tableqr = map.get("restid") + "_" + m.tableid + "_" + tableqr.getText().toString();
                    m.tableno = Integer.parseInt(tableno.getText().toString());
                    m.status = status.getText().toString();
                    Call<TableList> call = apiService.update_Rest_Table(Integer.parseInt(map.get("restid")), m.tableid, m);
                    call.enqueue(new Callback<TableList>() {
                        @Override
                        public void onResponse(Call<TableList> call, Response<TableList> response) {
                            Fragment fragment = new TableFragment();

                            loadFragment(fragment);
                        }

                        @Override
                        public void onFailure(Call<TableList> call, Throwable t) {
                            Toast.makeText(getContext(), t.toString() + "Try Again", Toast.LENGTH_SHORT).show();
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
