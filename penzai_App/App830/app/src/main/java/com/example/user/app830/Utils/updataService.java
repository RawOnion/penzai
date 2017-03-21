package com.example.user.app830.Utils;


import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.user.app830.Utils.*;
import com.example.user.app830.mysql_db.UserInfo;

public class updataService {
    private Jdbcutils jdbcutils;
    private String sql;


    public updataService(Bitmap bitmap,String name) {
        //上传图片
        jdbcutils = new Jdbcutils();
        boolean flag;
        List<Object> params = new ArrayList<Object>();
        sql="update userinfo set img=? where name=?";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        params.add(baos.toByteArray());
        params.add(name);
        try {
            jdbcutils.getConnection();
            flag = jdbcutils.updateByPreparedStatement(sql, params);
            System.out.println(flag);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //jdbcutils.releaseConn();
        }
    }


}