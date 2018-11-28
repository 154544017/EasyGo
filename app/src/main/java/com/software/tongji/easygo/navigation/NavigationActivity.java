package com.software.tongji.easygo.navigation;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;

import com.eftimoff.viewpagertransformers.DepthPageTransformer;
import com.software.tongji.easygo.Achievement.AchievementFragment;
import com.software.tongji.easygo.JournalDisplayMvp.JournalDisplayFragment;
import com.software.tongji.easygo.MapMvp.MapFragment;
import com.software.tongji.easygo.MyProvinceDisplayMvp.ProvinceDisplayFragment;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.attractions.AttractionsListFragment;
import com.software.tongji.easygo.schedule.ScheduleListFragment;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;

public class NavigationActivity extends AppCompatActivity implements NavigationView{

    @BindView(R.id.navigation_view_pager)
    ViewPager mNavigationViewPager;
    @BindView(R.id.bottom_tab_strip)
    PageNavigationView mPageNavigationView;

    private NavigationPresenter mNavigationPresenter;
    private Handler mHandler;

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
        setContentView(R.layout.activity_navigation);
        LitePal.getDatabase();

        ButterKnife.bind(this);

        mNavigationPresenter = new NavigationPresenter();

        mHandler = new Handler(Looper.getMainLooper());

        NavigationController navigationController = mPageNavigationView.material()
                .addItem(R.drawable.ic_format_list_bulleted_black_24dp, "行程")
                .addItem(R.drawable.ic_edit_location_black_24dp, "足迹")
                .addItem(R.drawable.ic_collections_black_24dp, "游记")
                .addItem(R.drawable.ic_search_black_24dp, "搜索")
                .build();
        navigationController.setupWithViewPager(mNavigationViewPager);
        setupViewPager();
        navigationController.setupWithViewPager(mNavigationViewPager);
    }

    public void changeFragment(){
        mNavigationViewPager.setCurrentItem(2);
    }
    @Override
    public void setupViewPager() {
        mHandler.post(()->{
            List<Fragment> fragmentList = new ArrayList<>();
            fragmentList.add(new ScheduleListFragment());
            fragmentList.add(new AchievementFragment());
            fragmentList.add(new JournalDisplayFragment());
            fragmentList.add(new AttractionsListFragment());
            mNavigationViewPager.setAdapter(new NavigationPagerAdapter(NavigationActivity.this.getSupportFragmentManager(), fragmentList));
            mNavigationViewPager.setPageTransformer(true, new DepthPageTransformer());
        });
    }
}
