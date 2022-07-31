package com.example.duan1_nhom1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_nhom1.dbhelper.DBHelper;
import com.example.duan1_nhom1.modul.Order;

import java.util.ArrayList;

public class OrderDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public OrderDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }

    //Lay toan bo hoa don
    public ArrayList<Order> getAllOrder(){
        ArrayList<Order> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ORDERS", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maHD = cursor.getString(0);
                String ngayMua = cursor.getString(1);
                double tongTien = cursor.getDouble(2);
                String trangThai = cursor.getString(3);
                String maMA = cursor.getString(4);
                String maNH = cursor.getString(5);
                String maKH = cursor.getString(5);
                list.add(new Order(maHD, ngayMua, tongTien, trangThai, maMA, maNH, maKH));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }
    //Lay hoa don theo ma nha hang
    public ArrayList<Order> getOrderTheoMaNH(String maNH, String trangThai){
        ArrayList<Order> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ORDERS WHERE MA_NH=? AND TRANGTHAI=?", new String[]{maNH, trangThai});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maHD = cursor.getString(0);
                String ngayMua = cursor.getString(1);
                double tongTien = cursor.getDouble(2);
                String maMA = cursor.getString(4);
                String maKH = cursor.getString(6);
                list.add(new Order(maHD, ngayMua, tongTien, trangThai, maMA, maNH, maKH));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }
    //Lay hoa don theo ma khach hang
    public ArrayList<Order> getOrderTheoMaKH(String maKH){
        ArrayList<Order> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ORDERS WHERE MA_KH=?", new String[]{maKH});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maHD = cursor.getString(0);
                String ngayMua = cursor.getString(1);
                double tongTien = cursor.getDouble(2);
                String trangThai = cursor.getString(3);
                String maMA = cursor.getString(4);
                String maNH = cursor.getString(5);
                list.add(new Order(maHD, ngayMua, tongTien, trangThai, maMA, maNH, maKH));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }

    //Them hoa don
    public void insert(Order order){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MA_HD", order.getMaHD());
        values.put("NGAYMUA", order.getNgayMua());
        values.put("TONGTIEN", order.getTongTien());
        values.put("TRANGTHAI", order.getTrangThai());
        values.put("MA_MA", order.getMaMA());
        values.put("MA_NH", order.getMaNH());
        values.put("MA_KH", order.getMaKH());
        db.insert("ORDERS", null, values);
        db.close();
    }

    //Cap nhat hoa don
    public void update(Order order){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NGAYMUA", order.getNgayMua());
        values.put("TONGTIEN", order.getTongTien());
        values.put("TRANGTHAI", order.getTrangThai());
        values.put("MA_MA", order.getMaMA());
        values.put("MA_NH", order.getMaNH());
        values.put("MA_KH", order.getMaKH());
        db.update("ORDERS", values, "MA_HD=?", new String[]{order.getMaHD()});
        db.close();
    }

    //Xoa tat ca
    public void deleteAll(){
        db = dbHelper.getWritableDatabase();
        db.delete("ORDERS", null, null);
        db.close();
    }
}
