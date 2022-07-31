package com.example.duan1_nhom1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.duan1_nhom1.adapter.FoodAdapter;
import com.example.duan1_nhom1.dao.FoodDAO;
import com.example.duan1_nhom1.modul.Food;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RestaurantFoodManagerActivity extends AppCompatActivity {
    private ArrayList<Food> list;
    private FoodDAO foodDAO;
    private FoodAdapter foodAdapter;
    private FloatingActionButton fab;
    private RecyclerView rcv;
    private String maNH = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_manager);
        initUi();
        Intent intent = getIntent();
        maNH = intent.getStringExtra("maNH");
        foodDAO = new FoodDAO(RestaurantFoodManagerActivity.this);
        list = new ArrayList<>();
        getData();

        //Bat su kien khi click vao item rcv
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RestaurantAddFoodActivity.class);
                i.putExtra("maNH", maNH);
                startActivity(i);
                finish();
            }
        });

    }
    //Anh xa view
    private void initUi(){
        fab = findViewById(R.id.fabAddFoodManager);
        rcv = findViewById(R.id.rcvFoodManager);
    }
    //Set du lieu len rcv
    private void getData(){
        if(list.size()>0){
            list.clear();
        }
        list = foodDAO.getFoodTheoMaNH(maNH);
        Log.e("maNhaHang", maNH);
        foodAdapter = new FoodAdapter();
        foodAdapter.getData(list, RestaurantFoodManagerActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(RestaurantFoodManagerActivity.this);
        manager.setOrientation(RecyclerView.VERTICAL);

        rcv.setAdapter(foodAdapter);
        rcv.setLayoutManager(manager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(RestaurantFoodManagerActivity.this, DividerItemDecoration.VERTICAL);
        rcv.addItemDecoration(itemDecoration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        Intent intent = getIntent();
        maNH = intent.getStringExtra("maNH");
    }
}