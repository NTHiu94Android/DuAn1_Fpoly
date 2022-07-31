package com.example.duan1_nhom1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_nhom1.dbhelper.DBHelper;
import com.example.duan1_nhom1.modul.OrderDetail;

import java.util.ArrayList;

public class OrderDetailDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public OrderDetailDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }

    //Lay toan bo hoa don chi tiet
    public ArrayList<OrderDetail> getAllOrderDetail(){
        ArrayList<OrderDetail> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ORDER_DETAIL", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maHDCT = cursor.getString(0);
                int soLuong = cursor.getInt(1);
                double donGia = cursor.getDouble(2);
                String maMA = cursor.getString(3);
                String maHD = cursor.getString(4);
                list.add(new OrderDetail(maHDCT, soLuong, donGia, maMA, maHD));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }

    //Them hoa don chi tiet
    public void insert(OrderDetail orderDetail){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MA_HDCT", orderDetail.getMaDHCT());
        values.put("SOLUONG", orderDetail.getSoLuong());
        values.put("DONGIA", orderDetail.getDonGia());
        values.put("MA_MA", orderDetail.getMaMA());
        values.put("MA_HD", orderDetail.getMaHD());
        db.insert("ORDER_DETAIL", null, values);
        db.close();
    }

    //Xoa tat ca
    public void deleteAll(){
        db = dbHelper.getWritableDatabase();
        db.delete("ORDER_DETAIL", null, null);
        db.close();
    }
}
