package com.example.lenovo.hackathontourism;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String SHARED_PREF_NAME="tourist";
    public static final String EMAIL_SHARED_PREF="email";
    public static final String LOGGEDIN_SHARED_PREF="loggedin";
    public static final String NAME_SHARED_PREF="name";
    private boolean loggedIn=false;
    private String email = null;
    private String name = null;
    NavigationView navigationView;
    Menu nav_Menu;
    TextView navUsername,navEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF, false);
        email = sharedPreferences.getString(EMAIL_SHARED_PREF,null);
        name = sharedPreferences.getString(NAME_SHARED_PREF,null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nav_Menu = navigationView.getMenu();
        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.head_login);
        navEmail = (TextView) headerView.findViewById(R.id.head_email);

        updateDrawer();

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    FirstActivity.this);

            alertDialogBuilder.setTitle("Exit");
            alertDialogBuilder
                    .setMessage("Do you really want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            ActivityCompat.finishAffinity(FirstActivity.this);
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_places_to_visit)
        {
            Intent places_to_visit = new Intent(FirstActivity.this, CityActivity.class);
            startActivity(places_to_visit);
        }
        else if (id == R.id.nav_book_tickets)
        {
            Intent book_tickets = new Intent(FirstActivity.this, QRCodeGeneration.class);
            startActivity(book_tickets);
        }
        else if(id == R.id.nav_logout)
        {
            doLogout();
        }
        else if(id == R.id.nav_login)
        {
            doLogin();
        }
        else if(id == R.id.nav_my_bookings)
        {
            Intent my_bookings = new Intent(FirstActivity.this,BookingsActivity.class);
            startActivity(my_bookings);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void doLogin()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent login_activity = new Intent(FirstActivity.this,LoginActivity.class);
        login_activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        login_activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login_activity);
    }

    private void updateDrawer()
    {
        if(loggedIn){
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            navUsername.setText(name);
            navEmail.setText(email);
            navEmail.setVisibility(View.VISIBLE);
        }
        else {
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            navUsername.setText("Not Logged in!");
            navEmail.setText("");
            navEmail.setVisibility(View.GONE);
        }
    }

    private void doLogout()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGGEDIN_SHARED_PREF,false);
        editor.apply();
        Intent new_activity = new Intent(FirstActivity.this,FirstActivity.class);
        new_activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        new_activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(new_activity);
    }

}
