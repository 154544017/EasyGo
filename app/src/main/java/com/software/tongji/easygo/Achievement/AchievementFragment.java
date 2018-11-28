package com.software.tongji.easygo.Achievement;

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
import android.widget.Button;
import android.widget.TableLayout;

import com.software.tongji.easygo.MapMvp.MapFragment;
import com.software.tongji.easygo.MapMvp.MapPresenterImpl;
import com.software.tongji.easygo.MyProvinceDisplayMvp.ProvinceDisplayFragment;
import com.software.tongji.easygo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AchievementFragment extends Fragment {
    private Unbinder mUnbinder;
    private FragmentManager mFragmentManager;

    private TabLayout mTabLayout;
    private TabItem mMapButton;
    private TabItem mDisplayButton;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.achievement_fragment,container,false);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        mDisplayButton = view.findViewById(R.id.display);
        mTabLayout = view.findViewById(R.id.tab_layout);

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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
