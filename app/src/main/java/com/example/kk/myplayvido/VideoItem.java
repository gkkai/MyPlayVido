package com.example.kk.myplayvido;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;

/**
 * Created by kk on 2017/9/6.
 */

public class VideoItem implements Serializable {
    public String title;//视频的名称
    public long size;//视频的大小
    public long duration;//视频的时长
    public String path;//视频在手机中的路径
    /**
     * 将cursor中的数据封装为一个info
     * @param cursor
     * @return
     */
    public static VideoItem fromCursor(Cursor cursor){
        VideoItem videoItem = new VideoItem();
        videoItem.duration=cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
        videoItem.path=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        videoItem.size=cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
        videoItem.title=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
        return videoItem;
    }


}
