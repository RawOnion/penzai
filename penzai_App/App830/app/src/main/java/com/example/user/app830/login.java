package com.example.user.app830;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.app830.Utils.LoginService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class login extends Activity implements View.OnClickListener {

    private static final int SUCCESS = 0;
    private static final int ERROR = 1;
    private EditText et_name;
    private EditText et_password;
    private TextView zhuce;


    String name;
    String password;

    LoginService loginService;
    final List<Object> params = new ArrayList<Object>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    Toast.makeText(login.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    Toast.makeText(login.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_name = (EditText) findViewById(R.id.username);
        et_password = (EditText) findViewById(R.id.login_password);
        zhuce = (TextView) findViewById(R.id.zhuce);


        zhuce.setOnClickListener(this);


        Map<String, String> map = LoginService.getSiveUserInfo(this);
        if (map != null) {
            et_name.setText(map.get("name"));
            et_password.setText(map.get("password"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> map = LoginService.getSiveUserInfo(this);
        if (map != null) {
            et_name.setText(map.get("name"));
            et_password.setText(map.get("password"));
        }
    }



    public void click(View view) {
        name = et_name.getText().toString().trim();
        password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password))
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        else {
            loginService = new LoginService();
            System.out.println(name);
            System.out.println(password);
            params.add(name);
            params.add(password);
            new Thread() {
                @Override
                public void run() {
                    boolean flag = loginService.login(params);
                    params.clear();
                    if (flag) {
                        LoginService.SiveUserInfo(login.this, name, password);
                        Message msg = new Message();
                        msg.what = SUCCESS;
                        handler.sendMessage(msg);
                        Intent intent = new Intent(login.this, main_menu.class);
                        intent.putExtra("username", name);
                        startActivity(intent);
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR;
                        handler.sendMessage(msg);

                    }
                }
            }.start();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhuce:
                Intent intent = new Intent(this, zhuce.class);
                startActivity(intent);
                break;
            case R.id.login:
                Intent intent2 = new Intent(this, main_menu.class);
                startActivity(intent2);
                break;
        }
    }
}

