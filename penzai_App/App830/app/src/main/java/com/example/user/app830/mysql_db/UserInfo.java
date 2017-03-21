package com.example.user.app830.mysql_db;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.Arrays;

public class UserInfo  {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private byte[] img;
    private String qianming;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQianming() {
        return qianming;
    }

    public void setQianming(String qianming) {
        this.qianming = qianming;
    }

    public Bitmap getImg() {
        if (img.length != 0) {
            return BitmapFactory.decodeByteArray(img, 0, img.length);
        } else {
            return null;
        }

    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", qianming='" + qianming + '\'' +
                ", id=" + id +
                '}';
    }
}
