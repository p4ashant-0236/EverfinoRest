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
import com.everfino.everfinorest.Models.RestUserResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateManagerAccount extends AppCompatActivity {

    EditText name, mobileno, email, password;
    Button manbtn;
    private static Api apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_manager_account);
        name = findViewById(R.id.mname);
        mobileno = findViewById(R.id.mmobileno);
        email = findViewById(R.id.memail);
        password = findViewById(R.id.mpass);
        manbtn = findViewById(R.id.mbtn);
        apiService = ApiClient.getClient().create(Api.class);

        manbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterMan();
            }
        });

    }

    void RegisterMan() {
        Intent i = getIntent();
        int rid = i.getIntExtra("restid", 0);
        JsonObject ob = new JsonObject();
        if (name.getText().length() == 0) {
            name.setError("Name is Required!");
        } else if (mobileno.getText().length() == 0) {
            mobileno.setError("Mobile No is Required!");
        } else if (email.getText().length() == 0) {
            email.setError("Email is Required!");
        } else if (password.getText().length() == 0) {
            password.setError("Password is Required!");
        } else {
            ob.addProperty("name", name.getText().toString());
            ob.addProperty("mobileno", mobileno.getText().toString());
            ob.addProperty("email", email.getText().toString());
            ob.addProperty("password", password.getText().toString());
            ob.addProperty("role", "Manager");
            Log.e("$$$$4$$0", rid + "");
            Call<RestUserResponse> call = apiService.add_Rest_User(rid, ob);
            call.enqueue(new Callback<RestUserResponse>() {
                @Override
                public void onResponse(Call<RestUserResponse> call, Response<RestUserResponse> response) {

                    if (response.body().getUserid() != 0 && response.body().getName() != "") {
                        Toast.makeText(CreateManagerAccount.this, "Successfully", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(CreateManagerAccount.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(CreateManagerAccount.this, "Try Again", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RestUserResponse> call, Throwable t) {

                }
            });
        }
    }
}
