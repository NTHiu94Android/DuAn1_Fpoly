package com.example.duan1_nhom1.modul;

import java.io.Serializable;

public class RestaurantOwners implements Serializable {
    private String maCNH;
    private String tenCNH;
    private String vaiTro;
    private String matKhau;

    public RestaurantOwners() {
    }

    public RestaurantOwners(String maCNH, String tenCNH, String vaiTro, String matKhau) {
        this.maCNH = maCNH;
        this.tenCNH = tenCNH;
        this.vaiTro = vaiTro;
        this.matKhau = matKhau;
    }

    public String getMaCNH() {
        return maCNH;
    }

    public void setMaCNH(String maCNH) {
        this.maCNH = maCNH;
    }

    public String getTenCNH() {
        return tenCNH;
    }

    public void setTenCNH(String tenCNH) {
        this.tenCNH = tenCNH;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
