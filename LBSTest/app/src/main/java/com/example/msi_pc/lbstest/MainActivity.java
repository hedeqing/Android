package com.example.msi_pc.lbstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public LocationClient mLocationClient;
    private TextView positionText;
    private MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient  = new LocationClient(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLoactiponListener());
        setContentView(R.layout.activity_main);
        positionText = (TextView) findViewById(R.id.position_text_view);
        mapView = (MapView) findViewById(R.id.bmapView);
        List<String> permissionList  = new ArrayList<>();

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
      if(!permissionList.isEmpty()){
          String [] permissions = permissionList.toArray(new String[permissionList.size()]);
          ActivityCompat.requestPermissions(MainActivity.this,permissions,1);
      }else {
          requestLocation();
      }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    private  void requestLocation(){
        initLocation();
        mLocationClient.start();
        }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for (int result:grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有权限才能使用该软件",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    public  class MyLoactiponListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
         StringBuffer currentLocation = new StringBuffer();
            currentLocation.append("维度:").append(bdLocation.getLatitude()).append("\n");
            currentLocation.append("经度:").append(bdLocation.getLongitude()).append("\n");
            currentLocation.append("国家:").append(bdLocation.getCountry()).append("\n");
            currentLocation.append("省:").append(bdLocation.getProvince()).append("\n");
            currentLocation.append("市:").append(bdLocation.getCity()).append("\n");
            currentLocation.append("区:").append(bdLocation.getDistrict()).append("\n");
            currentLocation.append("街道:").append(bdLocation.getStreet()).append("\n");
            currentLocation.append("定位方式：");

            if(bdLocation.getLocType()==BDLocation.TypeGpsLocation){
                currentLocation.append("GPS:");
            }else  if(bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
                currentLocation.append("网络:");
            }
            positionText = (TextView) findViewById(R.id.position_text_view);
           positionText.setText(currentLocation);
        }

    }
}
