package com.example.duan1_nhom1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.duan1_nhom1.dbhelper.DBHelper;
import com.example.duan1_nhom1.modul.Restaurant;
import java.util.ArrayList;

public class RestaurantDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    public RestaurantDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }

    //Lay toan bo nha hang
    public ArrayList<Restaurant> getAllRestaurant(){
        ArrayList<Restaurant> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RESTAURANT", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maNH = cursor.getString(0);
                String tenNH = cursor.getString(1);
                String diaChi = cursor.getString(2);
                String moTa = cursor.getString(3);
                int hinhAnh = cursor.getInt(4);
                String maCNH = cursor.getString(5);
                int maLoaiNH = cursor.getInt(6);
                list.add(new Restaurant(maNH, tenNH, diaChi, moTa, hinhAnh, maCNH, maLoaiNH));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }

    //Lay nha hang theo maCNH
    public ArrayList<Restaurant> getRestaurantTheoMaCNH(String maCNH){
        ArrayList<Restaurant> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RESTAURANT WHERE MA_CNH=?", new String[]{maCNH});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maNH = cursor.getString(0);
                String tenNH = cursor.getString(1);
                String diaChi = cursor.getString(2);
                String moTa = cursor.getString(3);
                int hinhAnh = cursor.getInt(4);
                int maLoaiNH = cursor.getInt(6);
                list.add(new Restaurant(maNH, tenNH, diaChi, moTa, hinhAnh, maCNH, maLoaiNH));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }
    //Them nha hang
    public void insert(Restaurant restaurant){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MA_NH", restaurant.getMaCNH()+restaurant.getTenNH());
        values.put("TEN_NH", restaurant.getTenNH());
        values.put("DIACHI", restaurant.getDiaChi());
        values.put("MOTA", restaurant.getMoTa());
        values.put("HINHANH_NH", restaurant.getHinhAnh());
        values.put("MA_CNH", restaurant.getMaCNH());
        values.put("MALOAI_NH", restaurant.getMaLoaiNH());
        db.insert("RESTAURANT", null, values);
        db.close();
    }
    //Sua nha hang
    public void update(Restaurant restaurant){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TEN_NH", restaurant.getTenNH());
        values.put("DIACHI", restaurant.getDiaChi());
        values.put("MOTA", restaurant.getMoTa());
        values.put("HINHANH_NH", restaurant.getHinhAnh());
        values.put("MA_CNH", restaurant.getMaCNH());
        values.put("MALOAI_NH", restaurant.getMaLoaiNH());
        db.update("RESTAURANT", values, "MA_NH=?", new String[]{restaurant.getMaNH()});
        db.close();
    }
    public void delete(String maNH){
        db = dbHelper.getWritableDatabase();
        db.delete("RESTAURANT", "MA_NH=?", new String[]{maNH});
        db.close();
    }
    //Xoa tat ca nha hang
    public void deleteAllRestaurant(){
        db = dbHelper.getWritableDatabase();
        db.delete("RESTAURANT", null, null);
        db.close();
    }
}
