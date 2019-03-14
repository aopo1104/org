package com.example.asus.organization2.Bean;

import java.io.Serializable;

/**
 * Created by ASUS on 2019/3/8.
 */

public class Info_myTaskStatusMessage implements Serializable {

    private int id;
    private String name;
    private int isread;
    private int isreport;
    private String report;

    public Info_myTaskStatusMessage(int id, String name, int isread, int isreport, String report) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
