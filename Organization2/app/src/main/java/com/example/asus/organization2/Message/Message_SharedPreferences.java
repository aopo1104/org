package com.example.asus.organization2.Message;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * 将数据存储到本机上，每次上线将数据读取到local_message中
 */

public class Message_SharedPreferences {

    public static void saveMessage(Context context){
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp=context.getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("loginStatus",1);
        editor.putInt("id", Message_Local.id);
        //提交修改
        editor.commit();
    }

    public static void clearMessage(Context context){
        SharedPreferences sp=context.getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("loginStatus",0);
        editor.putInt("id",0);
        //提交修改
        editor.commit();
    }

    //读取缓存并存入localMessage
    public static void loadMessage(Context context){
        SharedPreferences sp=context.getSharedPreferences("loginInfo", MODE_PRIVATE);
        int loginStatus = sp.getInt("loginStatus",0);
        int id = sp.getInt("id",0);
        Message_Local.saveId(loginStatus,id);
    }
}
