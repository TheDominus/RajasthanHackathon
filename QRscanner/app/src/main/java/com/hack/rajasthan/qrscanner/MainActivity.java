package com.hack.rajasthan.qrscanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonScan;
    private TextView userName,monumentName,date,ticketID,quantity;
    //private final static String REQUEST_URL = "https://scienceakk.000webhostapp.com/php/qrscanner.php";
    private final static String REQUEST_URL = "http://139.59.10.59/qrscanner.php";
    private final static String USER_ID = "userid";
    private final static String MONUMENT_ID = "monumentid";
    private final static String DATE = "date";
    private final static String QUANTITY = "quantity";
    private final static String RANDOM = "random";
    private final static String TICKET_ID = "ticketid" ;

    private String random;
    private String userId;
    private String monumentId;
    private String ticketDate;
    private String ticketId;
    private String quan;
    private String suserName;
    private String smonumentName;

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonScan = (Button) findViewById(R.id.buttonScan);
        userName = (TextView) findViewById(R.id.UName);
        monumentName = (TextView) findViewById(R.id.monument);
        date = (TextView) findViewById(R.id.date);
        ticketID = (TextView) findViewById(R.id.TID);
        quantity = (TextView) findViewById(R.id.tickets);

        qrScan = new IntentIntegrator(this);
        buttonScan.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        suserName = "";
        smonumentName = "";
        ticketDate = "";
        ticketId = "";
        quan = "";
        userName.setText(suserName);
        monumentName.setText(smonumentName);
        quantity.setText(quan);
        date.setText(ticketDate);
        ticketID.setText(ticketId);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Tickets could not be Scanned", Toast.LENGTH_LONG).show();
            } else try {
                JSONObject obj = new JSONObject(result.getContents());

                random = obj.getString("random");
                userId = obj.getString("userid");
                monumentId = obj.getString("monumentid");
                ticketDate = obj.getString("date");
                ticketId = obj.getString("ticketid");
                quan = obj.getString("quantity");

                readData();

            } catch (JSONException e) {
                Toast.makeText(this, "Error occurred try Again", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onClick(View view) {
        qrScan.initiateScan();
    }

    public void readData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            String message = jsonObject.getString("message");
                            if(message.equals("success")) {
                                suserName = jsonObject.getString("firstname");
                                smonumentName = jsonObject.getString("monument");
                                monumentName.setText(smonumentName);
                                userName.setText(suserName);
                                date.setText(ticketDate);
                                ticketID.setText(ticketId);
                                quantity.setText(quan);
                                Toast.makeText(MainActivity.this, "Ticket Verified", Toast.LENGTH_SHORT).show();
                            }
                            else if(message.equals("failure")){
                                Toast.makeText(MainActivity.this, "Incorrect Ticket", Toast.LENGTH_SHORT).show();
                            }
                            else if(message.equals("scanned")) {
                                suserName = jsonObject.getString("firstname");
                                smonumentName = jsonObject.getString("monument");
                                monumentName.setText(smonumentName);
                                userName.setText(suserName);
                                date.setText(ticketDate);
                                ticketID.setText(ticketId);
                                quantity.setText(quan);
                                Toast.makeText(MainActivity.this, "Ticket was already scanned", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this,"Volley Error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Another Volley Error",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prams = new HashMap<>();
                prams.put(USER_ID,userId);
                prams.put(MONUMENT_ID, monumentId);
                prams.put(RANDOM,random);
                prams.put(TICKET_ID,ticketId);
                prams.put(QUANTITY,quan);
                prams.put(DATE,ticketDate);


                return prams;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}

