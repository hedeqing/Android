package com.example.msi_pc.cameraalbumtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.R.attr.contextUri;
import static android.R.attr.data;
import static android.R.attr.dial;

public class MainActivity extends AppCompatActivity {

    public  static  final int TAKE_PHOTO =1;
    public  static  final int CHOOSE_PHOTO = 2;
    private ImageView picture;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.take_photo);
        Button chooseButton = (Button) findViewById(R.id.choose_from_album);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(getExternalCacheDir(),"outputImage.jpg");
                try {
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT>=24){
                    imageUri = FileProvider.getUriForFile(MainActivity.this,"com.example.msi_pc.cameraalbumtest.fileprovider",outputImage);
                }else {
                    imageUri = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new  String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
                else {
                    openAlbum();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                picture = (ImageView) findViewById(R.id.picture);//第一行代码中缺少这一句初始化，图片设置不成功
                if(resultCode==RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                            .openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                       handleImageOnKitKat(data);
                    }else {
                        //handleImageBeforeKitKat(data);
                        Toast.makeText(this,"this is under 19",Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            default:
                break;
        }
    }
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }
                else {
                    Toast.makeText(this,"you deny the pessmission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
//        如果uri是document类型，就通过读取document id进行处理
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
//            如果Uri的Authority是media格式的话，document id还需要进行解析，然后通过字符串
//            分割方式取出后半部分才能得到真正的数字id
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads,documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads.public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            如果是content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            如果是file类型，直接获取图片路径
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }
//    @RequiresApi(19)
//    private  void handleImageOnKitKat(Intent data){
//        String imagePath = null;
//        Uri uri = data.getData();
//        if(DocumentsContract.isDocumentUri(this,uri)){
//            String docID = DocumentsContract.getDocumentId(uri);
//            if("com.android.providers.media.documents".equals(uri.getAuthority())){
//                String id =docID.split(":")[1];
//                String selection = MediaStore.Images.Media._ID+"="+id;
//                imagePath  = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
//            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
//
//                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content//downloads//public_downloads"), Long.valueOf(docID));
//                imagePath = getImagePath(contentUri, null);
//            }
//        }else  if ("content".equalsIgnoreCase(uri.getScheme())){
//                imagePath = getImagePath(uri,null);
//
//            }else if("file".equalsIgnoreCase(uri.getScheme())){
//                imagePath = uri.getPath();
//            }
//        display(imagePath);
//    }

    private  void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    private  String getImagePath(Uri uri,String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
             }
             cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        picture = (ImageView) findViewById(R.id.picture);
        if (null != imagePath) {
            Bitmap b = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(b);
        } else {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_LONG).show();
        }
    }
}
