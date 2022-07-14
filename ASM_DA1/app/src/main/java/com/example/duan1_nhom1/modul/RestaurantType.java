package com.example.duan1_nhom1.modul;

public class RestaurantType {
    private int maLoaiNH;
    private String tenLoaiNH;

    public RestaurantType() {
    }

    public RestaurantType(int maLoaiNH, String tenLoaiNH) {
        this.maLoaiNH = maLoaiNH;
        this.tenLoaiNH = tenLoaiNH;
    }

    public int getMaLoaiNH() {
        return maLoaiNH;
    }

    public void setMaLoaiNH(int maLoaiNH) {
        this.maLoaiNH = maLoaiNH;
    }

    public String getTenLoaiNH() {
        return tenLoaiNH;
    }

    public void setTenLoaiNH(String tenLoaiNH) {
        this.tenLoaiNH = tenLoaiNH;
    }
}
