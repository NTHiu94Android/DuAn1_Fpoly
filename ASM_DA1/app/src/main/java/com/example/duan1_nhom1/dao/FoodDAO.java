package com.example.duan1_nhom1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.duan1_nhom1.dbhelper.DBHelper;
import com.example.duan1_nhom1.modul.Food;

import java.util.ArrayList;

public class FoodDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public FoodDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }

    //Lay toan bo loai nha hang
    public ArrayList<Food> getAllFood(){
        ArrayList<Food> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM FOOD", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maMA = cursor.getString(0);
                String tenMA = cursor.getString(1);
                String moTa = cursor.getString(2);
                String hinhAnhMA = cursor.getString(3);
                double gia = cursor.getDouble(4);
                String maNH = cursor.getString(5);
                int maLoaiMA = cursor.getInt(6);
                list.add(new Food(maMA, tenMA, moTa, hinhAnhMA, gia, maNH, maLoaiMA));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }
    //Lay mon an tho ma nha hang
    public ArrayList<Food> getFoodTheoMaNH(String maNH){
        ArrayList<Food> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM FOOD WHERE MA_NH=?", new String[]{maNH});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maMA = cursor.getString(0);
                String tenMA = cursor.getString(1);
                String moTa = cursor.getString(2);
                String hinhAnhMA = cursor.getString(3);
                double gia = cursor.getDouble(4);
                int maLoaiMA = cursor.getInt(6);
                list.add(new Food(maMA, tenMA, moTa, hinhAnhMA, gia, maNH, maLoaiMA));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }
    //Lay mon an theo ma loai mon an
    public ArrayList<Food> getFoodTheoMaLoaiMA(int maLoaiMA){
        ArrayList<Food> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM FOOD WHERE MALOAI_MA=?", new String[]{String.valueOf(maLoaiMA)});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maMA = cursor.getString(0);
                String tenMA = cursor.getString(1);
                String moTa = cursor.getString(2);
                String hinhAnhMA = cursor.getString(3);
                double gia = cursor.getDouble(4);
                String maNH = cursor.getString(5);
                list.add(new Food(maMA, tenMA, moTa, hinhAnhMA, gia, maNH, maLoaiMA));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }
    //Them mon an
    public void insert(Food food){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MA_MA", food.getMaMA());
        values.put("TEN_MA", food.getTenMA());
        values.put("MOTA", food.getMoTa());
        values.put("HINHANH_MA", food.getHinhAnh());
        values.put("GIA", food.getGia());
        values.put("MA_NH", food.getMaNH());
        values.put("MALOAI_MA", food.getMaLoaiMA());
        db.insert("FOOD", null, values);
        db.close();
    }
    //Sua mon an
    public void update(Food food){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TEN_MA", food.getTenMA());
        values.put("MOTA", food.getMoTa());
        values.put("HINHANH_MA", food.getHinhAnh());
        values.put("GIA", food.getGia());
        values.put("MA_NH", food.getMaNH());
        values.put("MALOAI_MA", food.getMaLoaiMA());
        db.update("FOOD", values, "MA_MA=?", new String[]{food.getMaMA()});
        db.close();
    }
    //Xoa mon an
    public void delete(String maMA){
        db = dbHelper.getWritableDatabase();
        db.delete("FOOD", "MA_MA=?", new String[]{maMA});
        db.close();
    }
    //Xoa tat ca
    public void deleteAll(){
        db = dbHelper.getWritableDatabase();
        db.delete("FOOD", null, null);
        db.close();
    }
}
