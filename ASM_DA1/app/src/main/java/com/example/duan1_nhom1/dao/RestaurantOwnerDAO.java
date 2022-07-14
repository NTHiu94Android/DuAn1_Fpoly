package com.example.duan1_nhom1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.duan1_nhom1.dbhelper.DBHelper;
import com.example.duan1_nhom1.modul.RestaurantOwners;
import java.util.ArrayList;

public class RestaurantOwnerDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    public RestaurantOwnerDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }

    public ArrayList<RestaurantOwners> getAllRestaurantOwner(){
        ArrayList<RestaurantOwners> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RESTAURANT_OWNERS", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maCNH = cursor.getString(0);
                String tenCNH = cursor.getString(1);
                String vaitro = cursor.getString(2);
                String matKhau = cursor.getString(3);
                list.add(new RestaurantOwners(maCNH, tenCNH, vaitro, matKhau));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }
    //Lay thu thu theo ten
    public RestaurantOwners getRestaurantOwner(String MaCNH){
        ArrayList<RestaurantOwners> listRestaurantOwner = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RESTAURANT_OWNERS WHERE MA_CNH =?", new String[]{MaCNH});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maCNH = cursor.getString(0);
                String tenCNH = cursor.getString(1);
                String vaitro = cursor.getString(2);
                String matKhau = cursor.getString(3);
                listRestaurantOwner.add(new RestaurantOwners(maCNH, tenCNH, vaitro, matKhau));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return listRestaurantOwner.get(0);
    }
    public void insertRestaurantOwner(RestaurantOwners restaurantOwners){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MA_CNH", restaurantOwners.getMaCNH());
        values.put("TEN_CNH", restaurantOwners.getTenCNH());
        values.put("VAITRO_CNH", restaurantOwners.getVaiTro());
        values.put("MATKHAU_CNH", restaurantOwners.getMatKhau());
        db.insert("RESTAURANT_OWNERS", null, values);
        db.close();
    }

    public void updateRestaurantOwnwer(RestaurantOwners restaurantOwners){
        db =dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TEN_CNH", restaurantOwners.getTenCNH());
        values.put("VAITRO_CNH", restaurantOwners.getVaiTro());
        values.put("MATKHAU_CNH", restaurantOwners.getMatKhau());
        db.update("RESTAURANT_OWNERS", values, "MA_CNH=?", new String[]{restaurantOwners.getMaCNH()});
        db.close();
    }

    public void deleteRestaurantOwner(String maCNH){
        db = dbHelper.getWritableDatabase();
        db.delete("RESTAURANT_OWNERS", "MA_CNH=?", new String[]{maCNH});
        db.close();
    }

    //Xoa tat ca
    public void deleteAll(){
        db = dbHelper.getWritableDatabase();
        db.delete("RESTAURANT_OWNERS", null, null);
        db.close();
    }
    //Dang ky
    public Boolean checkusername(String username) {
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from RESTAURANT_OWNERS where MA_CNH=?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    //Dang nhap
    public Boolean checkusernamepassword(String username, String password){
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from RESTAURANT_OWNERS where MA_CNH=? and MATKHAU_CNH=?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
