package com.example.asus.organization2.Bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ASUS on 2019/2/9.
 */

public class Info_PeronalMessage implements Serializable{

    public int id;    //该用户的id号
    public String name;  //姓名
    public String phoneNumber;   //手机号
    public int sex;      //性别
    public String email;     //电子邮箱
    public String school;    //学校
    public String academy;   //学院
    public String className; //班级
    public String studentid; //学号
    public String birth; //生日
    public String headPicture;   //头像
    public int isReal;   //是否实名认证

    //以下3个中的值一一对应
    public ArrayList<Integer> organizationId; //社团编号
    public ArrayList<String> organizationName;    //社团名字
    public ArrayList<Integer> organizationPlace;  //该人在该社团的职位

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }

    public int getIsReal() {
        return isReal;
    }

    public void setIsReal(int isReal) {
        this.isReal = isReal;
    }

    public ArrayList<Integer> getorganizationId() {
        return organizationId;
    }

    public void setorganizationId(ArrayList<Integer> organizationId) {
        this.organizationId = organizationId;
    }

    public ArrayList<String> getorganizationName() {
        return organizationName;
    }

    public void setorganizationName(ArrayList<String> organizationName) {
        this.organizationName = organizationName;
    }

    public ArrayList<Integer> getorganizationPlace() {
        return organizationPlace;
    }

    public void setorganizationPlace(ArrayList<Integer> organizationPlace) {
        this.organizationPlace = organizationPlace;
    }

    public Info_PeronalMessage(int id, String name, String phoneNumber, int sex, String email, String school, String academy, String className, String studentid, String birth, String headPicture, int isReal, ArrayList<Integer> organizationId, ArrayList<String> organizationName, ArrayList<Integer> organizationPlace) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.email = email;
        this.school = school;
        this.academy = academy;
        this.className = className;
        this.studentid = studentid;
        this.birth = birth;
        this.headPicture = headPicture;
        this.isReal = isReal;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.organizationPlace = organizationPlace;
    }
}

