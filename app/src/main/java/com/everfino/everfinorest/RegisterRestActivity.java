package com.everfino.everfinorest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.Models.RegisterRestResponse;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRestActivity extends AppCompatActivity {
    EditText restname, desc, mobile, email, address, city;
    Button register;
    private static Api apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_rest);
        restname = findViewById(R.id.restname);
        desc = findViewById(R.id.desc);
        mobile = findViewById(R.id.mobileno);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        register = findViewById(R.id.registerbtn);
        apiService = ApiClient.getClient().create(Api.class);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterRest();
            }
        });
    }

    void RegisterRest() {

        JsonObject ob = new JsonObject();
        if (restname.getText().length() == 0) {
            restname.setError("RestName is Required!");
        } else if (email.getText().length() == 0) {
            email.setError("Email is Required!");
        } else if (desc.getText().length() == 0) {
            desc.setError("description is Required!");
        } else if (address.getText().length() == 0) {
            address.setError("Address is Required!");
        } else if (city.getText().length() == 0) {
            city.setError("City is Required!");
        } else {
            ob.addProperty("restname", restname.getText().toString());
            ob.addProperty("email", email.getText().toString());
            ob.addProperty("mobileno", mobile.getText().toString());
            ob.addProperty("restdesc", desc.getText().toString());
            ob.addProperty("address", address.getText().toString());
            ob.addProperty("city", city.getText().toString());
            ob.addProperty("status", "Activate");
            Call<RegisterRestResponse> call = apiService.register_rest(ob);
            Log.e("$$$$$$$$", "---------------------------------------------");
            call.enqueue(new Callback<RegisterRestResponse>() {
                @Override
                public void onResponse(Call<RegisterRestResponse> call, Response<RegisterRestResponse> response) {
                    if (response.body().getRestid() != 0 && response.body().getRestname() != "") {

                        Toast.makeText(RegisterRestActivity.this, "Successfull", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(RegisterRestActivity.this, RegisterRestDetailActivity.class);
                        i.putExtra("restid", response.body().getRestid());
                        i.putExtra("restname", response.body().getRestname());
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(RegisterRestActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterRestResponse> call, Throwable t) {
                    Log.e("$$$$$$$", t.getMessage());
                }
            });

        }
    }
}
