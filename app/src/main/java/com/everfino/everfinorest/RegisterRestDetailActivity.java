package com.everfino.everfinorest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterRestDetailActivity extends AppCompatActivity {

    Button createmanager;
    TextView restid,restname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_rest_detail);
        createmanager=findViewById(R.id.createmangetbtn);
        restid=findViewById(R.id.restid);
        restname=findViewById(R.id.restname);
        Intent i = getIntent();
        final int rid = i.getIntExtra("restid",0);
        String rname=i.getStringExtra("restname");
        restid.setText("Rest ID :"+rid);
        restname.setText("Rest Name:"+rname);

        createmanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RegisterRestDetailActivity.this,CreateManagerAccount.class);
                i.putExtra("restid",rid);
                startActivity(i);
                finish();

            }
        });

    }
}
