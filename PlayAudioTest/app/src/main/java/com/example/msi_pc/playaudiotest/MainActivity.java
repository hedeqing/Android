package com.example.msi_pc.playaudiotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button play  = (Button) findViewById(R.id.play);
        Button pause   = (Button) findViewById(R.id.pause);
        Button stop = (Button) findViewById(R.id.stop);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    private  void initMediaPlayer(){

        try {
            File file  = new File(Environment.getExternalStorageDirectory(),"music.mp3");
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.play:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
            case R.id.pause:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
            case R.id.stop:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();;
            mediaPlayer.release();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                initMediaPlayer();
                }
                else {
                        Toast.makeText(this,"拒绝权限将无法使用该软件",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
}
