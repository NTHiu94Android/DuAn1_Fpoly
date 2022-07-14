package com.example.duan1_nhom1.modul;

import java.io.Serializable;

public class Food implements Serializable {
    private String maMA;
    private String tenMA;
    private String moTa;
    private String hinhAnh;
    private double gia;
    private String maNH;
    private int maLoaiMA;

    public Food() {
    }

    public Food(String maMA, String tenMA, String moTa, String hinhAnh, double gia, String maNH, int maLoaiMA) {
        this.maMA = maMA;
        this.tenMA = tenMA;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.gia = gia;
        this.maNH = maNH;
        this.maLoaiMA = maLoaiMA;
    }

    public String getMaMA() {
        return maMA;
    }

    public void setMaMA(String maMA) {
        this.maMA = maMA;
    }

    public String getTenMA() {
        return tenMA;
    }

    public void setTenMA(String tenMA) {
        this.tenMA = tenMA;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getMaNH() {
        return maNH;
    }

    public void setMaNH(String maNH) {
        this.maNH = maNH;
    }

    public int getMaLoaiMA() {
        return maLoaiMA;
    }

    public void setMaLoaiMA(int maLoaiMA) {
        this.maLoaiMA = maLoaiMA;
    }
}
