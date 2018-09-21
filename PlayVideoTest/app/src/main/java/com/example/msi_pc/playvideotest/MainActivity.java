package com.example.msi_pc.playvideotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button play = (Button) findViewById(R.id.play);
        Button pause = (Button) findViewById(R.id.pause);
        Button replay = (Button) findViewById(R.id.replay);
        videoView = (VideoView) findViewById(R.id.video_view);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
            if(!videoView.isPlaying()){
                videoView.start();
            }
            case R.id.pause:
                if(videoView.isPlaying()){
                    videoView.pause();
                }
            case R.id.replay:
                if(videoView.isPlaying()){
                    videoView.resume();
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView!=null){
            videoView.suspend();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  1:
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    initVideoPath();
                }else {
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void initVideoPath() {
        File file = new File(Environment.getExternalStorageDirectory(),"movie.mp4");
        videoView.setVideoPath(file.getPath());
    }
}
