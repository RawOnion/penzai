package com.example.user.app830.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.user.app830.android_db.*;

import com.example.user.app830.R;
import com.example.user.app830.information;
import com.example.user.app830.main_menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class add extends Activity {

    private ListView listView = null;
    private MyApplication application = null;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        listView = (ListView) findViewById(R.id.tianjia);
        application = (MyApplication) getApplication();
        list = application.getList();
        SimpleAdapter Adapter = new SimpleAdapter(this, list, R.layout.message_adapter, new String[]{"f_name", "photo", "habit"}, new int[]{R.id.info_name, R.id.info_head, R.id.info_last});
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SimpleAdapter adapter= (SimpleAdapter) parent.getAdapter();
                Map<String,String> map=(Map<String, String>) adapter.getItem(position);
                Intent intent = new Intent(add.this,information.class);
                intent.putExtra("name",map.get("f_name"));
                startActivity(intent);
            }

        });

    }

    public void back() {
        Intent intent = new Intent(this, main_menu.class);
        startActivity(intent);
    }
}
