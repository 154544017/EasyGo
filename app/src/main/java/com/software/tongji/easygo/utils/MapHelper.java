package com.software.tongji.easygo.utils;

import java.util.HashMap;
import java.util.Random;

public class MapHelper {
    public static HashMap<String,Integer> provinceIndex= new HashMap<String,Integer>(){
        {
            put("安徽省",0);put("北京市",1);put("重庆市",2);put("福建省",3);put("广东省",4);
            put("甘肃省",5);put("广西省",6);put("贵州省",7);put("海南省",8);put("河北省",9);
            put("河南省",10);put("香港",11);put("黑龙江",12);put("湖南省",13);put("湖北省",14);
            put("吉林省",15);put("江苏省",16);put("江西省",17);put("辽宁省",18);put("澳门",19);
            put("内蒙古",20);put("宁夏区",21);put("青海省",22);put("陕西省",23);put("四川省",24);
            put("山东省",25);put("上海市",26);put("山西省",27);put("天津市",28);put("台湾",29);
            put("新疆区",30);put("西藏区",31);put("云南省",32);put("浙江省",33);
        }
    };
    public static HashMap<String, String> province_color = new HashMap<>();

    /**  
          * 获取十六进制的颜色代码.例如  "#6E36B4" , For HTML ,  
          * @return String  
          */
    public static String getRandColorCode() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        return "#"+ r + g + b;
    }

}
