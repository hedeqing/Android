package com.example.msi_pc.notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendNotice = (Button) findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.send_notice:
                        Intent intent = new Intent(MainActivity.this,NotificationActivity.class);
                        PendingIntent pendingIntent  = PendingIntent.getActivity(MainActivity.this,0,intent,0);
                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        Notification notification;
                        notification = new NotificationCompat.Builder(MainActivity.this).setContentTitle("this is a contexttitle")
                                .setContentText("this is context textbuybui uy yuuiu ghuohg ghkg hg hg iy")
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true)
                                .setPriority(Notification.PRIORITY_HIGH)
                                .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
                                .build();
                        manager.notify(3,notification);
                        //manager.notify(2,notification);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
