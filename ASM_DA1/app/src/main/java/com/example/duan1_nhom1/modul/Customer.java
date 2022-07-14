package com.example.duan1_nhom1.modul;

import java.io.Serializable;

public class Customer implements Serializable {
    private String sdt;
    private String tenKH;
    private String diaChi;
    private String vaiTro;
    private String matKhau;

    public Customer() {
    }

    public Customer(String sdt, String tenKH, String diaChi, String vaiTro, String matKhau) {
        this.sdt = sdt;
        this.tenKH = tenKH;
        this.diaChi = diaChi;
        this.vaiTro = vaiTro;
        this.matKhau = matKhau;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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
