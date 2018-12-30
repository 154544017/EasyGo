package com.software.tongji.easygo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.software.tongji.easygo.bean.Attraction;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String mmm = java.net.URLEncoder.encode("上海市");
        Log.e("shanghai", mmm);

        setContentView(R.layout.activity_main);
    }
}
