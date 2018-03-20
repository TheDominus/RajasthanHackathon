package com.example.lenovo.hackathontourism.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.lenovo.hackathontourism.data.TicketsSchema.*;

public class TicketDBHelper extends SQLiteOpenHelper{

    private static TicketDBHelper sInstance;

    public static synchronized TicketDBHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new TicketDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "tickets.db";

    // Table Names
    private static final String TABLE_NAME = "ticket_table";


    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + TABLE_NAME + "("+
            TicketsEntry.KEY_USERID + " TEXT PRIMARY KEY," +
            TicketsEntry.KEY_MONUMENTID + " TEXT NOT NULL," +
            TicketsEntry.KEY_QUANTITY + " TEXT," +
            TicketsEntry.KEY_DATE + " TEXT," +
            TicketsEntry.KEY_QRCODE + " BLOB);";

    public TicketDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating table
        db.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // create new table
        onCreate(db);
    }

}
