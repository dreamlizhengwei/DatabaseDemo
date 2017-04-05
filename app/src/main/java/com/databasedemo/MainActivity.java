package com.databasedemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private DBHelper helper;
    private int param = 0;
    static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.content);
        helper = DBHelper.getInstance(this);
        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0;i<10;i++){

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            param++;
                            final int param2 = param;
                            final long id = helper.insert("----> " + param2);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tv.append(id + ":" + param2 + "\n");
                                }
                            });
                        }
                    }).start();
                }
            }
        });
    }
}
