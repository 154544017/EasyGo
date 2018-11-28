package com.software.tongji.easygo.MapMvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.software.tongji.easygo.basic.SingleFragmentActivity;

import org.litepal.LitePal;

public class MapActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.getDatabase();
    }

    @Override
    protected Fragment createFragment() {
        MapFragment fragment = new MapFragment();
        new MapPresenterImpl(MapActivity.this, fragment);
        return fragment;
    }
}

