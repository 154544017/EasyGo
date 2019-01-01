package com.software.tongji.easygo.MyProvinceDisplayMvp;

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
import com.software.tongji.easygo.bean.Province;
import com.software.tongji.easygo.bean.ProvinceLab;
import com.software.tongji.easygo.navigation.NavigationActivity;

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
                    activity.changeSearchFragmentWithArgs(province);
                } else {
                    activity.changeJournalFragmentWithArgs(province);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }
}
