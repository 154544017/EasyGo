package com.software.tongji.easygo.details;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.software.tongji.easygo.newschedule.NewScheduleActivity;
import com.youth.banner.Banner;
import com.software.tongji.easygo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity{

    @BindView(R.id.details_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.details_collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    @BindView(R.id.details_banner)
    Banner mBanner;

    @BindView(R.id.details_address)
    TextView mAddress;
    @BindView(R.id.details_introduction)
    TextView mIntroduction;
    @BindView(R.id.details_telephone)
    TextView mTelephone;
    @BindView(R.id.details_transport)
    TextView mTransport;
    @BindView(R.id.floating_add_button)
    FloatingActionButton mAddToScheduleButton;

    List<Integer> images = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_details);

        images.add(R.drawable.attractions_picture1);
        images.add(R.drawable.login_background);
        images.add(R.drawable.login_background2);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setImages(images);
        mBanner.start();

        mAddToScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, NewScheduleActivity.class);
                startActivity(intent);
            }
        });

    }
}