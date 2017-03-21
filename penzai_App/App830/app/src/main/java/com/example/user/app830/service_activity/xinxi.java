package com.example.user.app830.service_activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.user.app830.R;
import com.example.user.app830.android_db.MyApplication;
import com.example.user.app830.tools.CircularImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class xinxi extends Activity {

    private CircularImage touxiang;
    private ListView listView;
    private Map<String, Object> map = new HashMap<String, Object>();
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> list_flower = new ArrayList<Map<String, Object>>();
    private TextView edit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinxi_1);
        touxiang = (CircularImage) findViewById(R.id.f_photo);
        listView = (ListView) findViewById(R.id.info_adapter);
        edit = (TextView) findViewById(R.id.xinxi_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(xinxi.this, xinxi_edit.class);
                startActivity(intent);
            }
        });
        MyApplication app = (MyApplication) getApplication();
        map = app.getUserinfo();
        list_flower = app.getList();
        touxiang.setImageBitmap((Bitmap) map.get("img"));

        //遍历list_flower查找用户所关注的盆栽
        String attention_flower = (String) map.get("attention_flower");
        String[] infos = attention_flower.split("#");
        for (int i = 0; i < infos.length; i++) {
            if (!infos[i].isEmpty()) {
                for (int n = 0; n < list_flower.size(); n++) {
                    if (infos[i].equals(list_flower.get(n).get("f_name"))) {
                        map.put("flower_img" + i, list_flower.get(n).get("photo"));
                        map.put("flower_name" + i, list_flower.get(n).get("f_name"));
                        System.out.println(list_flower.get(n).get("f_name"));
                    }
                }
            }
        }
        list.add(map);
        String[] map_key = {"name", "sex" + "age", "email", "qianming", "habit", "flower_img1", "flower_img2", "flower_img3", "flower_img4", "flower_name1", "flower_name2", "flower_name3", "flower_name4"};
        int[] layout = {R.id.xinxi_name, R.id.xinxi_other, R.id.xinxi_email, R.id.xinxi_qianming, R.id.xinxi_habit, R.id.pot_image1, R.id.pot_image2, R.id.pot_image3, R.id.pot_image4, R.id.pot_info1, R.id.pot_info2, R.id.pot_info3, R.id.pot_info4};
        SimpleAdapter Adapter = new SimpleAdapter(this, list, R.layout.activity_xinxi_2, map_key, layout);
        Adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                                  public boolean setViewValue(View view, Object data,
                                                              String textRepresentation) {
                                      // TODO Auto-generated method stub
                                      if (view instanceof ImageView && data instanceof Bitmap) {
                                          ImageView i = (ImageView) view;
                                          i.setImageBitmap((Bitmap) data);
                                          return true;
                                      }
                                      return false;
                                  }
                              }
        );
        listView.setAdapter(Adapter);
        Adapter.notifyDataSetChanged();
    }
}
