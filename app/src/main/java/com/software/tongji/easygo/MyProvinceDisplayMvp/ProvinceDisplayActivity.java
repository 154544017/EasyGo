package com.software.tongji.easygo.MyProvinceDisplayMvp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.software.tongji.easygo.ProvinceDialog.ProvinceDialogFragment;
import com.software.tongji.easygo.basic.SingleFragmentActivity;

import org.litepal.LitePal;

public class ProvinceDisplayActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LitePal.getDatabase();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        ProvinceDisplayFragment fragment = new ProvinceDisplayFragment();
        return fragment;
    }

}
