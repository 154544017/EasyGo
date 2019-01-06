package com.software.tongji.easygo.InputTipModule.InputProvince;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputProvinceAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private TestFilter mTestFilter;
    private List<String> mListProvinces;
    private List<String> mTempProvinces;

    public InputProvinceAdapter(Context context) {
        mContext = context;
        ArrayList<String> provinces = new ArrayList<>(Arrays.asList(
                "显示全部","北京市","重庆市","福建省","广东省",
                "甘肃省","广西省","贵州省","海南省","河北省",
                "河南省","香港","黑龙江","湖南省","湖北省",
                "吉林省","江苏省","江西省","辽宁省","澳门",
                "内蒙古","宁夏区","青海省","陕西省","四川省",
                "山东省","上海市","山西省","天津市","台湾",
                "新疆区","西藏区","云南省","浙江省","安徽省"
        ));
        mListProvinces = provinces;
        mTempProvinces = provinces;
    }

    @Override
    public int getCount() {
        if (mTempProvinces != null) {
            return mTempProvinces.size();
        }
        return 0;
    }


    @Override
    public Object getItem(int i) {
        if (mTempProvinces != null) {
            return mTempProvinces.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
        }
        TextView provinceName = convertView.findViewById(android.R.id.text1);
        if (mTempProvinces == null) {
            return convertView;
        }

        provinceName.setText(mTempProvinces.get(position));
        return convertView;

    }

    @Override
    public Filter getFilter() {
        if(mTestFilter == null){
            mTestFilter = new TestFilter();
        }
        return mTestFilter;
    }

    class TestFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<String> newProvince = new ArrayList<>();
            //遍历Provinces，如果包含constraint，就添加到newProvince
            if (constraint != null && constraint.toString().trim().length() > 0) {
                for (int i = 0; i < mListProvinces.size(); i++) {
                    String content = mListProvinces.get(i);
                    if (content.contains(constraint)) {
                        newProvince.add(content);
                    }
                }
            }else {
                newProvince=mListProvinces;
            }
            FilterResults filterResults = new FilterResults();
            filterResults.count = newProvince.size();
            filterResults.values = newProvince;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //这里对ProvinceList进行过滤后重新赋值
            mTempProvinces = (List) results.values;
            //如果过滤后的返回的值的个数大于等于0的话,对Adapter的界面进行刷新
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                //否则说明没有任何过滤的结果,直接提示用户"没有符合条件的结果"
                mTempProvinces = new ArrayList(){};
                mTempProvinces.add(": ( 没有符合条件的结果");
                notifyDataSetChanged();
            }

        }
    }

}
