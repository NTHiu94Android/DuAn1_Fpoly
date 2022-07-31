package com.example.duan1_nhom1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.duan1_nhom1.adapter.ProductAdapter;
import com.example.duan1_nhom1.dao.FoodDAO;
import com.example.duan1_nhom1.modul.Food;
import com.example.duan1_nhom1.modul.TypeOfFood;

import java.util.ArrayList;

public class CustomerListCategoriesActivity extends AppCompatActivity {

    private FoodDAO foodDAO;
    private TypeOfFood typeOfFood;
    private ProductAdapter adapter;
    private TextView tv;
    private RecyclerView rcv;
    private ArrayList<Food> listFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_categories);
        //Anh xa
        tv = findViewById(R.id.tvListCategories);
        rcv = findViewById(R.id.rcvListCategories);
        foodDAO = new FoodDAO(getApplicationContext());
        //Lay loai mon an tu trang chu
        Intent intent = getIntent();
        String maKH = intent.getStringExtra("maKH");
        typeOfFood = (TypeOfFood) intent.getSerializableExtra("FoodType");
        //Lay danh sach mon an theo ma loai mon an
        listFood = foodDAO.getFoodTheoMaLoaiMA(typeOfFood.getMaLoaiMA());
        //Set text len tv
        tv.setText(typeOfFood.getTenLoaiMA());
        //Set data len rcv
        adapter = new ProductAdapter();
        adapter.getData(listFood, CustomerListCategoriesActivity.this, maKH);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(manager);
    }
}