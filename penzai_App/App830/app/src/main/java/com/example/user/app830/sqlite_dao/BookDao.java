package com.example.user.app830.sqlite_dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.app830.android_db.BookInfo;
import com.example.user.app830.android_db.DbOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2015/10/3.
 */
public class BookDao {
    private DbOpenHelper dbOpenHelper = null;

    public BookDao(Context context) {
        dbOpenHelper = new DbOpenHelper(context);
    }

    public void add(String BookName, String BookContent) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("insert into book_mark (filename,bookmark) values (?,?)", new Object[]{BookName, BookContent});
        db.close();
    }

    public boolean find(String BookName) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id from book_mark where filename =?", new String[]{BookName});
        boolean result = cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
    }

    public void delete(String BookName) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("delete from book_mark where filename =?", new Object[]{BookName});
        db.close();
    }

    public List<BookInfo> findAll() {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        List<BookInfo> bookInfos = new ArrayList<BookInfo>();
        Cursor cursor = db.rawQuery("select * from book_mark", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String bookname = cursor.getString(1);
            String bookmark = cursor.getString(2);
            BookInfo bookInfo = new BookInfo(id, bookname, bookmark);
            bookInfos.add(bookInfo);
        }
        cursor.close();
        db.close();
        return bookInfos;
    }

    public List<BookInfo> findSimple(int id) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        List<BookInfo> bookInfos = new ArrayList<BookInfo>();
        Cursor cursor = db.rawQuery("select * from book_mark where id =?", new String[]{"" + id});
        while (cursor.moveToNext()) {
            String bookname = cursor.getString(1);
            String bookmark = cursor.getString(2);
            BookInfo bookInfo = new BookInfo(id, bookname, bookmark);
            bookInfos.add(bookInfo);
        }
        cursor.close();
        db.close();
        return bookInfos;
    }
}
