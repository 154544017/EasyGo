package com.software.tongji.easygo.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.util.Log;

import com.software.tongji.easygo.basic.Const;
import com.software.tongji.easygo.bean.MyMap;
import com.software.tongji.easygo.bean.Province;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/*
 *根据assets中的China.svg文件，解析出每个省份（名称，路径信息，大小，点集等信息）
 * 并构建出一个MyMap的实例
 */
public class SvgUtil {
    private Context context;
    private float Max_X,Min_x,Max_y,Min_y;

    public SvgUtil(Context context){
        this.context=context;
    }
    public MyMap getProvinces(){
        MyMap map=new MyMap();
        int index = 0;
        try {
            InputStream inputStream= context.getResources().getAssets().open("china.svg");
            DocumentBuilder myBuilder= DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document myDoc=myBuilder.parse(inputStream);
            //找到根Element
            Element root=myDoc.getDocumentElement();
            NodeList items1 = root.getElementsByTagName("g");
            Element groot=(Element)items1.item(0);
            NodeList items2 = groot.getElementsByTagName("path");
            //遍历每一个省份
            if (items2.getLength()>0){
                List<Province> list=new ArrayList<>();
                SvgPathParser svg=new SvgPathParser();
                for (int i=0;i<items2.getLength();i++){
                    Province province=new Province();
                    Element ele_Province=(Element)items2.item(i);
                    String PathPoints=ele_Province.getAttribute("d");
                    String name=ele_Province.getAttribute("title");
                    List<Path> listpath=new ArrayList<>();
                    //拿到每个省的path集合
                    String s[]=PathPoints.split("z");
                    List<String> pathStringList = new ArrayList<>();
                    for(String ss:s){
                        ss+="z";
                        listpath.add(svg.parsePath(ss));
                        pathStringList.add(ss);
                    }
                    //拿到name和path
                    province.setName(name);
                    province.setPinYin(MapHelper.provincePinYin.get(name));
                    province.setPathList(listpath);
                    province.setPathStringList(pathStringList);
                    province.setColor(Color.parseColor(MapHelper.getRandColorCode()));
                    province.setLineColor(Color.parseColor(Const.UNSELECTED_PROVINCE_LINE_COLOR));
                    if (svg.getMax_X()>=Max_X){
                        Max_X=svg.getMax_X();
                    }
                    if (svg.getMax_Y()>=Max_y){
                        Max_y=svg.getMax_Y();
                    }
                    if (svg.getMin_X()<=Min_x){
                        Min_x=svg.getMin_X();
                    }
                    if (svg.getMin_Y()<=Min_y){
                        Min_y=svg.getMin_Y();
                    }
                    list.add(province);
                    Log.e("map","privince name:"+province.getName()+"  index:"+index);
                    index++;
                }
                Log.e("map:","count:"+list.size() + "maxx:"+Max_X+"maxy:"
                        +Max_y+"minx:"+Min_x+"miny:"+Min_y);
                map.setProvinceList(list);
                map.setMax_x(Max_X);
                map.setMax_y(Max_y);
                map.setMin_x(Min_x);
                map.setMin_y(Min_y);
            }
        }catch (IOException | ParserConfigurationException | ParseException | SAXException e) {
            e.printStackTrace();
        }
        return map;
    }

}