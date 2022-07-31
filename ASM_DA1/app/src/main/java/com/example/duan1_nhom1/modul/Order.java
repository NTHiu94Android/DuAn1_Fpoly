package com.example.duan1_nhom1.modul;

public class Order {
    private String maHD;
    private String ngayMua;
    private double tongTien;
    private String trangThai;
    private String maMA;
    private String maNH;
    private String maKH;

    public Order() {
    }

    public Order(String maHD, String ngayMua, double tongTien, String trangThai, String maMA, String maNH, String maKH) {
        this.maHD = maHD;
        this.ngayMua = ngayMua;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.maMA = maMA;
        this.maNH = maNH;
        this.maKH = maKH;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaMA() {
        return maMA;
    }

    public void setMaMA(String maMA) {
        this.maMA = maMA;
    }

    public String getMaNH() {
        return maNH;
    }

    public void setMaNH(String maNH) {
        this.maNH = maNH;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }
}
