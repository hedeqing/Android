package com.example.msi_pc.servertest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  MyService.DownloadBinder downloadBinder ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = (Button) findViewById(R.id.start_server);
        Button stop = (Button) findViewById(R.id.stop_server);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        Button binderServer = (Button) findViewById(R.id.bind_server);
        Button unBinderServer = (Button) findViewById(R.id.unbinder_server);
        binderServer.setOnClickListener(this);
        unBinderServer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_server:
                Intent startIntent = new Intent(this,MyService.class);
                startService(startIntent);
                break;
            case  R.id.stop_server:
                Intent stopIntent  = new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            case  R.id.bind_server:
                Intent binderIntent = new Intent(this,MyService.class);
                bindService(binderIntent,connection,BIND_AUTO_CREATE);
                break;
            case R.id.unbinder_server:
                unbindService(connection);
                break;
            default:
                break;
        }
    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProcgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
