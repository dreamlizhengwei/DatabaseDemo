package com.databasedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DBHelper extends SQLiteOpenHelper{

    private static String DBName = "TestDB";
    private static int DBVersion = 1;
    private SQLiteDatabase db;
    private static DBHelper INSTANCE;
    // 不用的时候需要关闭线程池
    private ExecutorService pool;

    private DBHelper(Context context) {
        super(context, DBName, null, DBVersion);
        db = getReadableDatabase();
        pool = Executors.newSingleThreadExecutor();
    }

    public static DBHelper getInstance(Context context){
        if(INSTANCE == null){
            synchronized (DBHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new DBHelper(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 模拟一个插入，参数随便写了，返回值和SQLiteDatabase的插入方法一样都是long类型，表示插入的id
    public long insert(final String param){
        Future<Long> submit = pool.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                Thread.sleep(1000);
                Log.e("lzw","插入 param: " + param);
                return 100L;
            }
        });
        long id = -1;
        try {
            // 这里会阻塞
            id = submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return  id;
    }
}
