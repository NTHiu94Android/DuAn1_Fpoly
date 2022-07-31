package com.example.duan1_nhom1.modul;

import java.io.Serializable;

public class Cart implements Serializable {
    private String maGH;
    private int soLuong;
    private double donGia;
    private String maMA;
    private String maKH;

    public Cart() {
    }

    public Cart(String maGH, int soLuong, double donGia, String maMA, String maKH) {
        this.maGH = maGH;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.maMA = maMA;
        this.maKH = maKH;
    }

    public String getMaGH() {
        return maGH;
    }

    public void setMaGH(String maGH) {
        this.maGH = maGH;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getMaMA() {
        return maMA;
    }

    public void setMaMA(String maMA) {
        this.maMA = maMA;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }
}
