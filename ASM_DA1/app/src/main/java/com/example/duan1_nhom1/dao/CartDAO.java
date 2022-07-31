package com.example.duan1_nhom1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_nhom1.dbhelper.DBHelper;
import com.example.duan1_nhom1.modul.Cart;

import java.util.ArrayList;

public class CartDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public CartDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }

    //Lay toan bo gio hang
    public ArrayList<Cart> getAllCart(){
        ArrayList<Cart> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CART", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maGH = cursor.getString(0);
                int soLuong = cursor.getInt(1);
                double donGia = cursor.getDouble(2);
                String maMA = cursor.getString(3);
                String maKH = cursor.getString(4);
                list.add(new Cart(maGH, soLuong, donGia, maMA, maKH));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }
    //Lay gio hang theo ma khach hang
    public ArrayList<Cart> getAllCartTheoMaKH(String maKH){
        ArrayList<Cart> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CART WHERE MA_KH=?", new String[]{maKH});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maGH = cursor.getString(0);
                int soLuong = cursor.getInt(1);
                double donGia = cursor.getDouble(2);
                String maMA = cursor.getString(3);
                list.add(new Cart(maGH, soLuong, donGia, maMA, maKH));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }

    //Them vao gio hang
    public void insert(Cart cart){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MA_GH", cart.getMaGH());
        values.put("SOLUONG_CART", cart.getSoLuong());
        values.put("DONGIA_CART", cart.getDonGia());
        values.put("MA_MA", cart.getMaMA());
        values.put("MA_KH", cart.getMaKH());
        db.insert("CART", null, values);
        db.close();
    }

    //Xoa gio hang
    public void delete(String maGH){
        db = dbHelper.getWritableDatabase();
        db.delete("CART", "MA_GH=?", new String[]{maGH});
        db.close();
    }

    //Xoa tat ca
    public void deleteAll(){
        db = dbHelper.getWritableDatabase();
        db.delete("CART", null, null);
        db.close();
    }
}
