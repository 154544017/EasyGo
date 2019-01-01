package com.software.tongji.easygo.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Path;

import com.software.tongji.easygo.utils.MapHelper;
import com.software.tongji.easygo.utils.SvgPathParser;
import com.software.tongji.easygo.utils.SvgUtil;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ProvinceLab {
    private static ProvinceLab sProvinceLab;
    private Context mContext;
    private List<Province> mProvinceList;

    public ProvinceLab(Context context) {
        mContext = context.getApplicationContext();
    }

    public static ProvinceLab getProvinceLab(Context context){
        if(sProvinceLab == null){
            sProvinceLab = new ProvinceLab(context);
        }
        return sProvinceLab;
    }

    public MyMap getMap(){

        mProvinceList = LitePal.findAll(Province.class);
        if(mProvinceList.size() > 10){
            return createMapFromProvinces();
        }else{
            return createMapFromSvg();
        }

    }

    public int getUnlockedSize(){
        List<Province> provinces = LitePal.where("mIsLocked = ?", "0")
                .find(Province.class);
        return provinces.size();
    }

    public List<Province> getProvinces(){
        if(mProvinceList == null){
            mProvinceList = LitePal.findAll(Province.class);
        }
        return mProvinceList;
    }

    public Province getProvinceByName(String provinceName){
        int index = MapHelper.provinceIndex.get(provinceName);
        return mProvinceList.get(index);
    }

    public void updateProvinceState(MyMap myMap, String provinceName, boolean isLocked){
        List<Province> provinces = LitePal.where("mName = ?", provinceName).find(Province.class);
        Province province = provinces.get(0);
        province.setLocked(isLocked);
        province.save();

        int index = MapHelper.provinceIndex.get(provinceName);
        mProvinceList.get(index).setLocked(isLocked);
        myMap.setProvinceList(mProvinceList);
    }

    private MyMap createMapFromProvinces(){
        MyMap myMap = new MyMap();
        try {
            for (Province province : mProvinceList) {
                List<Path> paths = new ArrayList<>();
                SvgPathParser svg = new SvgPathParser();
                for (String ss : province.getPathStringList()) {
                    paths.add(svg.parsePath(ss));
                }
                province.setPathList(paths);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SharedPreferences sf = mContext.getSharedPreferences("MAP_XY",Context.MODE_PRIVATE);
        float max_x = sf.getFloat("MAX_X",773.56f);
        float max_y = sf.getFloat("MAX_Y",568.7499f);
        float min_x = sf.getFloat("MIN_X",0);
        float min_y = sf.getFloat("MIN_Y",0);
        myMap.setMax_x(max_x);
        myMap.setMax_y(max_y);
        myMap.setMin_x(min_x);
        myMap.setMin_y(min_y);
        myMap.setProvinceList(mProvinceList);
        return myMap;
    }

    private MyMap createMapFromSvg(){
        MyMap myMap;

        myMap = new SvgUtil(mContext).getProvinces();
        mProvinceList = myMap.getProvinceList();
        LitePal.saveAll(mProvinceList);
        SharedPreferences sp = mContext.getSharedPreferences("MAP_XY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("MAX_X", myMap.getMax_x());
        editor.putFloat("MAX_Y",myMap.getMax_y());
        editor.putFloat("MIN_X",myMap.getMin_x());
        editor.putFloat("MIN_Y",myMap.getMin_y());
        editor.apply();

        return myMap;
    }
}

