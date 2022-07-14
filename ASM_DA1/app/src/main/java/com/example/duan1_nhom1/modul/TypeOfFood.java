package com.example.duan1_nhom1.modul;

import java.io.Serializable;

public class TypeOfFood implements Serializable {
    private int maLoaiMA;
    private String tenLoaiMA;
    private int hinhAnh;

    public TypeOfFood() {
    }

    public TypeOfFood(int maLoaiMA, String tenLoaiMA, int hinhAnh) {
        this.maLoaiMA = maLoaiMA;
        this.tenLoaiMA = tenLoaiMA;
        this.hinhAnh = hinhAnh;
    }

    public int getMaLoaiMA() {
        return maLoaiMA;
    }

    public void setMaLoaiMA(int maLoaiMA) {
        this.maLoaiMA = maLoaiMA;
    }

    public String getTenLoaiMA() {
        return tenLoaiMA;
    }

    public void setTenLoaiMA(String tenLoaiMA) {
        this.tenLoaiMA = tenLoaiMA;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
