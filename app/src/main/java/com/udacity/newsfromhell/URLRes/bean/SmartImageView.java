package com.udacity.newsfromhell.URLRes.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.udacity.newsfromhell.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 佳伟 on 2017/1/5 0005.
 */

public class SmartImageView extends ImageView {
    private static final int SUCCESS =3 ;
    private static final int FAIL =4 ;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==SUCCESS){
                setImageBitmap((Bitmap) msg.obj);
            }else{
               setImageResource(R.mipmap.ic_launcher);
            }
        }
    };
    public SmartImageView(Context context) {
        super(context);
    }

    public SmartImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SmartImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 在子线程中获得图片,加载到image view
     *
     * @param path
     */
    public void setImg(final String path) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //超时和请求方式
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    //200
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream is = connection.getInputStream();
                        Bitmap bitmap= BitmapFactory.decodeStream(is);
                        Message msg=Message.obtain();
                        msg.obj=bitmap;
                        msg.what=SUCCESS;
                        handler.sendMessage(msg);
                    }else{
                        Message msg=Message.obtain();
                        msg.what=FAIL;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Message msg=Message.obtain();
                    msg.what=FAIL;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
}
