package com.example.lenovo.hackathontourism;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lenovo.hackathontourism.data.TicketDBHelper;

public class BookingsActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    public static final String SHARED_PREF_NAME="tourist";
    public static final String EMAIL_SHARED_PREF="email";
    public static final String LOGGEDIN_SHARED_PREF="loggedin";
    public static final String USERID_SHARED_PREF="userid";
    private boolean loggedIn=false;
    private String userid = null;
    TicketDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF, false);
        if(loggedIn == false)
        {
            Toast.makeText(BookingsActivity.this,"Login to view Bookings",Toast.LENGTH_LONG).show();
            Intent main = new Intent(BookingsActivity.this,LoginActivity.class);
            startActivity(main);
        }

        userid = sharedPreferences.getString(USERID_SHARED_PREF,null);
        dbHelper = TicketDBHelper.getInstance(BookingsActivity.this);
        mDb = dbHelper.getReadableDatabase();



    }
}
