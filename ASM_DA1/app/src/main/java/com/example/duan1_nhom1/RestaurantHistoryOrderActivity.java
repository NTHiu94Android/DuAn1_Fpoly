package com.example.duan1_nhom1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.duan1_nhom1.adapter.OrderAdapter;
import com.example.duan1_nhom1.adapter.OrderResAdapter;
import com.example.duan1_nhom1.dao.OrderDAO;
import com.example.duan1_nhom1.modul.Order;

import java.util.ArrayList;

public class RestaurantHistoryOrderActivity extends AppCompatActivity {
    private OrderDAO orderDAO;
    private OrderAdapter adapter;
    private ArrayList<Order> list;
    private String maNH = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order_res);
        RecyclerView rcv = findViewById(R.id.rcvHistoryOrderRes);
        //Lay maNH tu DetailRestaurantActivity
        Intent intent = getIntent();
        maNH = intent.getStringExtra("maNH");

        orderDAO = new OrderDAO(getApplicationContext());
        list = orderDAO.getOrderTheoMaNH(maNH, "Don hang da duoc giao");

        adapter = new OrderAdapter();
        adapter.getData(list, getApplicationContext());

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);

        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);

    }
}