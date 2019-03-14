package com.example.asus.organization2.Bean;

import java.io.Serializable;

/**
 * Created by ASUS on 2019/3/5.
 */

public class Info_taskMessage implements Serializable {
    private int id;
    private int type;
    private String orgName;
    private String title;
    private String content;
    private String startTime;
    private String endTime;
    private int isread; //是否阅读
    private int isreport;   //是否有反馈报告
    private String report; //反馈报告

    public Info_taskMessage(int id,int type, String orgName, String title, String content, String startTime, String endTime, int isread, int isreport, String report) {
        this.id = id;
        this.type = type;
        this.orgName = orgName;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isread = isread;
        this.isreport = isreport;
        this.report = report;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }

    public int getIsreport() {
        return isreport;
    }

    public void setIsreport(int isreport) {
        this.isreport = isreport;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
