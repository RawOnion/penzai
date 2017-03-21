package com.example.user.app830.android_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper{

    private final static String DATABASE_NAME = "book_db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "book_mark";
    private final static String TABLE_SETUP = "book_setup";
    public final static String FIELD_ID = "_id";
    public final static String FIELD_FILENAME = "filename";//图书名称
    public final static String FIELD_BOOKMARK = "bookmark";//书签
    public final static String FONT_SIZE = "fontsize";//字体大小
    public final static String ROW_SPACE = "rowspace";//行间距
    public final static String COLUMN_SPACE = "columnspace";//字间距

    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sqlCreateCountTb = new StringBuffer();
        sqlCreateCountTb.append("create table ").append(TABLE_NAME)
                .append("(id integer primary key autoincrement,")
                .append(" filename varchar(64),")
                .append(" bookmark text);");
        db.execSQL(sqlCreateCountTb.toString());
        //String sql = "insert into " + TABLE_NAME + "(filename,bookmark) values('糗事百科.txt','0')";
        //db.execSQL(sql);
        //系统设置表
        StringBuffer setupTb = new StringBuffer();
        setupTb.append("create table ").append(TABLE_SETUP)
                .append("(id integer primary key autoincrement,")
                .append(" fontsize text,")
                .append(" rowspace text,")
                .append(" columnspace text);");
        db.execSQL(setupTb.toString());
        String setup = "insert into " + TABLE_SETUP + "(fontsize,rowspace,columnspace) values('6','0','0')";
        db.execSQL(setup);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
