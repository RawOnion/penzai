package com.example.user.app830;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.app830.Utils.Jdbcutils;
import com.example.user.app830.Utils.Jdbcutils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.LogRecord;

import com.example.user.app830.Utils.*;

public class zhuce extends Activity {

    private final static int SUCCESS = 0;
    private final static int ERROR = 1;


    private EditText et_name;
    private EditText et_user;
    private EditText et_password;
    private EditText et_password2;
    private EditText et_email;

    String name;
    String user;
    String password;
    String password2;
    String email;

    Jdbcutils jdbcUtils = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                Toast.makeText(zhuce.this, "注册成功", Toast.LENGTH_SHORT).show();
            }
            if (msg.what == ERROR) {
                if((String) msg.obj!="true")
                Toast.makeText(zhuce.this,(String) msg.obj, Toast.LENGTH_LONG).show();
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);

        et_name = (EditText) findViewById(R.id.name);
        et_user = (EditText) findViewById(R.id.user);
        et_password = (EditText) findViewById(R.id.password);
        et_password2 = (EditText) findViewById(R.id.password2);
        et_email = (EditText) findViewById(R.id.email);


    }

    public void zhuce(View view) {
        name = et_name.getText().toString().trim();
        user = et_user.getText().toString().trim();
        password = et_password.getText().toString().trim();
        password2 = et_password2.getText().toString().trim();
        email = et_email.getText().toString().trim();


        new Thread() {
            @Override
            public void run() {
                jdbcUtils = new Jdbcutils();
                String sql = "insert into userinfo (name,username, password,email) values (?,?,?,?) ";
                List<Object> params = new ArrayList<Object>();
                params.add(name);
                params.add(user);
                params.add(password);
                params.add(password2);
                params.add(email);
                zhuceService register = new zhuceService(params);
                String message = register.yanzheng();
                Message yanzheng = new Message();
                yanzheng.what = ERROR;
                yanzheng.obj = message;
                handler.sendMessage(yanzheng);
                if(message.equals("true")) {
                    try {
                        jdbcUtils.getConnection();
                        params.clear();
                        params.add(name);
                        params.add(user);
                        params.add(password);
                        params.add(email);
                        boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
                        System.out.println(flag);
                        Message msg = new Message();
                        msg.what = SUCCESS;
                        handler.sendMessage(msg);
                        Intent intent = new Intent(zhuce.this, login.class);
                        LoginService.SiveUserInfo(zhuce.this, user, password);
                        startActivity(intent);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        jdbcUtils.releaseConn();
                    }
                }
            }
        }.start();
    }
}
