package com.software.tongji.easygo.Achievement;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.software.tongji.easygo.basic.SingleFragmentActivity;

import org.litepal.LitePal;

public class AchievementActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.getDatabase();
    }

    @Override
    protected Fragment createFragment() {
        AchievementFragment fragment = new AchievementFragment();
        return fragment;
    }
}
