package com.example.msi_pc.androidthreadtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private  static  final int UPDATE_TEXT =1;
    private TextView textView;

    private Handler handler = new Handler(){
        public  void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    textView.setText("Nice to see you");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button change = (Button) findViewById(R.id.change_text);
        textView = (TextView) findViewById(R.id.text);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId() ){
                    case  R.id.change_text:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                              Message message = new Message();
                                message.what = UPDATE_TEXT;
                                handler.sendMessage(message);
                            }
                        }).start();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
