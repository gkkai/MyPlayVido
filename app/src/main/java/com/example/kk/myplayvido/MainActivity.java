package com.example.kk.myplayvido;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String[] projection = {MediaStore.Video.Media._ID, MediaStore.Video.Media.TITLE, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.DATA};
    private ListView listView;
    ArrayList<VideoItem> list;
private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt= (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGetImageThread();
            }
        });
        listView= (ListView) findViewById(R.id.lView_ShowVideo);
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            Activity activty=MainActivity.this;
            ActivityCompat.requestPermissions(activty,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    101);
            return;
        }




    }

    private Serializable cursorToList(Cursor cursor) {
        list  = new ArrayList<VideoItem>();
        cursor.moveToPosition(-1);
        while(cursor.moveToNext()){
            list.add(VideoItem.fromCursor(cursor));
        }
        return list;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 101){
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)
                    &&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //用户同意使用write


            }else{
                //用户不同意，自行处理即可
                finish();
            }
        }
    }

    private void startGetImageThread() {
        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        final VideoListAdapter  adapter = new VideoListAdapter(this, cursor);
        //listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //获取当前点击的条目对应的数据
                Cursor cursor = (Cursor) adapter.getItem(position);
                Bundle bundle = new Bundle();
                //当前点击播放视频的位置
                bundle.putInt("currentPosition", position);
                //视频信息的集合
                bundle.putSerializable("videoList", cursorToList(cursor));

                Intent intent = new Intent(getApplicationContext(),VitamioPlayerActivity.class);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
    }



}
