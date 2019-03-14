package com.example.asus.organization2.Utils;

import java.util.ArrayList;

/**
 * Created by ASUS on 2018/12/31.
 */

public class Util_String {

    //把数据库传过来的 用，分隔的字符串 解析成一个int数组
    public static ArrayList<Integer> setStringToArray(String string){
        String[] arrayString = string.split(",");
        ArrayList<Integer> arrayInt = new ArrayList<>();
        for(int temp=0;temp<arrayString.length;temp++){
            arrayInt.add(Integer.parseInt(arrayString[temp]));
        }
        return arrayInt;
    }
}
