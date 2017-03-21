package com.example.user.app830.android_db;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.app830.R;
import com.example.user.app830.sqlite_dao.BookDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class BookActivity extends Activity {

    private List<BookInfo> bookInfos = null;
    private List<Map<String, Object>> books = new ArrayList<Map<String, Object>>();;
    private Map<String,Object> book = new HashMap<String,Object>();
    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookread);
        listView = (ListView) findViewById(R.id.bookread);
        Intent intent = getIntent();
        int id = Integer.parseInt((String) intent.getStringExtra("bookid"));
        BookDao bookDao = new BookDao(this);
        bookInfos = bookDao.findSimple(id);
        String bookname = bookInfos.get(0).getBookname();
        String bookmark = bookInfos.get(0).getBookmark();
        System.out.println("bookname="+bookname);
        book.put("bookname",bookname);
        book.put("bookmark",bookmark);
        books.add(book);
        SimpleAdapter Adapter = new SimpleAdapter(BookActivity.this,books , R.layout.bookread_adapter, new String[]{"bookname","bookmark"}, new int[]{R.id.bookread_title,R.id.bookread_content});
        listView.setAdapter(Adapter);
        Adapter.notifyDataSetChanged();
    }
}