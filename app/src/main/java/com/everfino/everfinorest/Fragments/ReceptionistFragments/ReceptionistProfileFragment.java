package com.everfino.everfinorest.Fragments.ReceptionistFragments;



import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.AppSharedPreferences;

import com.everfino.everfinorest.Models.RestUserResponse;
import com.everfino.everfinorest.R;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceptionistProfileFragment extends Fragment {

    Button Edituserbtn,editinfobtn;
    TextView email,name,mobile;
    EditText ename,epassword,eemail,emobileno;
    LinearLayout details,edit;
    RestUserResponse r;
    private static Api apiService;
    AppSharedPreferences appSharedPreferences;
    HashMap<String,String> map;
    public ReceptionistProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_chef_profile, container, false);
        email=view.findViewById(R.id.email);
        name=view.findViewById(R.id.name);
        mobile=view.findViewById(R.id.mobile);
        editinfobtn=view.findViewById(R.id.editinfo);
        ename=view.findViewById(R.id.ename);
        eemail=view.findViewById(R.id.eemail);
        epassword=view.findViewById(R.id.epassword);
        emobileno=view.findViewById(R.id.emobileno);
        Edituserbtn=view.findViewById(R.id.editbtn);
        details=view.findViewById(R.id.userdatails);
        edit=view.findViewById(R.id.edituserdetails);

        apiService= ApiClient.getClient().create(Api.class);
        appSharedPreferences=new AppSharedPreferences(getContext());

        load_data();
        edit.setVisibility(View.GONE);

        editinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);

            }
        });



        Edituserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_data();
            }
        });






        return  view;
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void load_data()
    {
        map=appSharedPreferences.getPref();
        Call<RestUserResponse> call=apiService.get_Rest_single_User(Integer.parseInt(map.get("restid")),Integer.parseInt(map.get("userid")));
        call.enqueue(new Callback<RestUserResponse>() {
            @Override
            public void onResponse(Call<RestUserResponse> call, Response<RestUserResponse> response) {
                name.setText(response.body().getName());
                email.setText(response.body().getEmail());
                mobile.setText(response.body().getMobileno());

                ename.setText(response.body().getName());
                eemail.setText(response.body().getEmail());
                emobileno.setText(response.body().getMobileno());
                epassword.setText(response.body().getPassword());

                r=response.body();
            }

            @Override
            public void onFailure(Call<RestUserResponse> call, Throwable t) {

            }
        });
    }



    public  void update_data()
    {
        map=appSharedPreferences.getPref();
        if (emobileno.getText().length() == 0) {
            emobileno.setError("Mobile No is Required!");
        } else if (eemail.getText().length() == 0) {
            eemail.setError("Email is Required!");
        } else if (ename.getText().length() == 0) {
            ename.setError("Name is Required!");
        } else if (epassword.getText().length() == 0) {
            epassword.setError("Password is Required!");
        } else {
            r.setMobileno(emobileno.getText().toString());
            r.setName(ename.getText().toString());
            r.setPassword(epassword.getText().toString());
            r.setEmail(eemail.getText().toString());

            Call<RestUserResponse> call = apiService.update_Rest_User(Integer.parseInt(map.get("restid")), r.userid, r);
            call.enqueue(new Callback<RestUserResponse>() {
                @Override
                public void onResponse(Call<RestUserResponse> call, Response<RestUserResponse> response) {
                    ReceptionistProfileFragment profileFragment = new ReceptionistProfileFragment();
                    loadFragment(profileFragment);
                }

                @Override
                public void onFailure(Call<RestUserResponse> call, Throwable t) {

                }
            });
        }
    }

}
