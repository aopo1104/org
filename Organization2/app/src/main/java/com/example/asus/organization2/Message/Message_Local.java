package com.example.asus.organization2.Message;

import com.example.asus.organization2.Utils.Util_String;

import java.util.ArrayList;

/**
 * 存储打开app后，当前登录用户的信息，关闭app就消失了
 */

public class Message_Local {
    public static int loginStatus;  //登录状态 1表示登录

    public static int id;    //该用户的id号
    public static String name;  //姓名
    public static String phoneNumber;   //手机号
    public static int sex;      //性别
    public static String email;     //电子邮箱
    public static String school;    //学校
    public static String academy;   //学院
    public static String className; //班级
    public static String studentid; //学号
    public static String birth; //生日
    public static String headPicture;   //头像
    public static int isReal;   //是否实名认证

    public static int selectOrg;    //在workpage中选择的org，为在以下数组的编号
    //以下3个中的值一一对应
    public static ArrayList<Integer> organizationId; //社团编号
    public static ArrayList<String> organizationName;    //社团名字
    public static ArrayList<Integer> organizationPlace;  //该人在该社团的职位(1:社长 2:部长 3:干事 4:普通成员)

    public static ArrayList<Integer> friendsArray;  //好友列表

    public static String myToken="";//自己的token值（用于IM）

    //用于load1页面时，从sharedpreferences获得status和id，然后传id到load2中
    public static void saveId(int loginStatus,int id){
        Message_Local.loginStatus = loginStatus;
        Message_Local.id = id;
    }

    //刚登录时，批量初始化数据，从数据库读入到本地存储，方便调用
    public static void saveLocalMessage(int loginStatus,int id,String name,String phoneNumber,int sex,String email,String school,
                                        String academy,String className,String studentid,String birth,String headPicture,int isReal,
                                        String friendsArray,
                                        ArrayList organizationId,ArrayList organizationName,ArrayList organizationPlace){
        Message_Local.loginStatus = loginStatus;
        Message_Local.id = id;
        Message_Local.name = name;
        Message_Local.phoneNumber = phoneNumber;
        Message_Local.sex = sex;
        Message_Local.email = email;
        Message_Local.school = school;
        Message_Local.academy = academy;
        Message_Local.className = className;
        Message_Local.studentid = studentid;
        Message_Local.birth = birth;
        Message_Local.headPicture = headPicture;
        Message_Local.isReal = isReal;
        Message_Local.friendsArray = Util_String.setStringToArray(friendsArray);
        Message_Local.organizationId = organizationId;
        Message_Local.organizationName = organizationName;
        Message_Local.organizationPlace = organizationPlace;
    }


    public static void clearLocalMessage(){
        Message_Local.loginStatus = 0;
        Message_Local.id = 0;
        Message_Local.name = "";
        Message_Local.phoneNumber = "";
        Message_Local.sex = 0;
        Message_Local.email = "";
        Message_Local.school = "";
        Message_Local.academy = "";
        Message_Local.className = "";
        Message_Local.studentid = "";
        Message_Local.birth = "";
        Message_Local.headPicture = "";
        Message_Local.isReal = 0;
        Message_Local.friendsArray = null;
        Message_Local.selectOrg = 0;
        Message_Local.organizationId = null;
        Message_Local.organizationName = null;
        Message_Local.organizationPlace = null;
        Message_Local.myToken = "";

    }

    public static String getMessageString(){
        String string = "id = " + Message_Local.id + "\n" +
                "name = " + Message_Local.name + "\n" +
                "phoneNumber = " + Message_Local.phoneNumber + "\n" +
                "sex = " + Message_Local.sex + "\n" +
                "email = " + Message_Local.email + "\n" +
                "school = " + Message_Local.school + "\n" +
                "academy = " + Message_Local.academy + "\n" +
                "className = " + Message_Local.className + "\n" +
                "studentid = " + Message_Local.studentid + "\n" +
                "birth = " + Message_Local.birth + "\n" +
                "headPicture = " + Message_Local.headPicture + "\n" +
                "isReal = " + Message_Local.isReal + "\n" +
                "friendsArray = " + Message_Local.friendsArray.toString() + "\n" +
                "organizationId = " + Message_Local.organizationId.toString() + "\n" +
                "organizationName = " + Message_Local.organizationName.toString() + "\n" +
                "organizationPlace = " + Message_Local.organizationPlace.toString() + "\n";
        return string;

    }

    //用于hasNotVerified页面，存储实名认证得到的信息
    public static void realVerify(int isReal,String realName,String sex,String birth){
        Message_Local.isReal = 1;
        Message_Local.name = realName;
        Message_Local.birth = birth;
        Message_Local.sex = sex.equals("男")?1:0;
    }

}
