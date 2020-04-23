package com.everfino.everfinorest.Fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.AppSharedPreferences;
import com.everfino.everfinorest.MainActivity;
import com.everfino.everfinorest.Models.MenuList;
import com.everfino.everfinorest.R;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMenuFragment extends Fragment {


    EditText itemname, itemprice, itemdesc, itemtype;
    Button addmenubtn, cancelbtn;
    AppSharedPreferences appSharedPreferences;
    HashMap<String, String> map;
    private static Api apiService;

    public AddMenuFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_menu, container, false);
        apiService = ApiClient.getClient().create(Api.class);
        itemname = view.findViewById(R.id.itemname);
        itemprice = view.findViewById(R.id.itemprice);
        itemdesc = view.findViewById(R.id.itemdesc);
        itemtype = view.findViewById(R.id.itemtype);
        addmenubtn = view.findViewById(R.id.addnewbtn);
        cancelbtn = view.findViewById(R.id.cancelbtn);

        appSharedPreferences = new AppSharedPreferences(getContext());

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MenuFragment();


                loadFragment(fragment);
            }
        });

        addmenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map = appSharedPreferences.getPref();
                JsonObject newmenuitem = new JsonObject();
                if (itemname.getText().length() == 0) {
                    itemname.setError("Item Name is Required!");
                } else if (itemprice.getText().length() == 0) {
                    itemname.setError("Item Price is Required!");
                } else if (itemtype.getText().length() == 0) {
                    itemname.setError("Item Type is Required!");
                } else if (itemdesc.getText().length() == 0) {
                    itemname.setError("Item Decription is Required!");
                } else {
                    newmenuitem.addProperty("itemname", itemname.getText().toString());
                    newmenuitem.addProperty("itemprice", Integer.parseInt(itemprice.getText().toString()));
                    newmenuitem.addProperty("itemtype", itemtype.getText().toString());
                    newmenuitem.addProperty("itemdesc", itemdesc.getText().toString());

                    Call<MenuList> call = apiService.add_Rest_Menu(Integer.parseInt(map.get("restid")), newmenuitem);
                    call.enqueue(new Callback<MenuList>() {
                        @Override
                        public void onResponse(Call<MenuList> call, Response<MenuList> response) {
                            Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_LONG).show();
                            Fragment fragment = new MenuFragment();

                            loadFragment(fragment);
                        }

                        @Override
                        public void onFailure(Call<MenuList> call, Throwable t) {
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

