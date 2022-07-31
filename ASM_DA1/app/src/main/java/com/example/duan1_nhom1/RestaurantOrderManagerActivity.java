package com.example.duan1_nhom1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.duan1_nhom1.adapter.OrderResAdapter;
import com.example.duan1_nhom1.dao.OrderDAO;
import com.example.duan1_nhom1.modul.Order;

import java.util.ArrayList;

public class RestaurantOrderManagerActivity extends AppCompatActivity {
    private OrderResAdapter adapter;
    private OrderDAO orderDAO;
    private ArrayList<Order> list;
    private String maNH = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_order_manager);
        RecyclerView rcv = findViewById(R.id.rcvListOrderRes);
        //Lay maNH tu DetailRestaurantActivity
        Intent intent = getIntent();
        maNH = intent.getStringExtra("maNH");
        //Set rcv
        orderDAO = new OrderDAO(getApplicationContext());
        list = orderDAO.getOrderTheoMaNH(maNH, "Cho xac nhan");

        adapter = new OrderResAdapter();
        adapter.getData(list, getApplicationContext());

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);

        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);

    }
}