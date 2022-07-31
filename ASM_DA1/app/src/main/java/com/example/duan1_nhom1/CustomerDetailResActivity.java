package com.example.duan1_nhom1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.duan1_nhom1.adapter.ProductAdapter;
import com.example.duan1_nhom1.dao.FoodDAO;
import com.example.duan1_nhom1.modul.Food;
import com.example.duan1_nhom1.modul.Restaurant;

import java.util.ArrayList;

public class CustomerDetailResActivity extends AppCompatActivity {

    private ImageView imvRes;
    private TextView tvName, tvAddress, tvDescribe;
    private RecyclerView rcv;
    private String maKH = "";
    private ProductAdapter adapter;
    private FoodDAO foodDAO;
    private ArrayList<Food> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_res_customer);
        initUi();
        //Lay data tu PopularAdapter
        Intent intent = getIntent();
        maKH = intent.getStringExtra("maKH");
        Restaurant restaurant = (Restaurant) intent.getSerializableExtra("restaurant");
        //Set data len view
        Glide.with(CustomerDetailResActivity.this).load(restaurant.getHinhAnh()).into(imvRes);
        tvName.setText(restaurant.getTenNH());
        tvAddress.setText(restaurant.getDiaChi());
        tvDescribe.setText(restaurant.getMoTa());
        //Khai bao
        foodDAO = new FoodDAO(getApplicationContext());
        list = foodDAO.getFoodTheoMaNH(restaurant.getMaNH());
        adapter = new ProductAdapter();
        //Set data len rcv
        adapter.getData(list, CustomerDetailResActivity.this, maKH);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
    }
    //Anh xa
    private void initUi(){
        imvRes = findViewById(R.id.imvResCustomerDetail);
        tvName = findViewById(R.id.tvNameResCustomerDetail);
        tvAddress = findViewById(R.id.tvAddressResCustomerDetail);
        tvDescribe = findViewById(R.id.tvDescribeResCustomerDetail);
        rcv = findViewById(R.id.rcvFoodResCustomerDetail);
    }
}