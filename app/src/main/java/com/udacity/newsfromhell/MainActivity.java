package com.udacity.newsfromhell;

import android.app.ProgressDialog;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.newsfromhell.URLRes.ResUtils;
import com.udacity.newsfromhell.URLRes.bean.News;
import com.udacity.newsfromhell.URLRes.bean.SmartImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final int SUCCESS=1;
    private final int FAIL=2;
    private ListView lv_news;
    private EditText edt_url;
    private ProgressDialog pd;
    private List<News> newses;
    Button btn_update;
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==SUCCESS){
                //Toast.makeText(MainActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                //处理ui
                newses= (List<News>) msg.obj;
                lv_news.setAdapter(new MyAdapter());
            }else{
                Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_news = (ListView) findViewById(R.id.lv_news);
        edt_url = (EditText) findViewById(R.id.actv_url);
        btn_update = (Button) findViewById(R.id.btn_update);

        btn_update.setOnClickListener(this);

    }

    /**
     * 设置点击事件
     * 更新
     * @param v
     */
    @Override
    public void onClick(View v) {
        //获取url
        final String path = edt_url.getText().toString().trim();
        //Toast.makeText(this,path,Toast.LENGTH_SHORT).show();

        //处理网络请求
        new Thread(){
            @Override
            public void run() {
                try {
                    List<News> newses = ResUtils.getRes(path);
                    //
                   // Toast.makeText(MainActivity.this,newses.toString(),Toast.LENGTH_SHORT).show();
                    if (newses != null) {
                        Message msg=Message.obtain();
                        msg.what=SUCCESS;
                        msg.obj=newses;
                        handler.sendMessage(msg);
                    } else {
                        //失败
                        Message msg=Message.obtain();
                        msg.what=FAIL;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    Message msg=Message.obtain();
                    msg.what=FAIL;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();

        pd=new ProgressDialog(this);
        pd.setMessage("加载中...");
        pd.show();

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newses.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=View.inflate(MainActivity.this,R.layout.newsitem,null);
            }
            SmartImageView img= (SmartImageView) convertView.findViewById(R.id.imageView);
            TextView tv1=(TextView)convertView.findViewById(R.id.tv_title);
            TextView tv2=(TextView)convertView.findViewById(R.id.tv_content);
            tv1.setText(newses.get(position).title);
            tv2.setText(newses.get(position).link);
            img.setImg(newses.get(position).img_url);
            return convertView;
        }
    }
}
