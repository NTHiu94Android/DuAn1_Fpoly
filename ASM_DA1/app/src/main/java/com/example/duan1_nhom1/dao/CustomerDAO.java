package com.example.duan1_nhom1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.duan1_nhom1.dbhelper.DBHelper;
import com.example.duan1_nhom1.modul.Customer;

import java.util.ArrayList;

public class CustomerDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    public CustomerDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }

    public ArrayList<Customer> getAllCustomer(){
        ArrayList<Customer> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CUSTOMER", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String sdt = cursor.getString(0);
                String tenKH = cursor.getString(1);
                String diaChi = cursor.getString(2);
                String vaitro = cursor.getString(3);
                String matKhau = cursor.getString(4);
                list.add(new Customer(sdt, tenKH, diaChi, vaitro, matKhau));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }
    //Lay thu thu theo ten
    public Customer getCustomer(String MaTk){
        ArrayList<Customer> listCustomer = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CUSTOMER WHERE SDT =?", new String[]{MaTk});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String sdt = cursor.getString(0);
                String tenKH = cursor.getString(1);
                String diaChi = cursor.getString(2);
                String vaitro = cursor.getString(3);
                String matKhau = cursor.getString(4);
                listCustomer.add(new Customer(sdt, tenKH, diaChi, vaitro, matKhau));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return listCustomer.get(0);
    }
    public void insertCustomer(Customer customer){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SDT", customer.getSdt());
        values.put("TEN_KH", customer.getTenKH());
        values.put("DIACHI", customer.getDiaChi());
        values.put("VAITRO_KH", customer.getVaiTro());
        values.put("MATKHAU_KH", customer.getMatKhau());
        db.insert("CUSTOMER", null, values);
        //myRef.child(customer.getSdt()).setValue(customer);
        db.close();
    }

    public void updateCustomer(Customer customer){
        db =dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TEN_KH", customer.getTenKH());
        values.put("DIACHI", customer.getDiaChi());
        values.put("VAITRO_KH", customer.getVaiTro());
        values.put("MATKHAU_KH", customer.getMatKhau());
        db.update("CUSTOMER", values, "SDT=?", new String[]{customer.getSdt()});
        //myRef.child(customer.getSdt()).setValue(customer);
        db.close();
    }

    public void deleteCustomer(String sdt){
        db = dbHelper.getWritableDatabase();
        db.delete("CUSTOMER", "SDT=?", new String[]{sdt});
//        myRef.child(sdt).removeValue(new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//
//            }
//        });
        db.close();
    }

    //Xoa tat ca
    public void deleteAll(){
        db = dbHelper.getWritableDatabase();
        db.delete("CUSTOMER", null, null);
        db.close();
    }

    //Dang ky
    public Boolean checkusername(String username) {
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from CUSTOMER where SDT=?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    //Dang nhap
    public Boolean checkusernamepassword(String username, String password){
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from CUSTOMER where SDT=? and MATKHAU_KH=?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
