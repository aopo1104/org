package com.example.asus.organization2.Bean;

import java.io.Serializable;

/**
 * Created by ASUS on 2019/2/14.
 */

public class Info_ActivityMessage implements Serializable {

    private int id;
    private String organizationName;
    private String personName;  //发布通知的人的名字
    private int type;   //1：活动 2：比赛
    private String title;
    private String content;
    private String picture;//封面图片
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getorganizationName() {
        return organizationName;
    }

    public void setorganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Info_ActivityMessage(int id, String organizationName, String personName, int type, String title, String content, String picture, String createTime) {
        this.id = id;
        this.organizationName = organizationName;
        this.personName = personName;
        this.type = type;
        this.title = title;
        this.content = content;
        this.picture = picture;
        this.createTime = createTime;
    }
}
