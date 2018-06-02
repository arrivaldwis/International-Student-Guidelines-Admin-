package com.sandywinata.isgupdate.model;

import java.io.Serializable;

public class ContactModel implements Serializable {
    public String name;
    public String jabatan;
    public String office;
    public String email;
    public String phone;
    public String mobile;
    public String imgUrl;

    public ContactModel() {
    }

    public ContactModel(String name, String jabatan, String office, String email, String phone,
                        String mobile, String imgUrl) {
        this.name = name;
        this.jabatan = jabatan;
        this.office = office;
        this.email = email;
        this.phone = phone;
        this.mobile = mobile;
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
