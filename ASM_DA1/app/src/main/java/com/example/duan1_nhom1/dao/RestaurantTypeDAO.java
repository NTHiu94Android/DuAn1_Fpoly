package com.example.duan1_nhom1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.duan1_nhom1.dbhelper.DBHelper;
import com.example.duan1_nhom1.modul.RestaurantType;

import java.util.ArrayList;

public class RestaurantTypeDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public RestaurantTypeDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }

    //Lay toan bo loai nha hang
    public ArrayList<RestaurantType> getAllRestaurantType(){
        ArrayList<RestaurantType> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TYPE_RESRTAURANT", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int maLoaiNH = cursor.getInt(0);
                String tenLoaiNH = cursor.getString(1);
                list.add(new RestaurantType(maLoaiNH, tenLoaiNH));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }

}
