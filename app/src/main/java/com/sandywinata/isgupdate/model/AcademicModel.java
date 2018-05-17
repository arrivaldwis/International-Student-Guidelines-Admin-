package com.sandywinata.isgupdate.model;

public class AcademicModel {
    public String name;
    public String imgUrl;

    public AcademicModel() {
    }

    public AcademicModel(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
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
}
