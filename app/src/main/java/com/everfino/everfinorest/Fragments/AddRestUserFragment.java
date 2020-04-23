package com.everfino.everfinorest.Fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.AppSharedPreferences;
import com.everfino.everfinorest.MainActivity;
import com.everfino.everfinorest.Models.MenuList;
import com.everfino.everfinorest.Models.RestUserResponse;
import com.everfino.everfinorest.R;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRestUserFragment extends Fragment {


    EditText name, email, password, mobileno;
    Spinner role;
    Button addrestuser, cancelbtn;
    AppSharedPreferences appSharedPreferences;
    HashMap<String, String> map;
    String[] restrole = {"Manager", "Chef", "Rece"};
    private static Api apiService;

    public AddRestUserFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_rest_user, container, false);
        apiService = ApiClient.getClient().create(Api.class);
        name = view.findViewById(R.id.username);
        password = view.findViewById(R.id.userpassword);
        email = view.findViewById(R.id.useremail);
        mobileno = view.findViewById(R.id.usermobileno);
        role = view.findViewById(R.id.userrole);
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,restrole);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        role.setAdapter(aa);

        cancelbtn = view.findViewById(R.id.cancelbtn);
        addrestuser = view.findViewById(R.id.restuserbtn);
        appSharedPreferences = new AppSharedPreferences(getContext());

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RestStaffManageFragment();
                loadFragment(fragment);
            }
        });

        addrestuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map = appSharedPreferences.getPref();
                JsonObject newitem = new JsonObject();
                if (name.getText().length() == 0) {
                    name.setError("Name is Required!");
                } else if (password.getText().length() == 0) {
                    password.setError("Password is Required!");
                } else if (email.getText().length() == 0) {
                    email.setError("Email is Required!");
                } else if (mobileno.getText().length() == 0) {
                    mobileno.setError("Mobile No is Required!");
                } else {
                    newitem.addProperty("name", name.getText().toString());
                    newitem.addProperty("password", password.getText().toString());
                    newitem.addProperty("email", email.getText().toString());
                    newitem.addProperty("mobileno", mobileno.getText().toString());
                    newitem.addProperty("role", role.getSelectedItem().toString());
                    ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, restrole);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    role.setAdapter(aa);
                    Call<RestUserResponse> call = apiService.add_Rest_User(Integer.parseInt(map.get("restid")), newitem);
                    call.enqueue(new Callback<RestUserResponse>() {
                        @Override
                        public void onResponse(Call<RestUserResponse> call, Response<RestUserResponse> response) {
                            Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_LONG).show();
                            Fragment fragment = new RestStaffManageFragment();

                            loadFragment(fragment);
                        }

                        @Override
                        public void onFailure(Call<RestUserResponse> call, Throwable t) {
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

