package com.example.lenovo.hackathontourism;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.hackathontourism.data.DbUtil;
import com.example.lenovo.hackathontourism.data.TicketDBHelper;
import com.example.lenovo.hackathontourism.data.TicketsSchema;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QRCodeGeneration extends AppCompatActivity {

    int flag1=0,flag2=0,flag3=0,flag4=0;
    ImageView imageView1,imageView2,imageView3,imageView4;
    Button button;
    EditText editText;
    String EditTextValue ;
    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;
    private SQLiteDatabase mDb;
    public static final String SHARED_PREF_NAME="tourist";
    public static final String EMAIL_SHARED_PREF="email";
    public static final String LOGGEDIN_SHARED_PREF="loggedin";
    public static final String USERID_SHARED_PREF="userid";
    private boolean loggedIn=false;
    private String email = null;
    private ProgressDialog p;
    private String muserid1=null;
    TicketDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView1 = (ImageView)findViewById(R.id.imageView1a);
        imageView2 = (ImageView)findViewById(R.id.imageView1b);
        imageView3 = (ImageView)findViewById(R.id.imageView1c);
        imageView4 = (ImageView)findViewById(R.id.imageView1d);
        button = (Button)findViewById(R.id.button);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF, false);
        if(!loggedIn)
        {
            Toast.makeText(QRCodeGeneration.this,"Please Login to Book Ticket",Toast.LENGTH_LONG).show();
            Intent main = new Intent(QRCodeGeneration.this,LoginActivity.class);
            startActivity(main);
        }
        muserid1 = sharedPreferences.getString(USERID_SHARED_PREF,null);

        dbHelper = TicketDBHelper.getInstance(QRCodeGeneration.this);
        mDb = dbHelper.getWritableDatabase();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                p = new ProgressDialog(QRCodeGeneration.this);
                p.setMessage("Booking Ticket.. Please Wait");
                p.setIndeterminate(false);
                p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                p.setCancelable(false);
                p.show();
                imageView1.setImageBitmap(null);
                imageView1.destroyDrawingCache();
                imageView2.setImageBitmap(null);
                imageView2.destroyDrawingCache();
                imageView3.setImageBitmap(null);
                imageView3.destroyDrawingCache();
                imageView4.setImageBitmap(null);
                imageView4.destroyDrawingCache();
                if(flag1==1)
                    qr_code("1");
                if(flag2==1)
                    qr_code("2");
                if(flag3==1)
                    qr_code("3");
                if(flag4==1)
                    qr_code("4");

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
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_hawa_mahal:
                if (checked)
                {
                    flag1=1;
                }
                else
                    flag1=0;
                break;
            case R.id.checkbox_jal_mahal:
                if (checked)
                {
                    flag2=1;
                }
                else
                    flag2=0;
                break;
            case R.id.checkbox_albert_hall_museum:
                if (checked)
                {
                    flag3=1;
                }
                else
                    flag3=0;
                break;
            case R.id.checkbox_jantar_mantar:
                if (checked)
                {
                    flag4=1;
                }
                else
                    flag4=0;
                break;
        }
    }

    public static final String QR_URL="http://139.59.10.59/booking.php";

    //data = {'userid' : '1', 'monumentid' : '1', 'quantity' : 5, 'date' : '2018-03-23'}

    public static final String KEY_USERID="userid";
    public static final String KEY_MONUMENT_ID="monumentid";
    public static final String KEY_QUANTITY="quantity";
    public static final String KEY_DATE="date";

    public void qr_code(final String mmonumentid1) {

        EditText mquantity=(EditText)findViewById(R.id.quantityid);
        EditText mdate=(EditText)findViewById(R.id.dateid);
        final String mquantity1=mquantity.getText().toString();
        final String mdate1=mdate.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, QR_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        p.dismiss();
                        p = new ProgressDialog(QRCodeGeneration.this);
                        p.setMessage("Confirming Ticket...");
                        p.setIndeterminate(false);
                        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        p.setCancelable(false);
                        p.show();
                        JSONObject jObj = null;
                        try {
                            jObj = new JSONObject(response);
                            try {
                                bitmap = TextToImageEncode(response);
                                p.dismiss();
                                byte[] byteqr = DbUtil.getBytes(bitmap);
                                addEntry(muserid1,mquantity1,mmonumentid1,mdate1,byteqr);
                                if(mmonumentid1.equals("1"))
                                    imageView1.setImageBitmap(bitmap);
                                if(mmonumentid1.equals("2"))
                                    imageView2.setImageBitmap(bitmap);
                                if(mmonumentid1.equals("3"))
                                    imageView3.setImageBitmap(bitmap);
                                if(mmonumentid1.equals("4"))
                                    imageView4.setImageBitmap(bitmap);

                            } catch (WriterException e) {
                                Toast.makeText(QRCodeGeneration.this,"Booking Error",Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(QRCodeGeneration.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        p.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> prams = new HashMap<>();

                prams.put(KEY_USERID, muserid1);
                prams.put(KEY_MONUMENT_ID, mmonumentid1);
                prams.put(KEY_QUANTITY, mquantity1);
                prams.put(KEY_DATE, mdate1);

                return prams;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(QRCodeGeneration.this);
        requestQueue.add(stringRequest);
    }



    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        int color_white = getResources().getColor(R.color.colorWhite);
        int colot_black = getResources().getColor(R.color.colorPrimaryDark);

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? colot_black:color_white;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    public void addEntry(String userid,String qty,String mid,String date, byte[] image) throws SQLiteException {

        ContentValues cv = new ContentValues();
        cv.put(TicketsSchema.TicketsEntry.KEY_USERID, userid);
        cv.put(TicketsSchema.TicketsEntry.KEY_MONUMENTID,mid);
        cv.put(TicketsSchema.TicketsEntry.KEY_QUANTITY,qty);
        cv.put(TicketsSchema.TicketsEntry.KEY_DATE,date);
        cv.put(TicketsSchema.TicketsEntry.KEY_QRCODE,   image);
        mDb.insert(TicketsSchema.TicketsEntry.TABLE_NAME, null, cv );
        //mDb.close();
    }

}
