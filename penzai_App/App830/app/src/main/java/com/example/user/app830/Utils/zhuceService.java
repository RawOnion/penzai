package com.example.user.app830.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class zhuceService {

    private static final int SUCCESS = 0;
    private static final int ERROR = 1;

    String name;
    String username;
    String password;
    String password2;
    String email;
    List<Object> params = new ArrayList<Object>();
    Map<String, Object> map;
    ;

    private Jdbcutils jdbcutils;

    public zhuceService(List<Object> params) {

        jdbcutils = new Jdbcutils();
        jdbcutils.getConnection();
        name = (String) params.get(0);
        username = (String) params.get(1);
        password = (String) params.get(2);
        password2 = (String) params.get(3);
        email = (String) params.get(4);
    }

    public String yanzheng() { //用户输入验证
        String msg = "true";
        int flag;
        //昵称验证
        String sql = "select id from userinfo where name=?";
        params.add(name);
        flag = cunzai(sql, params);
        params.clear();
        if (flag == ERROR) {
            jdbcutils.releaseConn();
            return "昵称已存在";
        }
        //用户名验证
        if (!username.matches("[A-Za-z0-9_]+")) {
            jdbcutils.releaseConn();
            return "用户名只能由数字字母和下划线组成";
        }
        if (username.length() < 5) {
            jdbcutils.releaseConn();
            return "用户名必须大于5个字符";
        }
        sql = "select id from userinfo where username=?";
        params.add(username);

        flag = cunzai(sql, params);
        if (flag == ERROR) {
            jdbcutils.releaseConn();
            return "用户名已存在";
        }
        //密码验证
        if (!password.equals(password2) ) {
            jdbcutils.releaseConn();
            return "两次密码不一致";
        }
        //邮箱验证
        if (!email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) {
            jdbcutils.releaseConn();
            return "请输入正确的邮箱格式";
        }
        createUserDb();
        jdbcutils.releaseConn();
        return msg;
    }

    public int cunzai(String sql, List<Object> params) {
        int flag = ERROR;
        try {
            map = jdbcutils.findSimpleResult(sql, params);
            flag = map.isEmpty() ? SUCCESS : ERROR;
            map.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return flag;
        }
    }

    public boolean createUserDb() { //创建用户数据表
        jdbcutils.getConnection();
        boolean flag = false;
        params.clear();
        String sql = "create table "+username+"(id int primary key not null auto_increment,title varchar(64),content varchar(256),img mediumblob,shijian datetime);";
        try {
            jdbcutils.updateByPreparedStatement(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcutils.releaseConn();
        }
        return flag;
    }
}
