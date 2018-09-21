package com.example.msi_pc.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by MSI-PC on 2018/7/23.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public  static  final  String CREATE_BOOK="create table Book(" +
            "id integer primary key autoincrement" +
            "author text" +
            "price real" +
            "pages integer" +
            "name text)" ;

    private  Context mcontext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        Toast.makeText(mcontext,"create succeed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
