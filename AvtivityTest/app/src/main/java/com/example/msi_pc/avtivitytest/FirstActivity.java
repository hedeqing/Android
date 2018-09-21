package com.example.msi_pc.avtivitytest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class FirstActivity extends Activity {
    private  String[] fruit = { "apple","orange","banana","watermalon","pear","grape","pineapple","strawberry","cherry","Mango"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        Log.d("FirstActivity", "onOptionsItemSelected execute");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FirstActivity.this,R.layout.activity_second,fruit);
        ListView listView = findViewById(R.id.)
    }

}
