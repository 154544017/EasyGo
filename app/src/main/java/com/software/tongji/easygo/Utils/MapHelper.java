package com.software.tongji.easygo.Utils;

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
    public static HashMap<String, String> provincePinYin = new HashMap<String, String>(){
        {
            put("安徽省","anhui");put("北京市","beijing");put("重庆市","chongqing");put("福建省","fujian");put("广东省","guangdong");
            put("甘肃省","gansu");put("广西省","guangxi");put("贵州省","guizhou");put("海南省","hainan");put("河北省","hebei");
            put("河南省","henan");put("香港","xianggang");put("黑龙江","heilongjiang");put("湖南省","hunan");put("湖北省","hubei");
            put("吉林省","jilin");put("江苏省","jiangsu");put("江西省","jiangxi");put("辽宁省","liaoning");put("澳门","aomen");
            put("内蒙古","neimenggu");put("宁夏区","ningxia");put("青海省","qinghai");put("陕西省","shaanxi");put("四川省","sichuan");
            put("山东省","shandong");put("上海市","shanghai");put("山西省","shanxi");put("天津市","tianjin");put("台湾","taiwan");
            put("新疆区","xinjiang");put("西藏区","xizang");put("云南省","yunnan");put("浙江省","zhejiang");
        }
    };

    public static HashMap<String, String> provinceHanzi = new HashMap<String, String>(){
        {
            put("anhui","安徽省");put("beijing","北京市");put("chongqing","重庆市");put("fujian","福建省");put("guangdong","广东省");
            put("gansu","甘肃省");put("guangxi","广西省");put("guizhou","贵州省");put("hainan","海南省");put("hebei","河北省");
            put("henan","河南省");put("xianggang","香港");put("heilongjiang","黑龙江");put("hunan","湖南省");put("hubei","湖北省");
            put("jilin","吉林省");put("jiangsu","江苏省");put("jiangxi","江西省");put("liaoning","辽宁省");put("aomen","澳门");
            put("neimenggu","内蒙古");put("ningxia","宁夏区");put("qinghai","青海省");put("shaanxi","陕西省");put("sichuan","四川省");
            put("shandong","山东省");put("shanghai","上海市");put("shanxi","山西省");put("tianjin","天津市");put("taiwan","台湾");
            put("xinjiang","新疆区");put("xizang","西藏区");put("yunnan","云南省");put("zhejiang","浙江省");
        }
    };

    public static HashMap<String,String> provinceBrief = new HashMap<String, String>(){
        {
            put("安徽","安徽省");put("北京","北京市");put("重庆","重庆市");put("福建","福建省");put("广东","广东省");
            put("甘肃","甘肃省");put("广西","广西省");put("贵州","贵州省");put("海南","海南省");put("河北","河北省");
            put("河南","河南省");put("香港","香港");put("黑龙","黑龙江");put("湖南","湖南省");put("湖北","湖北省");
            put("吉林","吉林省");put("江苏","江苏省");put("江西","江西省");put("辽宁","辽宁省");put("澳门","澳门");
            put("内蒙","内蒙古");put("宁夏","宁夏区");put("青海","青海省");put("陕西","陕西省");put("四川","四川省");
            put("山东","山东省");put("上海","上海市");put("山西","山西省");put("天津","天津市");put("台湾","台湾");
            put("新疆","新疆区");put("西藏","西藏区");put("云南","云南省");put("浙江","浙江省");
        }
    };
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
