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
import com.example.user.app830.relation_activity.community;
import com.example.user.app830.relation_activity.personal;
import com.example.user.app830.relation_activity.friend;

public class relation extends Fragment implements View.OnClickListener {
    private ImageView personal;
    private ImageView friend;
    private ImageView community;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.relation_layout, null);
        personal = (ImageView) view.findViewById(R.id.gerenkongjian);
        friend= (ImageView) view.findViewById(R.id.yihuahuiyou);
        community= (ImageView) view.findViewById(R.id.shequtaolun);

        personal.setOnClickListener(this);
        friend.setOnClickListener(this);
        community.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gerenkongjian:
                Intent intent =new Intent(getActivity(),personal.class);
                startActivity(intent);
                break;
            case R.id.yihuahuiyou:
                Intent intent2 =new Intent(getActivity(),friend.class);
                startActivity(intent2);
                break;
            case R.id.shequtaolun:
                Intent intent3 =new Intent(getActivity(),community.class);
                startActivity(intent3);
                break;
        }

    }
}
