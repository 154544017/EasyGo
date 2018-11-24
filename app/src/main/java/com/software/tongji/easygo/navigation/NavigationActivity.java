package com.software.tongji.easygo.navigation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.SearchView;

import com.eftimoff.viewpagertransformers.DepthPageTransformer;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.attractions.AttractionsListFragment;

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
        setContentView(R.layout.activity_navigation);

        ButterKnife.bind(this);

        mNavigationPresenter = new NavigationPresenter();

        mHandler = new Handler(Looper.getMainLooper());

        NavigationController navigationController = mPageNavigationView.material()
                .addItem(R.drawable.ic_format_list_bulleted_black_24dp, "行程")
                .addItem(R.drawable.ic_edit_location_black_24dp, "足迹")
                .addItem(R.drawable.ic_search_black_24dp, "搜索")
                .build();
        navigationController.setupWithViewPager(mNavigationViewPager);
        setupViewPager();
        navigationController.setupWithViewPager(mNavigationViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.attractions_search, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.attractions_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setupViewPager() {
        mHandler.post(()->{
            List<Fragment> fragmentList = new ArrayList<>();
            for(int i = 0; i < 2; i++){
                fragmentList.add(new Fragment());
            }
            fragmentList.add(new AttractionsListFragment());
            mNavigationViewPager.setAdapter(new NavigationPagerAdapter(NavigationActivity.this.getSupportFragmentManager(), fragmentList));
            mNavigationViewPager.setPageTransformer(true, new DepthPageTransformer());
        });
    }
}
