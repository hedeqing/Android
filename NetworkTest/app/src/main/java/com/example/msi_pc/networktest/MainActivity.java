package com.example.msi_pc.networktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView responseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response_text);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.send_request){
//            sendRequestWithHttpURLConnection();
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().url("http://10.0.2.2/get_data.xml").build();
                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                   // parseXMLWidthPull(responseData);
                    parseXMLWithPull(responseData);
                    Log.d("MainActivity","run successful");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseXMLWithPull(String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int evenyType = xmlPullParser.getEventType();
            String id = "";
            String name ="";
            String version="";
            while (evenyType!=XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (evenyType){
                    case XmlPullParser.START_TAG:{
                        if("id".equals(nodeName)){
                            id = xmlPullParser.nextText();
                        }else  if("name".equals(nodeName)){
                            name = xmlPullParser.nextText();
                        }else  if("version".equals(nodeName)){
                            version = xmlPullParser.nextText();
                        }
                    }
                    break;
                    case XmlPullParser.END_TAG:{
                        if("app".equals(nodeName)){
                            Log.d("MainActivity","id is "+id);
                            Log.d("MainActivity","name is "+name);
                            Log.d("MainActivity","version is "+version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                evenyType =xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line =reader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void showResponse(final  String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}
