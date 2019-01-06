package com.software.tongji.easygo.AchievementModule.Achievement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.tongji.easygo.AchievementModule.MapMvp.MapFragment;
import com.software.tongji.easygo.AchievementModule.MapMvp.MapPresenterImpl;
import com.software.tongji.easygo.AchievementModule.MyProvinceDisplayMvp.ProvinceDisplayFragment;
import com.software.tongji.easygo.R;

import butterknife.Unbinder;

//成就模块的控制fragment，用于显示成就地图还是成就省份列表
public class AchievementFragment extends Fragment{
    private Unbinder mUnBinder;
    private FragmentManager mFragmentManager;

    private TabItem mMapButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.achievement_fragment,container,false);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    MapFragment mapFragment = new MapFragment();
                    new MapPresenterImpl(getActivity(), mapFragment);
                    changeFragment(mapFragment);
                }else{
                    ProvinceDisplayFragment displayFragment = new ProvinceDisplayFragment();
                    changeFragment(displayFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mFragmentManager = getActivity().getSupportFragmentManager();

        MapFragment mapFragment = new MapFragment();
        new MapPresenterImpl(getActivity(), mapFragment);
        mFragmentManager.beginTransaction()
                .add(R.id.achievement_fragment_container,mapFragment)
                .commit();

        return view;
    }


    private void changeFragment(Fragment fragment){
        Fragment oldFragment = mFragmentManager.findFragmentById(R.id.achievement_fragment_container);
        if (oldFragment != null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.achievement_fragment_container, fragment)
                    .commit();
        } else {
            mFragmentManager.beginTransaction()
                    .add(R.id.achievement_fragment_container, fragment)
                    .commit();
        }
    }

}
