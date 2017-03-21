package com.example.user.app830;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.user.app830.Utils.Jdbcutils;
import com.example.user.app830.android_db.BookActivity;
import com.example.user.app830.android_db.BookInfo;
import com.example.user.app830.android_db.MyApplication;
import com.example.user.app830.sqlite_dao.BookDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class data extends Fragment {

    private ShelfAdapter mAdapter;
    private ImageView add;
    private ListView shelf_list;

    int[] size = null;
    BookDao db;
    List<BookInfo> books;
    int realTotalRow;
    int bookNumber; //图书的数量
    final String[] font = new String[]{"20", "24", "26", "30", "32", "36",
            "40", "46", "50", "56", "60", "66", "70"};
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    refreshShelf((BookDao)msg.obj);
                    break;
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_layout, null);
        db = new BookDao(getActivity());
        add = (ImageView) getActivity().findViewById(R.id.add);
        shelf_list = (ListView) view.findViewById(R.id.shelf_list);


        /**************初始化书架图书*********************/
        books = db.findAll();//取得所有的图书
        bookNumber = books.size();
        int count = books.size();
        int totalRow = count / 3;
        if (count % 3 > 0) {
            totalRow = count / 3 + 1;
        }
        realTotalRow = totalRow;
        if (totalRow < 3) {
            totalRow = 3;
        }
        size = new int[totalRow];
        /***********************************/
        mAdapter = new ShelfAdapter();//new adapter对象才能用

        //注册ContextView到view中
        shelf_list.setAdapter(mAdapter);
        return view;
    }

    public class ShelfAdapter extends BaseAdapter {

        public ShelfAdapter() {
        }

        @Override
        public int getCount() {
            if (size.length > 3) {
                return size.length;
            } else {
                return 3;
            }
        }

        @Override
        public Object getItem(int position) {
            return size[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View layout = View.inflate(getActivity(), R.layout.shelf_list_item, null);
            if (position < realTotalRow) {
                int buttonNum = (position + 1) * 3;
                if (bookNumber <= 3) {
                    buttonNum = bookNumber;
                }
                System.out.println("buttonNum:" + buttonNum);
                for (int i = 0; i < buttonNum; i++) {
                    if (i == 0) {
                        System.out.println("position:" + position);
                        BookInfo book = books.get(position * 3);
                        String buttonName = book.bookname;
                        Button button = (Button) layout.findViewById(R.id.button_1);
                        button.setVisibility(button.VISIBLE);
                        button.setText(buttonName);
                        System.out.println("bookname:" + buttonName);
                        button.setId(book.id);
                        button.setOnClickListener(new ButtonOnClick());
                        button.setOnCreateContextMenuListener(listener);
                    } else if (i == 1) {
                        System.out.println("position:" + position);
                        BookInfo book = books.get(position * 3 + 1);
                        String buttonName = book.bookname;
                        Button button = (Button) layout.findViewById(R.id.button_2);
                        button.setVisibility(button.VISIBLE);
                        button.setText(buttonName);
                        System.out.println("bookname:" + buttonName);
                        button.setId(book.id);
                        button.setOnClickListener(new ButtonOnClick());
                        button.setOnCreateContextMenuListener(listener);
                    } else if (i == 2) {
                        System.out.println("position:" + position);
                        BookInfo book = books.get(position * 3 + 2);
                        String buttonName = book.bookname;
                        Button button = (Button) layout.findViewById(R.id.button_3);
                        button.setVisibility(button.VISIBLE);
                        button.setText(buttonName);
                        System.out.println("bookname:" + buttonName);
                        button.setId(book.id);
                        button.setOnClickListener(new ButtonOnClick());
                        button.setOnCreateContextMenuListener(listener);
                    }
                }
                bookNumber -= 3;
            }
            return layout;
        }
    }

    ;
    ;

    //添加长按点击
    View.OnCreateContextMenuListener listener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            //menu.setHeaderTitle(String.valueOf(v.getId()));
            menu.add(0, 1, v.getId(), "删除本书");
        }
    };

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case 0:

                break;
            case 1:
                Dialog dialog = new AlertDialog.Builder(getActivity()).setTitle(
                        "提示").setMessage(
                        "确认要删除吗？").setPositiveButton(
                        "确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //删除图书
                                new Thread() {
                                    @Override
                                    public void run() {
                                        BookDao bookDao = new BookDao(getActivity());
                                        List<BookInfo> bookInfos = bookDao.findSimple(item.getOrder());
                                        bookDao.delete(bookInfos.get(0).bookname);
                                        Jdbcutils jdbcutils = new Jdbcutils();
                                        MyApplication application = (MyApplication) getActivity().getApplication();
                                        Map<String,Object> map = application.getUserinfo();
                                        String book = (String) map.get("book");
                                        String username = (String) map.get("username");
                                        book = book.replace(bookInfos.get(0).bookname+"#","");
                                        String sql = "update userinfo set book ='"+book+"' where username ='"+username+"'";
                                        try {
                                            jdbcutils.getConnection();
                                            jdbcutils.updateByPreparedStatement(sql,null);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        } finally {
                                            jdbcutils.releaseConn();
                                            Message msg = new Message();
                                            msg.what = 1;
                                            msg.obj=bookDao;
                                            handler.sendMessage(msg);
                                        }
                                    }
                                }.start();

                            }
                        }).setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create();// 创建按钮
                dialog.show();
                break;
            default:
                break;
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 222) {
            String isImport = data.getStringExtra("isImport");
            if ("1".equals(isImport)) {
                refreshShelf();
            }
        }
    }

    //重新加载书架
    public void refreshShelf() {}
    public void refreshShelf(BookDao bookDao) {

        /**************初始化书架图书*********************/
        books = bookDao.findAll();//取得所有的图书
        bookNumber = books.size();
        System.out.println("books.size()"+books.size());
        int count = books.size();
        int totalRow = count / 3;
        if (count % 3 > 0) {
            totalRow = count / 3 + 1;
        }
        realTotalRow = totalRow;
        if (totalRow < 4) {
            totalRow = 4;
        }
        size = new int[totalRow];
        /***********************************/
        mAdapter = new ShelfAdapter();//new adapter对象才能用
        shelf_list.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();
    }

    public class ButtonOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            switch ( v.getId () ) {
//	            case 1 :
            Intent intent = new Intent();
            intent.setClass(getActivity(), BookActivity.class);
            intent.putExtra("bookid", String.valueOf(v.getId()));
            startActivity(intent);
        }
    }
}
