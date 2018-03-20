package com.example.lenovo.hackathontourism;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

public class CityJaipur extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_jaipur);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //IMAGE VIEW INTENT START
        ImageView mHawaMahal = (ImageView) findViewById(R.id.imageView10);
        mHawaMahal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent hawa_mahal = new Intent(CityJaipur.this,HawaMahal.class);
                startActivity(hawa_mahal);
            }
        });

        ImageView mJalMahal = (ImageView) findViewById(R.id.imageView11);
        mJalMahal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    Intent jal_mahal = new Intent(CityJaipur.this, JalMahal.class);
                    startActivity(jal_mahal);

            }
        });

        ImageView mAlbertHallMuseum = (ImageView) findViewById(R.id.imageView12);
        mAlbertHallMuseum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent albert_hall_museum = new Intent(CityJaipur.this,AlbertHallMuseum.class);
                startActivity(albert_hall_museum);
            }
        });

        ImageView mJantarMantar = (ImageView) findViewById(R.id.imageView13);
        mJantarMantar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent jantar_mantar = new Intent(CityJaipur.this,JantarMantar.class);
                startActivity(jantar_mantar);
            }
        });
        //IMAGE VIEW INTENT END

        //TEXT VIEW INTENT START
        TextView mHawaMahalt = (TextView) findViewById(R.id.textView7);
        mHawaMahalt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent hawa_mahal = new Intent(CityJaipur.this,HawaMahal.class);
                startActivity(hawa_mahal);
            }
        });

        TextView mJalMahalt = (TextView) findViewById(R.id.textView8);
        mJalMahalt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent jal_mahal = new Intent(CityJaipur.this,JalMahal.class);
                startActivity(jal_mahal);
            }
        });

        TextView mAlbertHallMuseumt = (TextView) findViewById(R.id.textView9);
        mAlbertHallMuseumt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent albert_hall_museum = new Intent(CityJaipur.this,AlbertHallMuseum.class);
                startActivity(albert_hall_museum);
            }
        });

        TextView mJantarMantart = (TextView) findViewById(R.id.textView10);
        mJantarMantart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent jantar_mantar = new Intent(CityJaipur.this,JantarMantar.class);
                startActivity(jantar_mantar);
            }
        });
        //TEXT VIEW END

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
