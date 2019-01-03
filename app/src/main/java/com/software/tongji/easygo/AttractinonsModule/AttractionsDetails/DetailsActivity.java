package com.software.tongji.easygo.AttractinonsModule.AttractionsDetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.software.tongji.easygo.Bean.Attraction;
import com.software.tongji.easygo.TravelPlanModule.NewSchedule.NewScheduleActivity;
import com.software.tongji.easygo.Utils.MapHelper;
import com.youth.banner.Banner;
import com.software.tongji.easygo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity{

    private static final String EXTRA_ATTRACTION = "com.software.tongji.easygo.extra_attraction";
    @BindView(R.id.details_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.details_banner)
    Banner mBanner;

    @BindView(R.id.details_address)
    TextView mAddress;
    @BindView(R.id.details_introduction)
    TextView mIntroduction;
    @BindView(R.id.details_best_time)
    TextView mBestTime;
    @BindView(R.id.floating_add_button)
    FloatingActionButton mAddToScheduleButton;

    private List<String> images = new ArrayList<>();

    public static Intent newIntent(Context packageContext, Attraction attraction){
        Intent intent = new Intent(packageContext,DetailsActivity.class);
        intent.putExtra(EXTRA_ATTRACTION, attraction);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //从Intent中获取该景点
        Attraction attraction = (Attraction) getIntent().getSerializableExtra(EXTRA_ATTRACTION);

        if(Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_details);

        images.addAll(attraction.getImages());

        ButterKnife.bind(this);

        mToolbar.setTitle(attraction.getName());
        setSupportActionBar(mToolbar);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setImages(images);
        mBanner.start();

        mAddress.setText(MapHelper.provinceHanzi.get(attraction.getProvince()) + attraction.getCity());
        mIntroduction.setText(attraction.getIntroduction());
        mBestTime.setText(attraction.getBestTime());
        mAddToScheduleButton.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsActivity.this, NewScheduleActivity.class);
            startActivity(intent);
        });

    }
}
