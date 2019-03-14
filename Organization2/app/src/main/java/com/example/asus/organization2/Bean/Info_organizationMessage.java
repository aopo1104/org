package com.example.asus.organization2.Bean;

import java.io.Serializable;

/**
 * Created by ASUS on 2019/2/11.
 */

public class Info_organizationMessage implements Serializable {

    private int id;
    private String name;
    private int type;   //1：社团  2：组织
    private String affiliatedUnit;  //挂靠单位
    private String schoolName;
    private int star;   //如果是社团的话 会有星级
    private String presidentName;   //会长姓名
    private String headPicture;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAffiliatedUnit() {
        return affiliatedUnit;
    }

    public void setAffiliatedUnit(String affiliatedUnit) {
        this.affiliatedUnit = affiliatedUnit;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getPresidentName() {
        return presidentName;
    }

    public void setPresidentName(String presidentName) {
        this.presidentName = presidentName;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }

    public Info_organizationMessage(int id, String name, int type, String affiliatedUnit, String schoolName, int star, String presidentName, String headPicture) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.affiliatedUnit = affiliatedUnit;
        this.schoolName = schoolName;
        this.star = star;
        this.presidentName = presidentName;
        this.headPicture = headPicture;
    }
}
