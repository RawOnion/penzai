package com.example.user.app830.Utils;


import android.content.Context;

import com.example.user.app830.mysql_db.UserInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginService {

    private Jdbcutils jdbcutils;
    private  Map<String, Object> userInfo = null;


    public boolean login(List<Object> params) {

        //将用户名密码从服务器验证
        jdbcutils = new Jdbcutils();
        boolean flag = false;
        String sql = "select id from userinfo where username=? and password=?";
        try {
            jdbcutils.getConnection();
            Map<String, Object> map = jdbcutils.findSimpleResult(sql, params);
            flag = !map.isEmpty() ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcutils.releaseConn();
        }
        return flag;
    }

    public  Map<String, Object> downdata(List<Object> params) {

        jdbcutils = new Jdbcutils();
        String sql = "select * from userinfo where username =? ";
        try {
            jdbcutils.getConnection();
            userInfo = jdbcutils.findSimpleResult(sql, params);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            jdbcutils.releaseConn();
        }
        return userInfo;
    }

    public static boolean SiveUserInfo(Context context, String name, String password) {
        //本地保存用户名密码
        try {
            File file = new File(context.getCacheDir(), "info.dat");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write((name + "###" + password).getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Map<String, String> getSiveUserInfo(Context context) {
        //获得本地保存的用户名密码
        File file = new File(context.getCacheDir(), "info.dat");
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String str = br.readLine();
            String[] infos = str.split("###");
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", infos[0]);
            map.put("password", infos[1]);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}