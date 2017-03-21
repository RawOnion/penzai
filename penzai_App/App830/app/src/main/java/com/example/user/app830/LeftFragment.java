package com.example.user.app830;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.app830.Utils.LoginService;
import com.example.user.app830.mysql_db.UserInfo;
import com.example.user.app830.service_activity.xinxi;
import com.example.user.app830.relation_activity.personal;
import com.example.user.app830.leftmenu_activity.*;

import com.example.user.app830.tools.CircularImage;

import java.util.ArrayList;
import java.util.List;

public class LeftFragment extends Fragment implements View.OnClickListener {

    private TextView name, xinxi, kongjian, guanzhu, shoucang, tiwen, zhuxiao;

    private CircularImage touxiang;

    private static final int TOUXIANG = 0;
    private static final int NAME = 1;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left, container, false);

        name = (TextView) view.findViewById(R.id.left_name);
        xinxi = (TextView) view.findViewById(R.id.left_xinxi);
        kongjian = (TextView) view.findViewById(R.id.left_kongjian);
        guanzhu = (TextView) view.findViewById(R.id.left_guanzhu);
        shoucang = (TextView) view.findViewById(R.id.left_shoucang);
        tiwen = (TextView) view.findViewById(R.id.left_tiwen);
        zhuxiao = (TextView) view.findViewById(R.id.left_zhuxiao);
        touxiang = (CircularImage) view.findViewById(R.id.left_touxiang);

        xinxi.setOnClickListener(this);
        kongjian.setOnClickListener(this);
        guanzhu.setOnClickListener(this);
        shoucang.setOnClickListener(this);
        tiwen.setOnClickListener(this);
        zhuxiao.setOnClickListener(this);
        touxiang.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_xinxi:
                Intent intent = new Intent(getActivity(), xinxi.class);
                startActivity(intent);
                break;
            case R.id.left_kongjian:
                Intent intent2 = new Intent(getActivity(), personal.class);
                startActivity(intent2);
                break;
            case R.id.left_guanzhu:
                Intent intent3 = new Intent(getActivity(), guanzhu.class);
                startActivity(intent3);
                break;
            case R.id.left_shoucang:
                Intent intent4 = new Intent(getActivity(), shoucang.class);
                startActivity(intent4);
                break;
            case R.id.left_tiwen:
                Intent intent6 = new Intent(getActivity(), tiwen.class);
                startActivity(intent6);
                break;
            case R.id.left_zhuxiao:break;
            case R.id.left_touxiang:
                Intent intent5 = new Intent(getActivity(), touxiang.class);
                intent5.putExtra("name",name.getText());
                startActivity(intent5);
                break;
        }

    }
}
