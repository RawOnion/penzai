package com.example.user.app830.android_db;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.user.app830.Utils.Jdbcutils;
import com.example.user.app830.sqlite_dao.BookDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyApplication extends Application {
    private Jdbcutils jdbcutils;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    Map<String, Object> userinfo = null;
    String book = null;

    public Map<String, Object> getUserinfo() {

        return userinfo;
    }

    public void setUserinfo(Map<String, Object> userinfo) {
        if (userinfo.get("img") != null) {//将byte字节转换成图片
            byte[] image = (byte[]) userinfo.get("img");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length, options);
            userinfo.put("img", bitmap);
        }

        int sex = (int) userinfo.get("sex");
        if (sex == 0) {//为0则将“男”写入map
            userinfo.put("sex", "男");
        } else {//否则，将“女”写入map
            userinfo.put("sex", "女");
        }
        System.out.println(userinfo.get("sex"));

        //将int型age，转换成字符串
        int age = (int) userinfo.get("age");
        userinfo.put("age", String.valueOf(age));

        String qianming = (String) userinfo.get("qianming");
        String habit = (String) userinfo.get("habit");
        if (qianming.isEmpty())
            userinfo.put("qianming","无");
        if (habit.isEmpty())
            userinfo.put("habit","无");

        book = (String) userinfo.get("book");
        new Thread() {//从MySql中导入图书到Sqlite
            @Override
            public void run() {
                Map<String, Object> map = null;
                Jdbcutils jdbcutils = new Jdbcutils();
                book = book.replaceAll(" ", "");
                try {
                    jdbcutils.getConnection("flowers");
                    String[] infos = book.split("#");
                    for (int i = 0; i < infos.length; i++) {
                        if (infos[i] != "") {
                            map = jdbcutils.findSimpleResult("select * from pot_book where book_name='" + infos[i] + "'", null);
                            String bookname = (String) map.get("book_name");
                            String bookcontent = (String) map.get("book_content");
                            System.out.println("book_name" + bookname + "i = " + i);
                            BookDao Dao = new BookDao(getApplicationContext());
                            if (!Dao.find(bookname))
                                Dao.add(bookname, bookcontent);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    jdbcutils.releaseConn();
                }
            }
        }.start();
        this.userinfo = userinfo;
    }

    public void onCreate() {
        super.onCreate();
        new Thread() {
            @Override
            public void run() {
                jdbcutils = new Jdbcutils();
                String sql = "select f_name,photo,habit from pot_data";
                try {
                    jdbcutils.getConnection("flowers");
                    list = jdbcutils.findModeResult(sql, null);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    for (int i = 0; i < list.size(); i++) {
                        byte[] image = (byte[]) list.get(i).get("photo");
                        list.get(i).put("photo", BitmapFactory.decodeByteArray(image, 0, image.length, options));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    jdbcutils.releaseConn();
                }

            }
        }.start();


    }

    public List<Map<String, Object>> getList() {

        return list;
    }
}
