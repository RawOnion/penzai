package com.example.user.app830.android_db;

/**
 * 表示一个文件实体 *
 */
public class BookInfo {
    public int id;
    public String bookname;
    public String bookmark;

    public BookInfo() {

    }

    public BookInfo(int id, String bookname, String bookmark) {
        this.id = id;
        this.bookname = bookname;
        this.bookmark = bookmark;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }
}