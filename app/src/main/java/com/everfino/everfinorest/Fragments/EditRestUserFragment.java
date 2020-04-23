package com.everfino.everfinorest.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.AppSharedPreferences;
import com.everfino.everfinorest.Models.MenuList;
import com.everfino.everfinorest.Models.RestUserResponse;
import com.everfino.everfinorest.Models.TableList;
import com.everfino.everfinorest.R;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditRestUserFragment extends Fragment {


    EditText name,password,email,mobileno;
    Spinner role;
    Button editrestuserbtn,cancelbtn;
    AppSharedPreferences appSharedPreferences;
    HashMap<String,String> map;
    private static Api apiService;
    RestUserResponse u;
    String[] restrole={"Manager","Chef","Rece"};
    public EditRestUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        u=new RestUserResponse(Integer.parseInt(getArguments().getString("userid")),getArguments().getString("name"),getArguments().getString("password"),getArguments().getString("email"),getArguments().getString("mobileno"),getArguments().getString("role"));
        Log.e("###",u.userid+u.name);
        View view= inflater.inflate(R.layout.fragment_edit_rest_user, container, false);
        apiService= ApiClient.getClient().create(Api.class);

        name=view.findViewById(R.id.editusername);
        password=view.findViewById(R.id.edituserpassword);
        email=view.findViewById(R.id.edituseremail);
        mobileno=view.findViewById(R.id.editusermobileno);
        role=view.findViewById(R.id.edituserrole);

        name.setText(u.name);
        password.setText(u.password);
        email.setText(u.email);
        mobileno.setText(u.mobileno);
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,restrole);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        role.setAdapter(aa);



        editrestuserbtn=view.findViewById(R.id.editrestuserbtn);
        cancelbtn=view.findViewById(R.id.cancelbtn);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new RestStaffManageFragment();

                loadFragment(fragment);
            }
        });

        editrestuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               u.setName(name.getText().toString());
               u.setPassword(password.getText().toString());
               u.setEmail(email.getText().toString());
               u.setMobileno(mobileno.getText().toString());
               u.setRole(role.getSelectedItem().toString());
               Log.e("#####------",u.mobileno);
                appSharedPreferences=new AppSharedPreferences(getContext());
                map=appSharedPreferences.getPref();
                Call<RestUserResponse> call=apiService.update_Rest_User(Integer.parseInt(map.get("restid")),u.userid,u);
                call.enqueue(new Callback<RestUserResponse>() {
                    @Override
                    public void onResponse(Call<RestUserResponse> call, Response<RestUserResponse> response) {
                        Toast.makeText(getContext(), "edited", Toast.LENGTH_SHORT).show();
                        Fragment fragment=new RestStaffManageFragment();

                        loadFragment(fragment);
                    }

                    @Override
                    public void onFailure(Call<RestUserResponse> call, Throwable t) {
                        Toast.makeText(getContext(), t.toString()+"Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
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
