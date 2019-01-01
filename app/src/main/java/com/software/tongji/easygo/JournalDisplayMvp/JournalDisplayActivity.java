package com.software.tongji.easygo.JournalDisplayMvp;

import android.support.v4.app.Fragment;

import com.software.tongji.easygo.basic.SingleFragmentActivity;

public class JournalDisplayActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new JournalDisplayFragment();
    }
}
