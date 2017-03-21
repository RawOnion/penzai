package com.example.user.app830;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.user.app830.Utils.Jdbcutils;
import com.example.user.app830.android_db.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.user.app830.Utils.Jdbcutils;
import com.example.user.app830.leftmenu_activity.touxiang;
import com.example.user.app830.tools.CircularImage;


public class information extends Activity {
    private static final int CHANGE = 1;
    private static final int CANCEL_ATTENTION = 2;
    private static final int ATTENTION = 3;
    private Map<String, Object> map = null;
    private Jdbcutils jdbcutils;
    private List<Object> params = new ArrayList<Object>();
    private ImageView photobig;
    private CircularImage photo;
    private ImageView like = null;
    private ListView listView = null;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE:
                    photobig.setImageBitmap((Bitmap) map.get("photobig"));
                    photo.setImageBitmap((Bitmap) map.get("photo"));
                    xianshi();
                    break;
                case CANCEL_ATTENTION:
                    like.setImageResource(R.drawable.unlike);
                    Toast.makeText(information.this, "已取消养殖", Toast.LENGTH_LONG).show();
                    break;
                case ATTENTION:
                    like.setImageResource(R.drawable.like);
                    Toast.makeText(information.this, "养殖成功", Toast.LENGTH_LONG).show();
            }
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        photobig = (ImageView) findViewById(R.id.f_photobig);
        photo = (CircularImage) findViewById(R.id.f_photo);
        like = (ImageView) findViewById(R.id.info_like);
        listView = (ListView) findViewById(R.id.info_adapter);
        listView.setFocusable(false);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        params.add(name);
        new Thread() {
            @Override
            public void run() {
                jdbcutils = new Jdbcutils();
                String sql = "select * from pot_data where f_name=?";
                try {
                    jdbcutils.getConnection("flowers");
                    map = jdbcutils.findSimpleResult(sql, params);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    byte[] image = (byte[]) map.get("photobig");
                    map.put("photobig", BitmapFactory.decodeByteArray(image, 0, image.length, options));
                    byte[] image2 = (byte[]) map.get("photo");
                    map.put("photo", BitmapFactory.decodeByteArray(image2, 0, image2.length, options));
                    Message msg = new Message();
                    msg.what = CHANGE;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    jdbcutils.releaseConn();
                }
            }
        }.start();
        MyApplication app = (MyApplication) getApplication();
        String attention_flower = (String) app.getUserinfo().get("attention_flower");
        if (attention_flower.contains(name))
            //如果已标记
            like.setImageResource(R.drawable.like);
    }

    protected void onResume() {
        super.onResume();
    }

    public void xianshi() {
        for (String key : map.keySet()) {
            if (key != "photo" && key != "photobig" && key != "f_name")
                if (map.get(key) != "")
                    map.put(key, "     " + map.get(key));
            //System.out.println("key= "+ key + " and value= " + map.get(key));
        }
        list.add(map);
        SimpleAdapter Adapter = new SimpleAdapter(information.this, list, R.layout.information_adapter, new String[]{"f_name", "nickname", "language_flowers", "habit", "special", "value"}, new int[]{R.id.info_title, R.id.info_content, R.id.info_content2, R.id.info_content3, R.id.info_content4, R.id.info_content5});
        listView.setAdapter(Adapter);
        Adapter.notifyDataSetChanged();
    }

    public void like(View view) {//关注按钮
        MyApplication app = (MyApplication) getApplication();
        final String username = (String) app.getUserinfo().get("username");
        new Thread() {
            @Override
            public void run() {
                jdbcutils = new Jdbcutils();
                String f_name = (String) map.get("f_name");
                String sql_info_attention = "select attention_flower from userinfo where username='" + username + "'";
                String sql_attention = "update userinfo set attention_flower=concat(attention_flower,'" + f_name + "#') where username='" + username + "'";
                try {
                    jdbcutils.getConnection();
                    //若数据库中已有该f_name,则表示已关注，现在则取消关注。
                    Map<String, Object> attention = jdbcutils.findSimpleResult(sql_info_attention, null);
                    String attention_flower = (String) attention.get("attention_flower");
                    if (attention_flower.contains(f_name)) {
                        attention_flower = attention_flower.replaceAll(f_name + "#", "");
                        attention_flower = attention_flower.replaceAll(" ", "");
                        String sql_cancel_attention = "update userinfo set attention_flower='" + attention_flower + "' where username='" + username + "'";
                        boolean flag = jdbcutils.updateByPreparedStatement(sql_cancel_attention, null);
                        if (flag == true) {
                            System.out.println("cancel_attention");
                        }
                        Message msg = new Message();
                        msg.what = CANCEL_ATTENTION;
                        handler.sendMessage(msg);
                    } else {
                        boolean flag = jdbcutils.updateByPreparedStatement(sql_attention, null);
                        if (flag == true) {
                            System.out.println("attention");
                        }
                        Message msg = new Message();
                        msg.what = ATTENTION;
                        handler.sendMessage(msg);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    jdbcutils.releaseConn();
                }
            }
        }.start();
    }

}
