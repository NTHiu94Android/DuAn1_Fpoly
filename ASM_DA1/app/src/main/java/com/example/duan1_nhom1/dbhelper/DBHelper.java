package com.example.duan1_nhom1.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duan1_nhom1.R;

public class DBHelper extends SQLiteOpenHelper {
    private String sql = "";
    public DBHelper(Context context) {
        super(context, "DuAn1.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        taoBang(sqLiteDatabase);
        themDuLieu(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sql = "DROP TABLE IF EXISTS CUSTOMER";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS RESTAURANT_OWNERS";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS TYPE_RESRTAURANT";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS RESRTAURANT";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS TYPE_OF_FOOD";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS FOOD";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS ORDERS";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS ORDER_DETAIL";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS CART";
        sqLiteDatabase.execSQL(sql);

        onCreate(sqLiteDatabase);
    }

    //Tao bang
    private void taoBang(SQLiteDatabase sqLiteDatabase){
        //Tao bang khach hang
        sql = "CREATE TABLE CUSTOMER (SDT TEXT, TEN_KH TEXT, DIACHI TEXT, VAITRO_KH TEXT, MATKHAU_KH TEXT)";
        sqLiteDatabase.execSQL(sql);

        //Tao bang chu nha hang
        sql = "CREATE TABLE RESTAURANT_OWNERS (MA_CNH TEXT, TEN_CNH TEXT, VAITRO_CNH TEXT, MATKHAU_CNH)";
        sqLiteDatabase.execSQL(sql);

        //Tao bang loai nha hang
        sql = "CREATE TABLE TYPE_RESRTAURANT (MALOAI_NH INTEGER PRIMARY KEY AUTOINCREMENT, TENLOAI_NH TEXT)";
        sqLiteDatabase.execSQL(sql);

        //Tao bang nha hang
        sql = "CREATE TABLE RESTAURANT (MA_NH TEXT, TEN_NH TEXT, DIACHI TEXT, MOTA TEXT, " +
                "HINHANH_NH INTEGER, MA_CNH INTEGER, MALOAI_NH INTEGER," +
                "FOREIGN KEY (MALOAI_NH) REFERENCES TYPE_RESRTAURANT(MALOAI_NH)," +
                "FOREIGN KEY (MA_CNH) REFERENCES RESTAURANT_OWNERS(MA_CNH))";
        sqLiteDatabase.execSQL(sql);

        //Tao bang loai mon an
        sql = "CREATE TABLE TYPE_OF_FOOD (MALOAI_MA INTEGER PRIMARY KEY AUTOINCREMENT, TEN_LMA TEXT, HINHANH_LMA TEXT)";
        sqLiteDatabase.execSQL(sql);

        //Tao bang mon an
        sql = "CREATE TABLE FOOD (MA_MA TEXT, TEN_MA TEXT, MOTA TEXT, HINHANH_MA TEXT, GIA DOUBLE, " +
                "MA_NH TEXT, MALOAI_MA INTEGER," +
                "FOREIGN KEY (MA_NH) REFERENCES RESRTAURANT(MA_NH)," +
                "FOREIGN KEY (MALOAI_MA) REFERENCES TYPE_OF_FOOD(MALOAI_MA))";
        sqLiteDatabase.execSQL(sql);

        //Tao bang hoa don
        sql = "CREATE TABLE ORDERS (MA_HD TEXT, NGAYMUA TEXT, TONGTIEN DOUBLE, TRANGTHAI TEXT, MA_MA TEXT, MA_NH TEXT, MA_KH TEXT," +
                "FOREIGN KEY (MA_MA) REFERENCES FOOD(MA_MA)," +
                "FOREIGN KEY (MA_NH) REFERENCES RESRTAURANT(MA_NH)," +
                "FOREIGN KEY (MA_KH) REFERENCES TYPE_RESRTAURANT(MA_KH))";
        sqLiteDatabase.execSQL(sql);

        //Tao bang hoa don chi tiet
        sql = "CREATE TABLE ORDER_DETAIL (MA_HDCT TEXT, SOLUONG INTEGER, DONGIA DOUBLE, MA_MA TEXT, MA_HD TEXT," +
                "FOREIGN KEY (MA_MA) REFERENCES FOOD(MA_MA), " +
                "FOREIGN KEY (MA_HD) REFERENCES ORDERS(MA_HD))";
        sqLiteDatabase.execSQL(sql);

        //Tao bang gio hang
        sql = "CREATE TABLE CART (MA_GH TEXT, SOLUONG_CART INTEGER, DONGIA_CART DOUBLE, MA_MA TEXT, MA_KH TEXT," +
                "FOREIGN KEY (MA_MA) REFERENCES FOOD(MA_MA), " +
                "FOREIGN KEY (MA_KH) REFERENCES CUSTOMER(MA_KH))";
        sqLiteDatabase.execSQL(sql);
    }

    //Them du lieu
    private void themDuLieu(SQLiteDatabase sqLiteDatabase) {
        sql = "INSERT INTO TYPE_RESRTAURANT(TENLOAI_NH) VALUES('Fast food'), ('Family'), ('Casual'), ('Premium')";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO TYPE_OF_FOOD(TEN_LMA, HINHANH_LMA) " +
                "VALUES('Rice', "+R.drawable.ic_rice256+"), " +
                "('Noodle', "+R.drawable.ic_noodle256+"), " +
                "('Milktea',"+R.drawable.ic_milktea256+"),"+
                "('Hamburger',"+R.drawable.cat_2+"),"+
                "('Drink', "+R.drawable.cat_4+"), " +
                "('Hotdog',"+R.drawable.cat_3+"),"+
                "('Cake', "+R.drawable.cat_1+")";
        sqLiteDatabase.execSQL(sql);
    }
}
