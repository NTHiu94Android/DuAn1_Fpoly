package com.example.duan1_nhom1.modul;

public class OrderDetail {
    private int maDHCT;
    private int soLuong;
    private double donGia;
    private int maMA;
    private int maHD;

    public OrderDetail(int maDHCT, int soLuong, double donGia, int maMA, int maHD) {
        this.maDHCT = maDHCT;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.maMA = maMA;
        this.maHD = maHD;
    }

    public int getMaDHCT() {
        return maDHCT;
    }

    public void setMaDHCT(int maDHCT) {
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

    public int getMaMA() {
        return maMA;
    }

    public void setMaMA(int maMA) {
        this.maMA = maMA;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }
}
