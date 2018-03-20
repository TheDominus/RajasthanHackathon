package com.example.lenovo.hackathontourism;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

public class CityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView mJaipur = (ImageView) findViewById(R.id.imageView3);
        mJaipur.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent jaipur = new Intent(CityActivity.this,CityJaipur.class);
                startActivity(jaipur);
            }
        });


    }

    private void setSupportActionBar(Toolbar toolbar) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
