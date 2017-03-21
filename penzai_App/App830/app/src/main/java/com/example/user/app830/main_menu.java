package com.example.user.app830;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.app830.android_db.MyApplication;
import com.example.user.app830.mysql_db.UserInfo;
import com.example.user.app830.tools.CircularImage;
import com.nineoldandroids.view.ViewHelper;
import com.example.user.app830.Utils.*;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class main_menu extends FragmentActivity implements View.OnClickListener {

    private LinearLayout content;
    private ImageView message, data, relation, service, turn_left;
    private TextView tv_title;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private CircularImage touxiang;
    private ImageView add;
    MyApplication app = null;


    private String name;
    private String qianming;
    private Blob img = null;
    private byte[] image = null;


    private static final int CHANGE = 1;
    private static List<Object> params = new ArrayList<Object>();


    private DrawerLayout mDrawerLayout;

    private int TAG = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE:
                    touxiang.setImageBitmap((Bitmap) app.getUserinfo().get("img"));
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        content = (LinearLayout) findViewById(R.id.content);
        message = (ImageView) findViewById(R.id.message);
        data = (ImageView) findViewById(R.id.data);
        relation = (ImageView) findViewById(R.id.relation);
        service = (ImageView) findViewById(R.id.service);
        tv_title = (TextView) findViewById(R.id.tv_title);
        turn_left = (ImageView) findViewById(R.id.turn_left);
        touxiang = (CircularImage) findViewById(R.id.touxiang);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        add = (ImageView) findViewById(R.id.add);

        message.setOnClickListener(this);
        data.setOnClickListener(this);
        relation.setOnClickListener(this);
        service.setOnClickListener(this);
        add.setOnClickListener(this);


        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.content, new message());
        ft.commit();

        Intent intent = getIntent();
        params.add(intent.getStringExtra("username"));

        initView();
        initEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread() {
            @Override
            public void run() {
                LoginService loginService = new LoginService();
                Map<String, Object> userInfo = loginService.downdata(params);
                app = (MyApplication) getApplication();
                app.setUserinfo(userInfo);
                name = (String) app.getUserinfo().get("name");
                if (app.getUserinfo().get("img") != null) {
                    Message msg = new Message();
                    msg.what = CHANGE;
                    handler.sendMessage(msg);
                }
            }
        }.start();
        if (mDrawerLayout.isDrawerOpen(findViewById(R.id.left_menu)))
            mDrawerLayout.closeDrawers();
    }


    public void onBackPressed() { //重新返回键
        if (mDrawerLayout.isDrawerOpen(findViewById(R.id.left_menu))) {
            mDrawerLayout.closeDrawers();
        } else super.onBackPressed();
    }

    public void reSet() {
        if (TAG == 1) {
            message.setImageResource(R.drawable.message);
        } else if (TAG == 2) {
            data.setImageResource(R.drawable.data);
        } else if (TAG == 3) {
            relation.setImageResource(R.drawable.relation);
        } else if (TAG == 4) {
            service.setImageResource(R.drawable.service);
        }
    }


    public void onClick(View v) {
        ft = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.message:
                reSet();
                tv_title.setText(R.string.message);
                message.setImageResource(R.drawable.message_press);
                ft.replace(R.id.content, new message());
                TAG = 1;
                break;
            case R.id.data:
                reSet();
                tv_title.setText(R.string.data);
                data.setImageResource(R.drawable.data_press);
                ft.replace(R.id.content, new data());
                TAG = 2;
                break;
            case R.id.relation:
                reSet();
                tv_title.setText(R.string.relation);
                relation.setImageResource(R.drawable.relation_press);
                ft.replace(R.id.content, new relation());
                TAG = 3;
                break;
            case R.id.service:
                reSet();
                tv_title.setText(R.string.service);
                service.setImageResource(R.drawable.service_press);
                ft.replace(R.id.content, new service());
                TAG = 4;
                break;
            case R.id.add:
                Intent intent = new Intent(this, add.class);
                startActivity(intent);
                break;
        }
        ft.commit();
    }

//    public void refresh(){
//        System.out.println("刷新");
//        ft = fm.beginTransaction();
//        ft.replace(R.id.content, new message());
//        ft.commit();
//    }

    public void left(View view) {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.RIGHT);// 关闭右侧菜单的滑动出现效果
    }

    /**
     * 初始化DrawerLayout事件监听
     */
    private void initEvents() {
        // 设置监听
        mDrawerLayout.setDrawerListener(new DrawerListener() {

            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                // 展开左侧菜单
                float leftScale = 1 - 0.3f * scale;

                // 设置左侧菜单缩放效果
                ViewHelper.setScaleX(mMenu, leftScale);
                ViewHelper.setScaleY(mMenu, leftScale);
                ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));

                // 设置中间View缩放效果
                ViewHelper.setTranslationX(mContent,
                        mMenu.getMeasuredWidth() * (1 - scale));
                ViewHelper.setPivotX(mContent, 0);
                ViewHelper.setPivotY(mContent,
                        mContent.getMeasuredHeight() / 2);
                mContent.invalidate();
                ViewHelper.setScaleX(mContent, rightScale);
                ViewHelper.setScaleY(mContent, rightScale);

            }

            // 菜单打开
            @Override
            public void onDrawerOpened(View drawerView) {
                TextView left_name = (TextView) drawerView.findViewById(R.id.left_name);
                left_name.setText(name);
                if (app.getUserinfo().get("img") != null) {
                    CircularImage touxiang = (CircularImage) drawerView.findViewById(R.id.left_touxiang);
                    touxiang.setImageBitmap((Bitmap) app.getUserinfo().get("img"));
                }
            }

            // 菜单关闭
            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });
    }


}




