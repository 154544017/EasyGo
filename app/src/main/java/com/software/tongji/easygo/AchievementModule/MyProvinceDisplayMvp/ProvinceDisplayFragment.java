package com.software.tongji.easygo.AchievementModule.MyProvinceDisplayMvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.Bean.Province;
import com.software.tongji.easygo.Bean.ProvinceLab;
import com.software.tongji.easygo.Navigation.NavigationActivity;

import java.util.List;

public class ProvinceDisplayFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.province_display_fragment,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        List<Province> provinces = ProvinceLab.getProvinceLab(getActivity()).getProvinces();
        ProvinceAdapter adapter = new ProvinceAdapter(provinces, (mode, province) -> {
            NavigationActivity activity = (NavigationActivity) getActivity();
            if (activity != null) {
                if (mode == 0) {
                    //0代表未解锁，跳转到该省份的景点介绍界面
                    activity.changeSearchFragmentWithArgs(province);
                } else {
                    //1代表已解锁，跳转到该省份的游记展示界面
                    activity.changeJournalFragmentWithArgs(province);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }
}
