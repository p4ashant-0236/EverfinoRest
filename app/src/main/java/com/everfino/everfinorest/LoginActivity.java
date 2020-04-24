package com.everfino.everfinorest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.everfino.everfinorest.ApiConnection.Api;
import com.everfino.everfinorest.ApiConnection.ApiClient;
import com.everfino.everfinorest.Models.RestUserResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText restid, restname, username, password;
    Button btnlogin;
    TextView createnewrest;
    ProgressDialog progressDialog;
    private static Api apiService;
    AppSharedPreferences appSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        restid = findViewById(R.id.restid);
        restname = findViewById(R.id.rest_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnlogin = findViewById(R.id.btn_login);
        createnewrest = findViewById(R.id.registerrest);

        apiService = ApiClient.getClient().create(Api.class);
        appSharedPreferences = new AppSharedPreferences(this);


        createnewrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterRestActivity.class);
                startActivity(i);
                finish();
            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(true);
                progressDialog.setMessage("Validating...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                reststafflogin();

            }
        });
    }

    public void reststafflogin() {
        JsonObject inputData = new JsonObject();
        if (username.getText().length() == 0) {
            username.setError("Username is Required!");
        } else if (password.getText().length() == 0) {
            password.setError("Password is Required!");
        }else if(restid.getText().length()==0)
        {
            restid.setError("RestId is Required!");
        }else if(restname.getText().length()==0)
        {restname.setError("RestName Is Required!");}
            else {
            inputData.addProperty("username", username.getText().toString());
            inputData.addProperty("password", password.getText().toString());
            final int rest_id = Integer.parseInt(restid.getText().toString());
            Log.e("$$$$$$$$$$$$$$$", rest_id + username.getText().toString() + password.getText().toString());

            Call<RestUserResponse> call = apiService.rest_staff_login(Integer.parseInt(restid.getText().toString()), inputData);
            call.enqueue(new Callback<RestUserResponse>() {
                @Override
                public void onResponse(Call<RestUserResponse> call, Response<RestUserResponse> response) {
                    progressDialog.dismiss();

                    if (response.body().getName() == "false") {
                        Log.e("$$$$$$sadffdf", response.body().getName());
                        Toast.makeText(LoginActivity.this, "check username and password", Toast.LENGTH_LONG).show();
                    } else {
                        appSharedPreferences.setPref(rest_id, restname.getText().toString(), response.body().getUserid(), response.body().getEmail(), response.body().getRole());
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<RestUserResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
