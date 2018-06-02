package com.sandywinata.isgupdate.model;

import java.io.Serializable;

public class POIModel implements Serializable {
    public String name;
    public String imgUrl;
    public String desc;
    public String ticket;

    public POIModel() {
    }

    public POIModel(String name, String imgUrl, String desc, String ticket) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.desc = desc;
        this.ticket = ticket;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
