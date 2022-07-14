package com.example.duan1_nhom1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_nhom1.dbhelper.DBHelper;
import com.example.duan1_nhom1.modul.TypeOfFood;

import java.util.ArrayList;

public class FoodTypeDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public FoodTypeDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }

    //Lay toan bo loai nha hang
    public ArrayList<TypeOfFood> getAllFoodType(){
        ArrayList<TypeOfFood> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TYPE_OF_FOOD", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int maLoaiMA = cursor.getInt(0);
                String tenLoaiMA = cursor.getString(1);
                int hinhAnhLMA = cursor.getInt(2);
                list.add(new TypeOfFood(maLoaiMA, tenLoaiMA, hinhAnhLMA));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }
}
