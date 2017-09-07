package com.example.kk.myplayvido;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.util.ArrayList;

import static com.example.kk.myplayvido.R.id.tv_duration;

public class VitamioPlayerActivity extends AppCompatActivity {

    private VideoView video_view;
    private ProgressBar video_seekbar;
    private RelativeLayout mRlVv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitamio_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        video_view= (VideoView) findViewById(R.id.vView);
        mRlVv= (RelativeLayout) findViewById(R.id.rl_vv);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        currentPosition = getIntent().getExtras().getInt("currentPosition");
        videoList = (ArrayList<VideoItem>) getIntent().getExtras().getSerializable("videoList");
        video_view.setMediaController(new MediaController(this));
        //video_view.getCurrentPosition();
        //设置视频源播放res/raw中的文件,文件名小写字母,格式: 3gp,mp4等,flv的不一定支持;
        Uri rawUri = Uri.parse(videoList.get(currentPosition).path);
        video_view.setVideoURI(rawUri);
        video_view.start();
        video_view.requestFocus();
        //设置进行播放的操作监听
       /* video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //进行视频的播放         
                video_view.start();
                //更新视频播放指示的进度条  
                updatePlayProgress();
                //设置指示的进度条的最大值  
                video_seekbar.setMax((int) video_view.getDuration());
                //设置显示当前的播放时间  
              *//*  tv_current_position.setText("00:00");
                //设置显示视频的总时长  
                tv_duration.setText(StringUtil.formatVideoDuration(video_view.getDuration()));
                //设置播放按钮的背景图片  
                btn_play.setBackgroundResource(R.drawable.selector_btn_pause);*//*
            }

            private void updatePlayProgress() {
            }
        });*/
    }

    private int currentPosition;//当前播放视频的位置
    private ArrayList<VideoItem> videoList;//当前的视频列表




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (video_view == null) {
            return;
        }
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){//横屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().invalidate();
            float height = DensityUtil.getWidthInPx(this);
            float width = DensityUtil.getHeightInPx(this);
            mRlVv.getLayoutParams().height = (int) width;
            mRlVv.getLayoutParams().width = (int) height;
        } else {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            float width = DensityUtil.getWidthInPx(this);
            float height = DensityUtil.dip2px(this, 200.f);
            mRlVv.getLayoutParams().height = (int) height;
            mRlVv.getLayoutParams().width = (int) width;
        }
    }





}