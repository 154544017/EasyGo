package com.software.tongji.easygo.Utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//由于使用LitePal后，用Gson将Model转化为Json时会多很多没用的信息，如baseObjId等
//于是自定义MyLitepalGson来过滤这些无用信息
public class MyLitepalGson extends MyGson {

    public MyLitepalGson() {
        super(customizeGson());
    }


    private static String[] litepalExcludes = {"baseObjId", "associatedModelsMapWithFK",
            "associatedModelsMapWithoutFK", "associatedModelsMapForJoinTable",
            "listToClearSelfFK", "listToClearAssociatedFK", "fieldsToSetToDefault"};
    private static Gson customizeGson() {
        return new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                for (String exclude : litepalExcludes) {
                    if (exclude.equals(f.getName())) {
                        return true;
                    }
                }
                return false;
            }
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
    }

}


