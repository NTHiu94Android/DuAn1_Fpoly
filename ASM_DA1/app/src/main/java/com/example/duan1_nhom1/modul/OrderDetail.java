package com.example.duan1_nhom1.modul;

public class OrderDetail {
    private String maDHCT;
    private int soLuong;
    private double donGia;
    private String maMA;
    private String maHD;

    public OrderDetail() {
    }

    public OrderDetail(String maDHCT, int soLuong, double donGia, String maMA, String maHD) {
        this.maDHCT = maDHCT;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.maMA = maMA;
        this.maHD = maHD;
    }

    public String getMaDHCT() {
        return maDHCT;
    }

    public void setMaDHCT(String maDHCT) {
        this.maDHCT = maDHCT;
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

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }
}
