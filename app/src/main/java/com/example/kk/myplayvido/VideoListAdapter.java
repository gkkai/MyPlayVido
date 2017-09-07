package com.example.kk.myplayvido;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by kk on 2017/9/6.
 */

public class VideoListAdapter extends CursorAdapter {
    public VideoListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return View.inflate(context, R.layout.adapter_video_list, null);
    }
    ViewHolder holder;
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        holder = getHolder(view);
        //获取数据，这里的数据是保存在cursor中，通过方法fromCursor直接转换成VideoItem对象保存的信息
        VideoItem videoItem = VideoItem.fromCursor(cursor);

        holder.tv_title.setText(videoItem.title);
        //这里获取到的时长是毫秒，可以通过 自定义的时间格式来对时间进行格式化操作
       // holder.tv_duration.setText(StringUtil.formatVideoDuration(videoItem.duration));
        //将视频的大小格式化为M来进行数据显示
        holder.tv_size.setText(Formatter.formatFileSize(context, videoItem.size));
        createVideoThumbnail(videoItem.path,holder.imageView);
    }
    private ViewHolder getHolder(View view){
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if(viewHolder==null){
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        return viewHolder;
    }
    class ViewHolder{
        TextView tv_title,tv_duration,tv_size;
        ImageView imageView;
        public ViewHolder(View view){
            imageView= (ImageView) view.findViewById(R.id.imView);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_duration = (TextView) view.findViewById(R.id.tv_duration);
            tv_size = (TextView) view.findViewById(R.id.tv_size);
        }
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void createVideoThumbnail(final String path, final ImageView imageView) {
        Observable<Bitmap> observable = Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                File file=new File(path);
                if (!file.exists()) {
                    return ;
                }
                int kind = MediaStore.Video.Thumbnails.MINI_KIND;
              /*if (Build.VERSION.SDK_INT >= 14) {
                    retriever.setDataSource(file.getAbsolutePath(), new HashMap<String, String>());
                } else {
                    retriever.setDataSource(file.getAbsolutePath());
                }*/
                retriever.setDataSource(file.getAbsolutePath());
                bitmap = retriever.getFrameAtTime(-1);
                subscriber.onNext(bitmap);
                retriever.release();
            }
        });

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        //设置封面
                        imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
                    }
                });
    }
}
