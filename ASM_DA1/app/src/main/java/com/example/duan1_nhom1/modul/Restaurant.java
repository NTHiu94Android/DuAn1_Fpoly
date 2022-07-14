package com.example.duan1_nhom1.modul;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private String maNH;
    private String tenNH;
    private String diaChi;
    private String moTa;
    private int hinhAnh;
    private String maCNH;
    private int maLoaiNH;

    public Restaurant() {
    }

    public Restaurant(String maNH, String tenNH, String diaChi, String moTa, int hinhAnh, String maCNH, int maLoaiNH) {
        this.maNH = maNH;
        this.tenNH = tenNH;
        this.diaChi = diaChi;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.maCNH = maCNH;
        this.maLoaiNH = maLoaiNH;
    }

    public String getMaNH() {
        return maNH;
    }

    public void setMaNH(String maNH) {
        this.maNH = maNH;
    }

    public String getTenNH() {
        return tenNH;
    }

    public void setTenNH(String tenNH) {
        this.tenNH = tenNH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMaCNH() {
        return maCNH;
    }

    public void setMaCNH(String maCNH) {
        this.maCNH = maCNH;
    }

    public int getMaLoaiNH() {
        return maLoaiNH;
    }

    public void setMaLoaiNH(int maLoaiNH) {
        this.maLoaiNH = maLoaiNH;
    }
}
