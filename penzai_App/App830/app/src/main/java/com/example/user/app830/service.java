package com.example.user.app830;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.user.app830.service_activity.set;
import com.example.user.app830.service_activity.shangxi;
import com.example.user.app830.service_activity.shop;
import com.example.user.app830.service_activity.tuijian;
import com.example.user.app830.service_activity.xinxi;


public class service extends Fragment implements View.OnClickListener {

    private ImageView shangxi;
    private ImageView shop;
    private ImageView tuijian;
    private ImageView xinxi;
    private ImageView set;
    private ImageView exit;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_layout, null);

        shangxi = (ImageView) view.findViewById(R.id.shangxi);
        shop = (ImageView) view.findViewById(R.id.shop);
        tuijian = (ImageView) view.findViewById(R.id.tuijian);
        xinxi = (ImageView) view.findViewById(R.id.xinxi);
        set = (ImageView) view.findViewById(R.id.set);
        exit = (ImageView) view.findViewById(R.id.exit);

        shangxi.setOnClickListener(this);
        shop.setOnClickListener(this);
        tuijian.setOnClickListener(this);
        xinxi.setOnClickListener(this);
        set.setOnClickListener(this);
        exit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shangxi:
                Intent intent = new Intent(getActivity(), shangxi.class);
                startActivity(intent);
                break;
            case R.id.shop:
                Intent intent2 = new Intent(getActivity(), shop.class);
                startActivity(intent2);
                break;
            case R.id.tuijian:
                Intent intent3 = new Intent(getActivity(), tuijian.class);
                startActivity(intent3);
                break;
            case R.id.xinxi:
                Intent intent4 = new Intent(getActivity(), xinxi.class);
                startActivity(intent4);
                break;
            case R.id.set:
                Intent intent5 = new Intent(getActivity(), set.class);
                startActivity(intent5);
                break;
            case R.id.exit:
                break;
        }

    }
}
