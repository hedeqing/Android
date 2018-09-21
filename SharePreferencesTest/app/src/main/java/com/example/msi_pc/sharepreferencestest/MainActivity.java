package com.example.msi_pc.sharepreferencestest;

import android.content.SharedPreferences;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button savaData = (Button) findViewById(R.id.save_data);
        savaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_APPEND).edit();
                editor.putString("name","Tom");
                editor.putInt("age",128);
                editor.putBoolean("married",false);
                editor.apply();
            }
        });
        Button getData = (Button) findViewById(R.id.get_data);
        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences editor = getSharedPreferences("data",MODE_PRIVATE);
                String name = editor.getString("name","");
                int age = editor.getInt("age",0);
                Boolean married = editor.getBoolean("married",false);
                Log.d("MainActivity","name is "+name);
                Log.d("MainActivity","age is "+age);
                Log.d("MainActivity","married is "+married);

            }
        });
    }
}
